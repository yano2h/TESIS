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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailEJB implements EmailEJBLocal {

    //@Resource(lookup = "javaMail/correoUv")
    @Resource(lookup = "email/Sistema")
    //@Resource(name = "email/uv")
    private Session mailSession;

    @Override
    @Asynchronous
    public void enviarEmail(String direccion, String asunto, String mensaje) {
        Message msg = new MimeMessage(mailSession);
       
        try {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(direccion));
            msg.setSubject(asunto);
          msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setText(mensaje);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}