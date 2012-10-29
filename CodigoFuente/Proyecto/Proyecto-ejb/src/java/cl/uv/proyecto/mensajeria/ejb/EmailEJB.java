/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.url.utils.UrlBuilder;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailEJB implements EmailEJBLocal {
 
    @Resource(name = "email/ssrscm2")
    private Session mailSession;
    
    private String propertieEmail = "Emails";
    private String prefijoAsunto = "ASUNTO_";
    private String prefijoMensaje = "MSG_";
    
    @Override
    @Asynchronous
    public void enviarEmail(String direccion, String asunto, String mensaje) {
        System.out.println("ENVIANDO");
        Message msg = new MimeMessage(mailSession);
       
        try {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(direccion));
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setContent(mensaje,"text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    @Asynchronous
    public void enviarEmail(String[] direcciones,String asunto, String mensaje){
        Message msg = new MimeMessage(mailSession);
       
        try {
            Address[] addresses = new Address[direcciones.length];
            for (int i = 0; i < direcciones.length; i++) {
                addresses[i] = new InternetAddress(direcciones[i]);
            }
            
            msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setContent(mensaje,"text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String generarContenidoEmailSolicitud(TypeEmail t, SolicitudRequerimiento s, Funcionario invoker){
        String msg = Resources.getValue(propertieEmail, prefijoMensaje+t.name());
        switch(t){
            case ENVIO_SOLICITUD:
                String link = UrlBuilder.buildPublicUrlSolicitudReq(s.getCodigoConsulta());
                System.out.println("LINK:"+link);
                msg = String.format(msg,s.getAsunto(),s.getCodigoConsulta(),link);
                System.out.println("MSG:"+msg);
                break;
            case RECHAZO_SOLICITUD:

                break;
            case TRANSFERENCIA_SOLICITUD:

                break;
            case ASIGNACION_SOLICITUD:

                break;
            case INICIO_SOLICITUD:

                break;
            case CIERRE_SOLICITD:

                break;
            case COMENTARIO_SOLICITUD:

                break;
            default:
                throw new AssertionError("Tipo enum desconocido");
        }
                
                
        return msg;
    }
    
    @Override
    public String generarAsuntoEmailSolicitud(TypeEmail t, SolicitudRequerimiento s, Funcionario invoker){
        String asunto = Resources.getValue(propertieEmail, prefijoAsunto+t.name());
        
        switch(t){
            case ENVIO_SOLICITUD:
                asunto = String.format(asunto,s.getAsunto());
                break;
            case RECHAZO_SOLICITUD:

                break;
            case TRANSFERENCIA_SOLICITUD:

                break;
            case ASIGNACION_SOLICITUD:

                break;
            case INICIO_SOLICITUD:

                break;
            case CIERRE_SOLICITD:

                break;
            case COMENTARIO_SOLICITUD:

                break;
            default:
                throw new AssertionError("Tipo enum desconocido");
        }
        
        return asunto;
    }
   
    @Override
    @Asynchronous
    public void enviarEmailConfirmacionEnvioSolicitud(SolicitudRequerimiento s){
        String asunto = generarAsuntoEmailSolicitud(TypeEmail.ENVIO_SOLICITUD, s, s.getSolicitante());
        String msg = generarContenidoEmailSolicitud(TypeEmail.ENVIO_SOLICITUD, s, s.getSolicitante());
        enviarEmail(s.getSolicitante().getCorreoUv(), asunto, msg);
    }
}