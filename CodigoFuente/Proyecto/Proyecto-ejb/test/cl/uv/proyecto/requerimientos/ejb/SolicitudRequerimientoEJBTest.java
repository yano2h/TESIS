/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import javax.ejb.embeddable.EJBContainer;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class SolicitudRequerimientoEJBTest {
    
    public SolicitudRequerimientoEJBTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generarCodigo method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testGenerarCodigo() throws Exception {
        System.out.println("generarCodigo");
        long num = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        String expResult = "";
        String result = instance.generarCodigo(num);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generarCodigoConsulta method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testGenerarCodigoConsulta() throws Exception {
        System.out.println("generarCodigoConsulta");
        SolicitudRequerimiento solicitud = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        String expResult = "";
        String result = instance.generarCodigoConsulta(solicitud);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validarCodigoConsulta method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testValidarCodigoConsulta() throws Exception {
        System.out.println("validarCodigoConsulta");
        String codigoConsulta = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        boolean expResult = false;
        boolean result = instance.validarCodigoConsulta(codigoConsulta);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarSolicitud method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testEnviarSolicitud() throws Exception {
        System.out.println("enviarSolicitud");
        SolicitudRequerimiento solicitud = null;
        Funcionario solicitante = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        String expResult = "";
        String result = instance.enviarSolicitud(solicitud, solicitante);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rechazarSolicitud method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testRechazarSolicitud() throws Exception {
        System.out.println("rechazarSolicitud");
        SolicitudRequerimiento solicitud = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        instance.rechazarSolicitud(solicitud);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarRespuestaDirecta method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testEnviarRespuestaDirecta() throws Exception {
        System.out.println("enviarRespuestaDirecta");
        SolicitudRequerimiento solicitud = null;
        Boolean enviarCopiaCorreo = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        instance.enviarRespuestaDirecta(solicitud, enviarCopiaCorreo);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarRespuestaManual method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testEnviarRespuestaManual() throws Exception {
        System.out.println("enviarRespuestaManual");
        SolicitudRequerimiento solicitud = null;
        String[] direcciones = null;
        String asunto = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        instance.enviarRespuestaManual(solicitud, direcciones, asunto);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transferirSolicitud method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testTransferirSolicitud() throws Exception {
        System.out.println("transferirSolicitud");
        SolicitudRequerimiento solicitud = null;
        Area nuevaAreaResponsable = null;
        String motivoTransferencia = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        instance.transferirSolicitud(solicitud, nuevaAreaResponsable, motivoTransferencia);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of asignarSolicitud method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testAsignarSolicitud() throws Exception {
        System.out.println("asignarSolicitud");
        SolicitudRequerimiento solicitud = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        instance.asignarSolicitud(solicitud);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iniciarSolicitud method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testIniciarSolicitud() throws Exception {
        System.out.println("iniciarSolicitud");
        SolicitudRequerimiento solicitud = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        instance.iniciarSolicitud(solicitud);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarRespuestaJefeArea method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testEnviarRespuestaJefeArea() throws Exception {
        System.out.println("enviarRespuestaJefeArea");
        SolicitudRequerimiento solicitud = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
        instance.enviarRespuestaJefeArea(solicitud);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
