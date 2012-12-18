/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.url.utils.UrlBuilder;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;

@Stateless
public class EmailEJB implements EmailEJBLocal {

    @Resource(name = "email/ssrscm2")
    private Session mailSession;
    private String basePathFiles = Resources.getValue("BasicParam", "pathArchivosSolicitudes");

    @Override
    @Asynchronous
    public void enviarEmail(String direccion, String asunto, String mensaje) {
        System.out.println("enviarEmail");
        try {
            Message msg = new MimeMessage(mailSession);

            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(direccion));
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setContent(mensaje, "text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (AddressException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Asynchronous
    public void enviarEmail(String[] direcciones, String asunto, String mensaje) {
        System.out.println("enviarEmail");
        try {
            Message msg = new MimeMessage(mailSession);

            Address[] addresses = new Address[direcciones.length];
            for (int i = 0; i < direcciones.length; i++) {
                addresses[i] = new InternetAddress(direcciones[i]);
            }

            msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setContent(mensaje, "text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (AddressException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Asynchronous
    public void enviarEmail(String[] direcciones, String asunto, String mensaje, List<ArchivoAdjunto> adjuntos) throws MessagingException {
        System.out.println("enviarEmail");
        try {
            Message msg = new MimeMessage(mailSession);
            Address[] addresses = new Address[direcciones.length];
            for (int i = 0; i < direcciones.length; i++) {
                addresses[i] = new InternetAddress(direcciones[i]);
            }

            msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setSubject(asunto);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mensaje, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            //Adjuntar Archizos
            for (ArchivoAdjunto archivoAdjunto : adjuntos) {
                BodyPart adjuntoBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(new File(basePathFiles + archivoAdjunto.getPathFile()));
                adjuntoBodyPart.setDataHandler(new DataHandler(source));
                adjuntoBodyPart.setFileName(archivoAdjunto.getNombre());
                multipart.addBodyPart(adjuntoBodyPart);
            }

            msg.setContent(multipart);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (AddressException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Asynchronous
    public void enviarEmail(String direccion, String asunto, String mensaje, List<ArchivoAdjunto> adjuntos) throws MessagingException {
        System.out.println("enviarEmail");
        try {
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(direccion));
            msg.setSubject(asunto);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mensaje, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            //Adjuntar Archivos
            for (ArchivoAdjunto archivoAdjunto : adjuntos) {
                BodyPart adjuntoBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(new File(basePathFiles + archivoAdjunto.getPathFile()));
                adjuntoBodyPart.setDataHandler(new DataHandler(source));
                adjuntoBodyPart.setFileName(archivoAdjunto.getNombre());
                multipart.addBodyPart(adjuntoBodyPart);
            }

            msg.setContent(multipart);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (AddressException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Asynchronous
    private void enviarEmailNotificacionSolicitud(TypeEmail t, SolicitudRequerimiento s, Funcionario invoker, Funcionario destinatario) {
        String asunto = t.construirAsunto(s, (invoker != null) ? invoker.getNombre() : null);
        String msg = t.construirMensaje(s, (invoker != null) ? invoker.getNombre() : null);
        enviarEmail(destinatario.getCorreoUv(), asunto, msg);
    }

    @Override
    @Asynchronous
    public void enviarEmailConfirmacionEnvioSolicitud(SolicitudRequerimiento s) {
        enviarEmailNotificacionSolicitud(TypeEmail.ENVIO_SOLICITUD, s,
                s.getSolicitante(), s.getSolicitante());
    }

    @Override
    @Asynchronous
    public void enviarEmailInicioSolicitud(SolicitudRequerimiento s) {
        enviarEmailNotificacionSolicitud(TypeEmail.INICIO_SOLICITUD, s,
                s.getResponsable(), s.getSolicitante());
    }

    @Override
    @Asynchronous
    public void enviarEmailRechazoSolicitud(SolicitudRequerimiento s, Funcionario invoker) {
        enviarEmailNotificacionSolicitud(TypeEmail.RECHAZO_SOLICITUD, s,
                invoker, s.getSolicitante());
    }

    @Override
    @Asynchronous
    public void enviarEmailTransferenciaSolicitud(SolicitudRequerimiento s, Funcionario invoker) {
        enviarEmailNotificacionSolicitud(TypeEmail.TRANSFERENCIA_SOLICITUD, s,
                invoker, s.getSolicitante());
    }

    @Override
    @Asynchronous
    public void enviarEmailCierreSolicitud(SolicitudRequerimiento s, Funcionario invoker, List<ArchivoAdjunto> adjuntos) {
        if (adjuntos != null && !adjuntos.isEmpty()) {
            String asunto = TypeEmail.CIERRE_SOLICITUD.construirAsunto(s, null);
            String msg = TypeEmail.CIERRE_SOLICITUD.construirMensaje(s, null);
            try {
                enviarEmail(s.getSolicitante().getCorreoUv(), asunto, msg, adjuntos);
            } catch (MessagingException ex) {
                Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
                String msgError = Resources.getValue("Emails", "ALERTA_ERROR_ENVIO");
                msgError = String.format(msgError, UrlBuilder.buildPublicUrlSolicitudReq(s.getCodigoConsulta()));
                msg += msgError;
                enviarEmail(s.getSolicitante().getCorreoUv(), asunto, msg);
            }
        } else {
            enviarEmailNotificacionSolicitud(TypeEmail.CIERRE_SOLICITUD, s, invoker, s.getSolicitante());
        }
    }

    @Override
    @Asynchronous
    public void enviarEmailRespuestaManual(SolicitudRequerimiento s, String[] direcciones, String asunto, String msg, List<ArchivoAdjunto> adjuntos) {

        try {
            String msg_respManual = Resources.getValue("Emails", "RESPUESTA_MANUAL");
            msg += String.format(msg_respManual, UrlBuilder.buildPublicUrlSolicitudReq(s.getCodigoConsulta()));
            enviarEmail(direcciones, asunto, msg, adjuntos);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
            String msgError = Resources.getValue("Emails", "ALERTA_ERROR_ENVIO");
            msgError = String.format(msgError, UrlBuilder.buildPublicUrlSolicitudReq(s.getCodigoConsulta()));
            msg += msgError;
            enviarEmail(direcciones, asunto, msg);
        }
    }

    @Override
    @Asynchronous
    public void enviarEmailNotificacionComentario(ComentarioSolicitud c) {
        String asunto = TypeEmail.COMENTARIO_SOLICITUD.construirAsunto(c.getSolicitudRequerimiento(), c.getAutor().getNombre());
        String msg = TypeEmail.COMENTARIO_SOLICITUD.construirMensaje(c, c.getAutor().getNombre());
        if (c.getAutor().equals(c.getSolicitudRequerimiento().getSolicitante())) {
            enviarEmail(c.getSolicitudRequerimiento().getResponsable().getCorreoUv(), asunto, msg);
        } else {
            enviarEmail(c.getSolicitudRequerimiento().getSolicitante().getCorreoUv(), asunto, msg);
        }
    }

    @Override
    @Asynchronous
    public void enviarEmailAsignacionSolicitud(SolicitudRequerimiento s) {
        enviarEmailNotificacionSolicitud(TypeEmail.ASIGNACION_SOLICITUD, s, null, s.getSolicitante());
    }

    @Override
    @Asynchronous
    public void enviarEmailEdicionSolicitud(SolicitudRequerimiento s, boolean cambioFechaVencimiento, boolean cambioPrioridad) {
        String asunto = TypeEmail.CAMBIO_SOLICITUD.construirAsunto(s, null);
        String msg = TypeEmail.CAMBIO_SOLICITUD.construirMensaje(s, null);
        String listadoCambios = "";
        
        if (cambioFechaVencimiento) {
            listadoCambios += Resources.getValue("Emails", "LI_CAMBIO_FVENCIMIENTO");
            SimpleDateFormat df = new SimpleDateFormat("dd 'de' MMMM 'a las' HH:mm", new Locale("es","CL"));
            listadoCambios = String.format(listadoCambios, df.format(s.getFechaVencimiento()));
        }
        
        if (cambioPrioridad) {
            listadoCambios += Resources.getValue("Emails", "LI_CAMBIO_PRIORIDAD");
            listadoCambios = String.format(listadoCambios, s.getPrioridadSolicitud().getNombrePrioridad());
        }

        msg = String.format(msg, listadoCambios);
        enviarEmail(s.getResponsable().getCorreoUv(), asunto, msg);
    }

    @Override
    @Asynchronous
    public void enviarEmailCambioResponsable(SolicitudRequerimiento s) {
        enviarEmailNotificacionSolicitud(TypeEmail.CAMBIO_RESPONSABLE_SOLICITUD, s, null, s.getSolicitante());
    }
}