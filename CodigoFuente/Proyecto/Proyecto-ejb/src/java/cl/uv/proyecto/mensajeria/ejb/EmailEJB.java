/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.url.utils.UrlBuilder;
import java.io.File;
import java.util.Date;
import java.util.List;
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
    public void enviarEmail(String direccion, String asunto, String mensaje, List<ArchivoAdjunto> adjuntos) {
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
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR:MessagingException - "+ex.getMessage());
        }
    }

    private void enviarEmailNotificacionSolicitud(TypeEmail t,SolicitudRequerimiento s, String nameInvoker){
        String asunto = TypeEmail.ENVIO_SOLICITUD.construirAsunto(s, s.getSolicitante().getNombre());
        String msg = TypeEmail.ENVIO_SOLICITUD.construirMensaje(s, s.getSolicitante().getNombre());
        enviarEmail(s.getSolicitante().getCorreoUv(), asunto, msg);
    }
    
    
    @Override
    @Asynchronous
    public void enviarEmailConfirmacionEnvioSolicitud(SolicitudRequerimiento s) {
        String asunto = TypeEmail.ENVIO_SOLICITUD.construirAsunto(s, s.getSolicitante().getNombre());
        String msg = TypeEmail.ENVIO_SOLICITUD.construirMensaje(s, s.getSolicitante().getNombre());
        enviarEmail(s.getSolicitante().getCorreoUv(), asunto, msg);
    }
     
    public void enviarEmailCierreSolicitud(SolicitudRequerimiento s, Funcionario invoker, List<ArchivoAdjunto> adjuntos){
        String asunto = TypeEmail.CIERRE_SOLICITD.construirAsunto(s, s.getSolicitante().getNombre());
        String msg = TypeEmail.CIERRE_SOLICITD.construirMensaje(s, s.getSolicitante().getNombre());
        if (adjuntos!=null && !adjuntos.isEmpty()) {
            enviarEmail(s.getSolicitante().getCorreoUv(), asunto, msg, adjuntos);
        }else{
            enviarEmail(s.getSolicitante().getCorreoUv(), asunto, msg);
        }
    }
}