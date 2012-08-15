/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

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
    
    //@Resource(lookup = "javaMail/correoUv")
    //@Resource(name = "email/uv")
    @Resource(lookup = "email/Sistema")
    private Session mailSession;

    @Override
    @Asynchronous
    public void enviarEmail(String direccion, String asunto, String mensaje) {
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
    
}