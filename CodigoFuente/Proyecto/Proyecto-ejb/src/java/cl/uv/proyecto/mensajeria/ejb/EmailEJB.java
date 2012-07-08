/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.proyecto.persistencia.entidades.Funcionario;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

@Stateless
public class EmailEJB implements EmailEJBLocal {

    @Resource(lookup = "javaMail/correoUv")
    private Session mailSession;

    @Override
    public void enviarEmail(String direccion, String asunto, String mensaje) {
        Message msg = new MimeMessage(mailSession);
       // System.out.println(mailSession.getProperty("mail.from"));
        
        try {
            
            msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            msg.setSubject(asunto);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(direccion));
                             
            msg.setText(mensaje);
            Transport.send(msg);
            System.out.println("Envio Exitoso");

        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}