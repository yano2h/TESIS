/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.test.junit.base.BaseTestEJB;
import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Jano
 */
public class ComentarioSolicitudFacadeTest extends BaseTestEJB{
    
    private ComentarioSolicitudFacadeLocal ejb;
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    
    public ComentarioSolicitudFacadeTest() {
    }
    
    @Before
    public void setUp() throws NamingException {
        ejb = (ComentarioSolicitudFacadeLocal)lookupBy(ComentarioSolicitudFacade.class);
        solicitudFacade = (SolicitudRequerimientoFacadeLocal)lookupBy(SolicitudRequerimientoFacade.class);
    }
    
    @After
    public void tearDown() {
        ejb = null;
        solicitudFacade = null;
    }

    @Test
    public void testLookup(){
        assertNotNull(ejb);
    
    }

    @Test
    public void testCRUD() {
        long idComentario = 1L;
        String comentarioInicial = "Comentario X";
        String comentarioFinal = "Comentario Y";
        
        SolicitudRequerimiento s1 = new SolicitudRequerimiento();
        s1.setAreaResponsable(new Area((short)1));
        s1.setFechaEnvio(new Date());
        s1.setFechaUltimaActualizacion(new Date());
        s1.setEstadoSolicitud(new EstadoSolicitudRequerimiento((short)0));
        s1.setPrioridadSolicitud(new TipoPrioridad((short) 0));
        s1.setAsunto("Asunto Junit");
        s1.setMensaje("Mensaje Junit");
        s1.setCodigoConsulta("AbCdEe");
        s1.setSolicitante(new Funcionario(11111111));
        s1.setTipoSolicitud(new TipoSolicitudRequerimiento((short)1));
        solicitudFacade.create(s1);
        
        ComentarioSolicitud c = new ComentarioSolicitud(idComentario, comentarioInicial, new Date(), true);
        c.setSolicitudRequerimiento(s1);
        c.setAutor(new Funcionario(11111111));
        ejb.create(c);        
        assertEquals("Error Insert or select",c, ejb.find(idComentario));
        
        c.setComentario(comentarioFinal);
        ejb.edit(c);
        assertEquals("Error Update",(ejb.find(idComentario)).getComentario(), comentarioFinal);
        
        ejb.remove(c);
        assertNull("Error remove",ejb.find(idComentario));
    }

    @Test
    public void testFindAll() throws Exception {
        long sizeExp = 0;
        List<ComentarioSolicitud> listaComentarios = ejb.findAll();
        assertEquals(listaComentarios.size(), sizeExp);
    }

    @Test
    public void testCount() throws Exception {
        int expResult = 0;
        int result = ejb.count();
        assertEquals(expResult, result);
    }

    @Test
    public void testBuscarComentariosPorSolicitud() throws Exception {
        SolicitudRequerimiento s1 = new SolicitudRequerimiento(99L);
        s1.setAreaResponsable(new Area((short)1));
        s1.setFechaEnvio(new Date());
        s1.setFechaUltimaActualizacion(new Date());
        s1.setEstadoSolicitud(new EstadoSolicitudRequerimiento((short)0));
        s1.setPrioridadSolicitud(new TipoPrioridad((short) 0));
        s1.setAsunto("Asunto Junit");
        s1.setMensaje("Mensaje Junit");
        s1.setCodigoConsulta("AbCdEe");
        s1.setSolicitante(new Funcionario(11111111));
        s1.setTipoSolicitud(new TipoSolicitudRequerimiento((short)1));
        
        for (int i = 0; i < 10; i++) {
            ComentarioSolicitud c = new ComentarioSolicitud((long)i+1);
            c.setFecha(new Date());
            c.setComentario("Comentario - "+i+1);
            c.setVisible(true);
            c.setSolicitudRequerimiento(s1);
            c.setAutor(new Funcionario(11111111));
            ejb.create(c);
        }
        
        
        List<ComentarioSolicitud> listaComentarios = ejb.buscarComentariosPorSolicitud(1L);
        assertTrue(listaComentarios.size()==10);
        
        for (ComentarioSolicitud comentarioSolicitud : listaComentarios) {
            ejb.remove(comentarioSolicitud);
        }
    }
}
