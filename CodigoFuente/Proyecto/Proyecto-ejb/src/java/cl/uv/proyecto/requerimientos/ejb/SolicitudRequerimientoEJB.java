/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.consts.EstadoSR;
import cl.uv.proyecto.mensajeria.ejb.EmailEJBLocal;
import cl.uv.proyecto.persistencia.ejb.EstadoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TipoPrioridadFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.*;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Jano
 */
@Stateless
public class SolicitudRequerimientoEJB implements SolicitudRequerimientoEJBLocal {

    private final long MOD = 1000L;
    private final long DESPLAZAMIENTO = 10000000000L;
    private final long TIME_INIT = 1341773584868L;
    private final short PRIORIDAD_INICIAL = 0;
    private final int CIERRE = 0;
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private EstadoSolicitudRequerimientoFacadeLocal estadoSolicitudFacade;
    @EJB
    private TipoPrioridadFacadeLocal tipoPrioridadFacade;
    @EJB
    private EmailEJBLocal emailEJB;

    @Override
    public String generarCodigo(long num) {
        String code = "";
        String s = "1qQaAzZ2wWsSxX3eEdDcC4rRfFvV5TtgGbB6yYhHnN7uUjJmM8iIkK9oOlpP0";
        int length = s.length() - 1;
        while (num > 0) {
            int n = (int) (num % length);
            code += String.valueOf(s.charAt(n));
            num = num / length;
        }
        return code;
    }

    @Override
    public String generarCodigoConsulta(SolicitudRequerimiento solicitud) {
        String codigo = "";
        Random rand = new Random();
        rand.setSeed(new Date().getTime() - solicitud.getSolicitante().getRut());
        do {
            long base = (rand.nextInt(8) + 1) * MOD;
            long time = (solicitud.getFechaEnvio().getTime() - TIME_INIT) / MOD;
            base += solicitud.getSolicitante().getRut() % MOD;
            base = base * DESPLAZAMIENTO + time;
            codigo = generarCodigo(base);
        } while (!validarCodigoConsulta(codigo));

        return codigo;
    }

    @Override
    public boolean validarCodigoConsulta(String codigoConsulta) {
        return (solicitudFacade.buscarPorCodigo(codigoConsulta) == null);
    }

    @Override
    public String enviarSolicitud(SolicitudRequerimiento solicitud, Funcionario solicitante) {
        Date fechaActual = new Date();
        solicitud.setFechaEnvio(fechaActual);
        solicitud.setFechaUltimaActualizacion(fechaActual);
        solicitud.setSolicitante(solicitante);
        solicitud.setPrioridadSolicitud(tipoPrioridadFacade.find(PRIORIDAD_INICIAL));
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(EstadoSR.ENVIADA));
        solicitud.setCodigoConsulta(generarCodigoConsulta(solicitud));
        solicitudFacade.create(solicitud);
        return solicitud.getCodigoConsulta();
    }

    @Override
    public void rechazarSolicitud(SolicitudRequerimiento solicitud) {
        EstadoSolicitudRequerimiento estado = estadoSolicitudFacade.find(EstadoSR.RECHAZADA);
        solicitud.setEstadoSolicitud(estado);
        solicitudFacade.edit(solicitud);
    }

    private void cerrarSolicitud(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(EstadoSR.CERRADA));
        solicitud.setFechaCierre(new Date());
        solicitudFacade.edit(solicitud);
    }

    @Override
    public void enviarRespuestaDirecta(SolicitudRequerimiento solicitud, Boolean enviarCopiaCorreo) {
        cerrarSolicitud(solicitud);
        if (enviarCopiaCorreo) {
            String email = solicitud.getSolicitante().getCorreoUv();
            String asunto = crearAsunto(solicitud, CIERRE);
            String mensaje = crearMensaje(solicitud, CIERRE);
            emailEJB.enviarEmail(email, asunto, mensaje);
        }
    }

    @Override
    public void enviarRespuestaManual(SolicitudRequerimiento solicitud, String[] direcciones, String asunto) {
        cerrarSolicitud(solicitud);
        asunto = (asunto.isEmpty()) ? crearAsunto(solicitud, CIERRE) : asunto;
        String mensaje = crearMensaje(solicitud, CIERRE);
        emailEJB.enviarEmail(direcciones, asunto, mensaje);

    }

    @Override
    public void transferirSolicitud(SolicitudRequerimiento solicitud, Area nuevaAreaResponsable, String motivoTransferencia) {
        solicitud.setJustificacionTrasnferencia(motivoTransferencia);
        solicitud.setAreaResponsable(nuevaAreaResponsable);
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(EstadoSR.TRANSFERIDA));
        solicitudFacade.edit(solicitud);
    }

    @Override
    public void asignarSolicitud(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(EstadoSR.ASIGNADA));
        solicitudFacade.edit(solicitud);
    }

    @Override
    public void iniciarSolicitud(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(EstadoSR.INICIADA));
        solicitudFacade.edit(solicitud);
    }

    @Override
    public void enviarRespuestaJefeArea(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(EstadoSR.FINALIZADA_SIN_RESPUESTA));
        solicitudFacade.edit(solicitud);
    }

    private String crearMensaje(SolicitudRequerimiento solicitud, int tipoMensaje) {
        String mensaje = "";
        switch (tipoMensaje) {
            case CIERRE:
                mensaje = "<strong>[Codigo: " + solicitud.getCodigoConsulta() + " ][" + solicitud.getAsunto() + "]</strong>"
                        + "<div><br/><strong>Respuesta:</strong><br/></div>"
                        + solicitud.getRespuesta()
                        + "<br/><div>Para mayor información ingrese al sistema</div>";
                break;
            default:
                throw new AssertionError();
        }

        return mensaje;
    }

    private String crearAsunto(SolicitudRequerimiento solicitud, int tipoAsunto) {
        String asunto = "";
        switch (tipoAsunto) {
            case CIERRE:
                asunto = "La solicitud " + solicitud.getCodigoConsulta() + " fue cerrada.";
                break;
            default:
                throw new AssertionError();
        }

        return asunto;
    }
}
