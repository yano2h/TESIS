/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.NotificacionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Jano
 */
@Stateless
public class NotificacionEJB implements NotificacionEJBLocal {

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @EJB
    private NotificacionFacadeLocal notificacionFacade;

    @Override
    public void crearNotificacionSolicitud(TypeNotification type, SolicitudRequerimiento s, Funcionario invoker) {
        Notificacion n;
        switch (type) {
            case ENVIO_SOLICITUD:
                Funcionario f = (Funcionario) funcionarioDisicoFacade.buscarJefeArea(s.getAreaResponsable());
                n = crearNotificacionEnvioSolicitud(s, f);
                break;
            case RECHAZO_SOLICITUD:
                n = crearNotificacionRechazoSolicitud(s);
                break;
            case TRANSFERENCIA_SOLICITUD:
                n = crearNotificacionTranferenciaSolicitud(s);
                break;
            case ASIGNACION_SOLICITUD:
                n = (s.getFechaVencimiento() != null)
                        ? crearNotificacionAsignacionSolicitudConFecha(s)
                        : crearNotificacionAsignacionSolicitud(s);
                break;
            case INICIO_SOLICITUD:
                n = crearNotificacionInicioSolicitud(s);
                break;
            case CIERRE_SOLICITD:
                n = crearNotificacionCierreSolicitud(s);
                break;
            case COMENTARIO_SOLICITUD:
                n = crearNotificacionComentarioSolicitud(s, invoker);
                break;
            default:
                throw new AssertionError("Tipo enum desconocido", null);
        }

        notificacionFacade.create(n);
    }

    private Notificacion crearNotificacionEnvioSolicitud(SolicitudRequerimiento s, Funcionario destinatario) {
        String msg = Resources.getValue("Notificaciones", "Notf_EnvioSolicitudReq");
        msg = String.format(msg, s.getSolicitante().getNombreCorto(), s.getAsunto());
        return new Notificacion(msg, destinatario);
    }

    private Notificacion crearNotificacionRechazoSolicitud(SolicitudRequerimiento s) {
        String msg = Resources.getValue("Notificaciones", "Notf_RechazoSolicitudReq");
        msg = String.format(msg, s.getAsunto());
        return new Notificacion(msg, s.getSolicitante());
    }

    private Notificacion crearNotificacionAsignacionSolicitud(SolicitudRequerimiento s) {
        String msg = Resources.getValue("Notificaciones", "Notf_AsignacionSolicitudReq");
        msg = String.format(msg, s.getAsunto(), s.getResponsable().getNombreCorto());
        return new Notificacion(msg, s.getSolicitante());
    }

    private Notificacion crearNotificacionAsignacionSolicitudConFecha(SolicitudRequerimiento s) {
        String msg = Resources.getValue("Notificaciones", "Notf_AsignacionSolicitudReqConFecha");
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'a las' hh:mm", new Locale("es","CL"));
        msg = String.format(msg, s.getAsunto(), s.getResponsable().getNombreCorto(), formateador.format(s.getFechaVencimiento()));
        return new Notificacion(msg, s.getSolicitante());
    }

    private Notificacion crearNotificacionInicioSolicitud(SolicitudRequerimiento s) {
        String msg = Resources.getValue("Notificaciones", "Notf_InicioSolicitud");
        msg = String.format(msg, s.getResponsable().getNombreCorto(), s.getAsunto());
        return new Notificacion(msg, s.getSolicitante());
    }

    private Notificacion crearNotificacionCierreSolicitud(SolicitudRequerimiento s) {
        String msg = Resources.getValue("Notificaciones", "Notf_CierreSolicitudReq");
        msg = String.format(msg, s.getAsunto(), s.getResponsable().getNombreCorto());
        return new Notificacion(msg, s.getSolicitante());
    }

    private Notificacion crearNotificacionComentarioSolicitud(SolicitudRequerimiento s, Funcionario f) {
        String msg = Resources.getValue("Notificaciones", "Notf_ComentarioSolicitudReq");
        msg = String.format(msg, f.getNombreCorto(), s.getAsunto());
        if (f.getRut() == s.getSolicitante().getRut() && s.getResponsable() != null) {
            return new Notificacion(msg, s.getResponsable());
        } else {
            return new Notificacion(msg, f);
        }
    }

    private Notificacion crearNotificacionTranferenciaSolicitud(SolicitudRequerimiento s) {
        String msg = Resources.getValue("Notificaciones", "Notf_TranferenciaSolicitudReq");
        msg = String.format(msg, s.getAsunto(), s.getAreaResponsable().getNombreArea());
        return new Notificacion(msg, s.getSolicitante());
    }
}
