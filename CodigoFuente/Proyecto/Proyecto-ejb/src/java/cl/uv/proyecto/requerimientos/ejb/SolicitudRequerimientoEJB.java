/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.file.ejb.FileManagerEJBLocal;
import cl.uv.proyecto.mensajeria.ejb.EmailEJBLocal;
import cl.uv.proyecto.mensajeria.ejb.NotificacionEJBLocal;
import cl.uv.proyecto.mensajeria.ejb.TypeNotification;
import cl.uv.proyecto.persistencia.ejb.ComentarioSolicitudFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.EstadoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TipoPrioridadFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private EstadoSolicitudRequerimientoFacadeLocal estadoSolicitudFacade;
    @EJB
    private TipoPrioridadFacadeLocal tipoPrioridadFacade;
    @EJB
    private ComentarioSolicitudFacadeLocal comentarioSolicitudFacade;
    @EJB
    private EmailEJBLocal emailEJB;
    @EJB
    private NotificacionEJBLocal notificacionEJB;
    @EJB
    private FileManagerEJBLocal fileManagerEJB;

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
    public String enviarSolicitud(SolicitudRequerimiento solicitud, Funcionario solicitante, List<ArchivoAdjunto> archivosAdjuntos) {
        Date fechaActual = new Date();
        solicitud.setFechaEnvio(fechaActual);
        solicitud.setFechaUltimaActualizacion(fechaActual);
        solicitud.setSolicitante(solicitante);
        solicitud.setPrioridadSolicitud(tipoPrioridadFacade.find(Resources.getValueShort("Tipos", "Prioridad_Inicial")));
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_ENVIADA")));
        solicitud.setCodigoConsulta(generarCodigoConsulta(solicitud));
        solicitudFacade.create(solicitud);
        notificacionEJB.crearNotificacionSolicitud(TypeNotification.ENVIO_SOLICITUD, solicitud, solicitante);
        if (archivosAdjuntos != null && archivosAdjuntos.size() > 0) {
            fileManagerEJB.adjuntarArchivosSolicitudRequerimiento(archivosAdjuntos, solicitud);
        }
        emailEJB.enviarEmailConfirmacionEnvioSolicitud(solicitud);
        return solicitud.getCodigoConsulta();
    }

    @Override
    public void rechazarSolicitud(SolicitudRequerimiento solicitud) {
        EstadoSolicitudRequerimiento estado = estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_RECHAZADA"));
        solicitud.setEstadoSolicitud(estado);
        solicitudFacade.edit(solicitud);
        emailEJB.enviarEmailRechazoSolicitud(solicitud, null);
        notificacionEJB.crearNotificacionSolicitud(TypeNotification.RECHAZO_SOLICITUD, solicitud, null);
    }

    private void cerrarSolicitud(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_CERRADA")));
        solicitud.setFechaCierre(new Date());
        solicitudFacade.edit(solicitud);
        notificacionEJB.crearNotificacionSolicitud(TypeNotification.CIERRE_SOLICITD, solicitud, null);
    }

    private void cerrarSolicitud(SolicitudRequerimiento solicitud, List<ArchivoAdjunto> archivosAdjuntos) {
        if (archivosAdjuntos != null && archivosAdjuntos.size() > 0) {
            fileManagerEJB.adjuntarArchivosSolicitudRequerimiento(archivosAdjuntos, solicitud);
        }
        cerrarSolicitud(solicitud);
    }

    @Override
    public void enviarRespuestaDirecta(SolicitudRequerimiento solicitud, Boolean enviarCopiaCorreo, List<ArchivoAdjunto> archivosAdjuntos) {
        cerrarSolicitud(solicitud, archivosAdjuntos);
        if (enviarCopiaCorreo) {
            emailEJB.enviarEmailCierreSolicitud(solicitud, null, archivosAdjuntos);
        }
    }

    @Override
    public void enviarRespuestaManual(SolicitudRequerimiento solicitud, String[] direcciones, String asunto, List<ArchivoAdjunto> archivosAdjuntos) {
        cerrarSolicitud(solicitud, archivosAdjuntos);
        emailEJB.enviarEmailRespuestaManual(solicitud, direcciones, asunto, solicitud.getRespuesta(), archivosAdjuntos);
    }

    @Override
    public void transferirSolicitud(SolicitudRequerimiento solicitud, Area nuevaAreaResponsable, String motivoTransferencia) {
        solicitud.setJustificacionTrasnferencia(motivoTransferencia);
        solicitud.setAreaResponsable(nuevaAreaResponsable);
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_TRANSFERIDA")));
        solicitudFacade.edit(solicitud);
        emailEJB.enviarEmailTransferenciaSolicitud(solicitud, null);
        notificacionEJB.crearNotificacionSolicitud(TypeNotification.TRANSFERENCIA_SOLICITUD, solicitud, null);
    }

    @Override
    public void asignarSolicitud(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_ASIGNADA")));
        solicitudFacade.edit(solicitud);
        emailEJB.enviarEmailAsignacionSolicitud(solicitud);
        notificacionEJB.crearNotificacionSolicitud(TypeNotification.ASIGNACION_SOLICITUD, solicitud, null);
    }

    @Override
    public void convertirSolicitudEnProyecto(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_CERRADA")));
        solicitud.setFechaCierre(new Date());
        solicitudFacade.edit(solicitud);
    }

    @Override
    public void iniciarSolicitud(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_INICIADA")));
        solicitudFacade.edit(solicitud);
        emailEJB.enviarEmailInicioSolicitud(solicitud);
        notificacionEJB.crearNotificacionSolicitud(TypeNotification.INICIO_SOLICITUD, solicitud, null);
    }

    @Override
    public void dejarPendienteSolicitud(SolicitudRequerimiento solicitud) {
        if (solicitud.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento().equals(Resources.getValueShort("Estados", "EstadoSR_ASIGNADA"))) {
            solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_PENDIENTE")));
            solicitudFacade.edit(solicitud);
        }
    }

    @Override
    public void dejarPendienteSolicitud(Long idSolicitud) {
        SolicitudRequerimiento s = solicitudFacade.find(idSolicitud);
        dejarPendienteSolicitud(s);
    }

    @Override
    public void enviarRespuestaJefeArea(SolicitudRequerimiento solicitud) {
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(Resources.getValueShort("Estados", "EstadoSR_FINALIZADA_SIN_RESPUESTA")));
        solicitudFacade.edit(solicitud);
    }

    @Override
    public void comentarSolicitud(String comentario, SolicitudRequerimiento solicitud, Funcionario autor) {
        if (!comentario.isEmpty()) {
            ComentarioSolicitud c = new ComentarioSolicitud();
            c.setAutor(autor);
            c.setFecha(new Date());
            c.setVisible(true);
            c.setComentario(comentario);
            c.setSolicitudRequerimiento(solicitud);
            comentarioSolicitudFacade.create(c);
            notificacionEJB.crearNotificacionSolicitud(TypeNotification.COMENTARIO_SOLICITUD, solicitud, autor);
            emailEJB.enviarEmailNotificacionComentario(c);
        }
    }

    @Override
    public void editarResponsableSolicitud(SolicitudRequerimiento solicitud) {
        SolicitudRequerimiento s = solicitudFacade.find(solicitud.getIdSolicitudRequerimiento());

        if (!s.getResponsable().equals(solicitud.getResponsable())) {
            solicitud.setEstadoSolicitud(estadoSolicitudFacade.find( Resources.getValueShort("Estados", "EstadoSR_ASIGNADA") ));
            solicitudFacade.edit(solicitud);
            emailEJB.enviarEmailCambioResponsable(s);
        } else {
            boolean cambioFechaVencimiento = (solicitud.getFechaVencimiento() != null && !solicitud.getFechaVencimiento().equals(s.getFechaVencimiento()))
                                  || solicitud.getFechaVencimiento() == null && s.getFechaVencimiento() != null;
            boolean cambioPrioridad = (solicitud.getPrioridadSolicitud() != null && !solicitud.getPrioridadSolicitud().equals(s.getPrioridadSolicitud()))
                    || solicitud.getPrioridadSolicitud() == null && s.getPrioridadSolicitud() != null;

            solicitudFacade.edit(solicitud);
            emailEJB.enviarEmailEdicionSolicitud(solicitud, cambioFechaVencimiento, cambioPrioridad);
        }
    }
}
