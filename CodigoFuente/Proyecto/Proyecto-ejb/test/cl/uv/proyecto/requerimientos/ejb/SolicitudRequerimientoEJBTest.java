/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacade;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.test.junit.base.BaseTestEJB;
import java.util.Date;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class SolicitudRequerimientoEJBTest extends BaseTestEJB{
    private final static String CODIGO_EXISTENTE = "ABCD";
    private final static String CODIGO_INEXISTENTE = "ZZZZ";
    
    private SolicitudRequerimientoEJBLocal solicitudEJB;
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    
    public SolicitudRequerimientoEJBTest() {
    }

    @Before
    public void setUp() throws NamingException {
        solicitudEJB = lookupBy(SolicitudRequerimientoEJB.class);
        solicitudFacade = lookupBy(SolicitudRequerimientoFacade.class);
    }
    
    @After
    public void tearDown() {
        solicitudEJB = null;
    }

    /**
     * Test of generarCodigo method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testGenerarCodigoNumCero() throws Exception {
        long num = 0L;
        String expResult = "";
        String result = solicitudEJB.generarCodigo(num);
        assertEquals(expResult, result);
    }

    @Test
    public void testGenerarCodigoNumNegativo() throws Exception {
        long num = -1L;
        String expResult = "";
        String result = solicitudEJB.generarCodigo(num);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGenerarCodigoNumUno() throws Exception {
        long num = 1L;
        String expResult = "q";
        String result = solicitudEJB.generarCodigo(num);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGenerarCodigoMaxLong() throws Exception {
        long num = Long.MAX_VALUE;
        String expResult = "2teCogGBXee";
        String result = solicitudEJB.generarCodigo(num);
        assertEquals(expResult, result);
    }
    
    /**
     * Verifica que el metodo generarCodigoConsulta no genera codigos de consulta
     * iguales aunque los rut sean similares y el instante de tiempo en que se 
     * genera sea muy proximo.
     */
    @Test
    public void testGenerarCodigoConsultaNoDuplicados() throws Exception {
        SolicitudRequerimiento s1 = new SolicitudRequerimiento();
        SolicitudRequerimiento s2 = new SolicitudRequerimiento();
        
        s1.setSolicitante(new Funcionario(16000578));
        s2.setSolicitante(new Funcionario(17000578));
        
        s1.setFechaEnvio(new Date());
        s2.setFechaEnvio(new Date());
        
        String r1 = solicitudEJB.generarCodigoConsulta(s1);
        String r2 = solicitudEJB.generarCodigoConsulta(s2);
        System.out.println("r1:"+r1);
        System.out.println("r2:"+r2);
        
        assertTrue( ( !r1.equals(r2) ) );
    }

    /**
     * Test of validarCodigoConsulta method, of class SolicitudRequerimientoEJB.
     */
    @Test
    public void testValidarCodigoConsultaExistente() throws Exception {
        boolean result = solicitudEJB.validarCodigoConsulta(CODIGO_EXISTENTE);
        assertFalse(result);
    }
    
    @Test
    public void testValidarCodigoConsultaInexistente() throws Exception {
        boolean result = solicitudEJB.validarCodigoConsulta(CODIGO_INEXISTENTE);
        assertTrue(result);
    }

//    @Test
//    public void testEnviarSolicitud() throws Exception {
//        SolicitudRequerimiento s = new SolicitudRequerimiento(99L);
//        s.setAreaResponsable(new Area((short)1));;
//        s.setAsunto("Asunto Junit");
//        s.setMensaje("Mensaje Junit");
//        s.setCodigoConsulta("AbCdEe");
//        
//        solicitudEJB.enviarSolicitud(s, new Funcionario(11111111));
//        
//        assertNotNull(solicitudFacade.find(99L));
//
//    }
//
//    /**
//     * Test of rechazarSolicitud method, of class SolicitudRequerimientoEJB.
//     */
//    @Test
//    public void testRechazarSolicitud() throws Exception {
//        System.out.println("rechazarSolicitud");
//        SolicitudRequerimiento solicitud = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
//        instance.rechazarSolicitud(solicitud);
//
//    }
//
//    /**
//     * Test of enviarRespuestaDirecta method, of class SolicitudRequerimientoEJB.
//     */
//    @Test
//    public void testEnviarRespuestaDirecta() throws Exception {
//        System.out.println("enviarRespuestaDirecta");
//        SolicitudRequerimiento solicitud = null;
//        Boolean enviarCopiaCorreo = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
//        instance.enviarRespuestaDirecta(solicitud, enviarCopiaCorreo);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of enviarRespuestaManual method, of class SolicitudRequerimientoEJB.
//     */
//    @Test
//    public void testEnviarRespuestaManual() throws Exception {
//        System.out.println("enviarRespuestaManual");
//        SolicitudRequerimiento solicitud = null;
//        String[] direcciones = null;
//        String asunto = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
//        instance.enviarRespuestaManual(solicitud, direcciones, asunto);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of transferirSolicitud method, of class SolicitudRequerimientoEJB.
//     */
//    @Test
//    public void testTransferirSolicitud() throws Exception {
//        System.out.println("transferirSolicitud");
//        SolicitudRequerimiento solicitud = null;
//        Area nuevaAreaResponsable = null;
//        String motivoTransferencia = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
//        instance.transferirSolicitud(solicitud, nuevaAreaResponsable, motivoTransferencia);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of asignarSolicitud method, of class SolicitudRequerimientoEJB.
//     */
//    @Test
//    public void testAsignarSolicitud() throws Exception {
//        System.out.println("asignarSolicitud");
//        SolicitudRequerimiento solicitud = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
//        instance.asignarSolicitud(solicitud);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of iniciarSolicitud method, of class SolicitudRequerimientoEJB.
//     */
//    @Test
//    public void testIniciarSolicitud() throws Exception {
//        System.out.println("iniciarSolicitud");
//        SolicitudRequerimiento solicitud = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
//        instance.iniciarSolicitud(solicitud);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of enviarRespuestaJefeArea method, of class SolicitudRequerimientoEJB.
//     */
//    @Test
//    public void testEnviarRespuestaJefeArea() throws Exception {
//        System.out.println("enviarRespuestaJefeArea");
//        SolicitudRequerimiento solicitud = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        SolicitudRequerimientoEJBLocal instance = (SolicitudRequerimientoEJBLocal)container.getContext().lookup("java:global/classes/SolicitudRequerimientoEJB");
//        instance.enviarRespuestaJefeArea(solicitud);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
