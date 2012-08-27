/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.timer;

import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import cl.uv.test.junit.base.BaseTestEJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class TimerSolicitudRequerimientosUpdateTest extends BaseTestEJB {
    
    TimerSolicitudRequerimientosUpdate ejb;
    SolicitudRequerimientoEJBLocal solicitudEJB;
    
    public TimerSolicitudRequerimientosUpdateTest() {
    }

    @Before
    public void setUp() throws NamingException {
        ejb = lookupBy(TimerSolicitudRequerimientosUpdate.class);
    }
    
    @After
    public void tearDown() {
        ejb = null;
    }

    /**
     * Test of buscarSolicitudesVencidas method, of class TimerSolicitudRequerimientosUpdate.
     */
    @Test
    public void testBuscarSolicitudesVencidas() throws Exception {
        System.out.println("buscarSolicitudesVencidas");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        TimerSolicitudRequerimientosUpdate instance = (TimerSolicitudRequerimientosUpdate)container.getContext().lookup("java:global/classes/TimerSolicitudRequerimientosUpdate");
        instance.buscarSolicitudesVencidas();
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
