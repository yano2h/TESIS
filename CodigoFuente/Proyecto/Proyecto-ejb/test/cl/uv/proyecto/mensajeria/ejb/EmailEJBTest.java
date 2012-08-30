/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.test.junit.base.BaseTestEJB;
import javax.ejb.embeddable.EJBContainer;
import org.junit.*;
import static org.junit.Assert.*;
import org.subethamail.wiser.Wiser;

/**
 *
 * @author Jano
 */
public class EmailEJBTest extends BaseTestEJB{
    
    private Wiser smtpServer;
    
    public EmailEJBTest() {
    }
    
    @Before
    public void startSmtpServer() {
        smtpServer = new Wiser();
        smtpServer.setPort(2500);
        smtpServer.start();
    }
    
    @After
    public void stopSmtpServer() {
        smtpServer.stop();
    }

    /**
     * Test of enviarEmail method, of class EmailEJB.
     */
    @Test
    public void testEnviarEmail_3args_1() throws Exception {
        EmailEJBLocal instance = lookupBy(EmailEJB.class);
        instance.enviarEmail("yano2h@gmail.com", "test junit", "esto es un test junit");
        System.out.println("SIZE:"+smtpServer.getMessages().size());
        
        assertTrue(smtpServer.getMessages().size() == 1);
    }

    /**
     * Test of enviarEmail method, of class EmailEJB.
     */
    @Test
    public void testEnviarEmail_3args_2() throws Exception {
        String[] direcciones = {"yano2h@gmail.com","alejandro.alvareza@alumnos.uv.cl"};
        EmailEJBLocal instance = lookupBy(EmailEJB.class);
        instance.enviarEmail(direcciones, "test junit", " esto es un test junit");
        System.out.println("SIZE:"+smtpServer.getMessages().size());
        assertTrue(smtpServer.getMessages().size() == 2);
    }
}
