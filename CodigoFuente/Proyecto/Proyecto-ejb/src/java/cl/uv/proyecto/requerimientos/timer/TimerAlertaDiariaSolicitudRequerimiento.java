/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.timer;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.mensajeria.ejb.EmailEJBLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.url.utils.UrlBuilder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alejandro
 */
@Singleton
@LocalBean
public class TimerAlertaDiariaSolicitudRequerimiento {

    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;
    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudRequerimientoFacade;
    @EJB
    private EmailEJBLocal emailEJB;
    
    private String templateMailBase;
    private String templateListaSol;
    private String templateSolicitud;
    private String asunto;
    private SimpleDateFormat df;

    public TimerAlertaDiariaSolicitudRequerimiento() {
        this.templateMailBase = getTemplateMail("RSM_SOL_ESTRUCTURA_PRINCIPAL");
        this.templateListaSol = getTemplateMail("RSM_SOL_BLOQUE_LISTADO_SOLICITUDES");
        this.templateSolicitud = getTemplateMail("RSM_SOL_BLOQUE_SOLICITUD");
        this.asunto = getTemplateMail("ASUNTO_RESUMEN");
        df = new SimpleDateFormat("dd 'de' MMMM 'a las' HH:mm", new Locale("es", "CL"));
    }

    //@Schedule(minute = "25", month = "*", year = "*", hour = "8", dayOfWeek = "Mon-Fri")
    @Schedule(minute = "12", month = "*", year = "*", hour = "16", dayOfWeek = "Mon-Fri")
    public void envioAlertaDiaria() {
        System.out.println("ENVIANDO ALERTA DIARIA");
        List<FuncionarioDisico> funcionarios = funcionarioDisicoFacade.findAll();

        for (FuncionarioDisico f : funcionarios) {
            String mensaje = construirMensaje(f);
            if (mensaje!=null) {
                emailEJB.enviarEmail(f.getCorreoUv(), asunto, mensaje);
            }
        }
    }

    private String construirMensaje(FuncionarioDisico f) {
        List<SolicitudRequerimiento> listaSolicitudesVencidas = solicitudRequerimientoFacade.buscarSolicitudesAsignadasVencidas(f);
        List<SolicitudRequerimiento> listaSolicitudesPorVencer = solicitudRequerimientoFacade.buscarSolicitudesAsignadasQueVencenProximamente(f, 10);
        List<SolicitudRequerimiento> listaSolicitudesNoRevisadas = solicitudRequerimientoFacade.buscarSolicitudesAsignadasNoRevisadas(f);
        
        String contenidoMensaje = construirContenidoMensaje(listaSolicitudesVencidas, listaSolicitudesPorVencer, listaSolicitudesNoRevisadas);
        
        if (contenidoMensaje!=null) {
            return String.format(templateMailBase, f.getNombre(),contenidoMensaje);
        }else{
            return null;
        }
    }

    private String construirContenidoMensaje(List<SolicitudRequerimiento> listaSolicitudesVencidas, List<SolicitudRequerimiento> listaSolicitudesPorVencer, List<SolicitudRequerimiento> listaSolicitudesNoRevisadas){    
        String contenido = "";
        
        if (listaSolicitudesVencidas!=null && !listaSolicitudesVencidas.isEmpty()) {
            contenido += construirListadoSolVencidas(listaSolicitudesVencidas);
        }
        
        if (listaSolicitudesPorVencer!=null && !listaSolicitudesPorVencer.isEmpty()) {
            contenido += construirListadoSolPorVencer(listaSolicitudesPorVencer);
        }
        
        if (listaSolicitudesNoRevisadas!=null && !listaSolicitudesNoRevisadas.isEmpty()) {
            contenido += construirListadoSolNoRevisadas(listaSolicitudesNoRevisadas);
        }
        
        return contenido.isEmpty()?null:contenido;
    }
    
    private String construirListadoSolicitudes(String titulo, String color, String listadoSol) {
        return String.format(templateListaSol, titulo, color, listadoSol);
    }

    private String construirListadoSolVencidas(List<SolicitudRequerimiento> listadoSolicitudes) {
        String titulo = getTemplateMail("TITULO_SOL_VENCIDA");
        String color = getTemplateMail("COLOR_BAR_SOL_VENCIDA");
        String listado = "";
        for (SolicitudRequerimiento s : listadoSolicitudes) {
            listado += construirBloqueSolVencida(s);
        }

        return construirListadoSolicitudes(titulo, color, listado);
    }

    private String construirListadoSolPorVencer(List<SolicitudRequerimiento> listadoSolicitudes) {
        String titulo = getTemplateMail("TITULO_SOL_PROXIMA_A_VENCER");
        String color = getTemplateMail("COLOR_BAR_SOL_PROXIMA_A_VENCER");
        String listado = "";
        for (SolicitudRequerimiento s : listadoSolicitudes) {
            listado += construirBloqueSolPorVencer(s);
        }

        return construirListadoSolicitudes(titulo, color, listado);
    }

    private String construirListadoSolNoRevisadas(List<SolicitudRequerimiento> listadoSolicitudes) {
        String titulo = getTemplateMail("TITULO_SOL_ASIGNADA");
        String color = getTemplateMail("COLOR_BAR_SOL_ASIGNADA");
        String listado = "";
        for (SolicitudRequerimiento s : listadoSolicitudes) {
            listado += construirBloqueSolNoRevisada(s);
        }

        return construirListadoSolicitudes(titulo, color, listado);
    }

    private String construirBloqueSolicitud(SolicitudRequerimiento s, String footer) {
        return String.format(templateSolicitud,
                getLink(s),
                s.getAsunto(),
                footer);
    }

    private String construirBloqueSolVencida(SolicitudRequerimiento s) {
        return construirBloqueSolicitud(s, construirFooterSolVencida(s));
    }

    private String construirBloqueSolPorVencer(SolicitudRequerimiento s) {
        return construirBloqueSolicitud(s, construirFooterSolPorVencer(s));
    }

    private String construirBloqueSolNoRevisada(SolicitudRequerimiento s) {
        return construirBloqueSolicitud(s, construirFooterSolNoRevisada(s));
    }

    private String construirFooterSolVencida(SolicitudRequerimiento s) {
        String footer = getTemplateMail("FOOTER_SOL_VENCIDA");
        
        return String.format(footer,
                s.getSolicitante().getNombreCorto(),
                df.format(s.getFechaVencimiento()));
    }

    private String construirFooterSolPorVencer(SolicitudRequerimiento s) {
        String footer = getTemplateMail("FOOTER_SOL_PROXIMA_A_VENCER");
        return String.format(footer,
                s.getSolicitante().getNombreCorto(),
                df.format(s.getFechaVencimiento()));
    }

    private String construirFooterSolNoRevisada(SolicitudRequerimiento s) {
        String footer = getTemplateMail("FOOTER_SOL_ASIGNADA");
        return String.format(footer,
                s.getTipoSolicitud().getNombreTipoSolicitud(),
                df.format(s.getFechaEnvio()),
                s.getSolicitante().getNombreCorto());
    }

    private String getTemplateMail(String name) {
        return Resources.getValue("Emails", name);
    }

    private String getLink(SolicitudRequerimiento s) {
        return UrlBuilder.buildPublicUrlSolicitudReq(s.getCodigoConsulta());
    }
}
