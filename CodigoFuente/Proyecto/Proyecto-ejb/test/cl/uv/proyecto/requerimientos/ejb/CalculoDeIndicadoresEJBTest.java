/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.test.junit.base.BaseTestEJB;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author Alejandro
 */
public class CalculoDeIndicadoresEJBTest extends BaseTestEJB{
    
    private static CalculoDeIndicadoresEJBLocal ejbCalculoDeIndicadores;

    private static FuncionarioDisico funcionario;
    private static Area area;
    
    public CalculoDeIndicadoresEJBTest() {
    }

    @Before
    public void setUp() throws NamingException {
        ejbCalculoDeIndicadores = lookupBy(CalculoDeIndicadoresEJB.class);
        funcionario = new FuncionarioDisico(16775578, "");
        area = new Area((short)1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of contarSolicitudes method, of class CalculoDeIndicadoresEJB.
     */
    @Test
    public void testContarSolicitudes_FuncionarioDisico_Short() throws Exception {
        System.out.println("contarSolicitudes");
        Short idEstado = 1;
        Long expResult = 2L;
        
        Long result = ejbCalculoDeIndicadores.contarSolicitudes(funcionario, idEstado);
        assertEquals(expResult, result);
    }

    /**
     * Test of contarSolicitudes method, of class CalculoDeIndicadoresEJB.
     */
//    @Test
//    public void testContarSolicitudes_Area_Short() throws Exception {
//        System.out.println("contarSolicitudes");
//        Area area = null;
//        Short idEstado = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CalculoDeIndicadoresEJBLocal instance = (CalculoDeIndicadoresEJBLocal)container.getContext().lookup("java:global/classes/CalculoDeIndicadoresEJB");
//        Long expResult = null;
//        Long result = instance.contarSolicitudes(area, idEstado);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contarSolicitudes method, of class CalculoDeIndicadoresEJB.
//     */
//    @Test
//    public void testContarSolicitudes_Short() throws Exception {
//        System.out.println("contarSolicitudes");
//        Short idEstado = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CalculoDeIndicadoresEJBLocal instance = (CalculoDeIndicadoresEJBLocal)container.getContext().lookup("java:global/classes/CalculoDeIndicadoresEJB");
//        Long expResult = null;
//        Long result = instance.contarSolicitudes(idEstado);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of porcentajeSolicitudesAsignadas method, of class CalculoDeIndicadoresEJB.
//     */
//    @Test
//    public void testPorcentajeSolicitudesAsignadas_FuncionarioDisico() throws Exception {
//        System.out.println("porcentajeSolicitudesAsignadas");
//        FuncionarioDisico f = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CalculoDeIndicadoresEJBLocal instance = (CalculoDeIndicadoresEJBLocal)container.getContext().lookup("java:global/classes/CalculoDeIndicadoresEJB");
//        Float expResult = null;
//        Float result = instance.porcentajeSolicitudesAsignadas(f);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of porcentajeSolicitudesAsignadas method, of class CalculoDeIndicadoresEJB.
//     */
//    @Test
//    public void testPorcentajeSolicitudesAsignadas_Area() throws Exception {
//        System.out.println("porcentajeSolicitudesAsignadas");
//        Area a = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CalculoDeIndicadoresEJBLocal instance = (CalculoDeIndicadoresEJBLocal)container.getContext().lookup("java:global/classes/CalculoDeIndicadoresEJB");
//        Float expResult = null;
//        Float result = instance.porcentajeSolicitudesAsignadas(a);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of porcentajeRetrasos method, of class CalculoDeIndicadoresEJB.
//     */
//    @Test
//    public void testPorcentajeRetrasos_FuncionarioDisico() throws Exception {
//        System.out.println("porcentajeRetrasos");
//        FuncionarioDisico f = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CalculoDeIndicadoresEJBLocal instance = (CalculoDeIndicadoresEJBLocal)container.getContext().lookup("java:global/classes/CalculoDeIndicadoresEJB");
//        Float expResult = null;
//        Float result = instance.porcentajeRetrasos(f);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of porcentajeRetrasos method, of class CalculoDeIndicadoresEJB.
//     */
//    @Test
//    public void testPorcentajeRetrasos_Area() throws Exception {
//        System.out.println("porcentajeRetrasos");
//        Area a = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CalculoDeIndicadoresEJBLocal instance = (CalculoDeIndicadoresEJBLocal)container.getContext().lookup("java:global/classes/CalculoDeIndicadoresEJB");
//        Float expResult = null;
//        Float result = instance.porcentajeRetrasos(a);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of porcentajeRetrasos method, of class CalculoDeIndicadoresEJB.
//     */
//    @Test
//    public void testPorcentajeRetrasos() throws Exception {
//        System.out.println("porcentajeRetrasos");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CalculoDeIndicadoresEJBLocal instance = (CalculoDeIndicadoresEJBLocal)container.getContext().lookup("java:global/classes/CalculoDeIndicadoresEJB");
//        Float expResult = null;
//        Float result = instance.porcentajeRetrasos();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
