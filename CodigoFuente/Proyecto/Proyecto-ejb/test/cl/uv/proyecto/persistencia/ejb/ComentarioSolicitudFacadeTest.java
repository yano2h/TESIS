/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.test.junit.base.BaseTestEJB;
import cl.uv.test.junit.base.EntityUtils;
import java.util.List;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Jano
 */
public class ComentarioSolicitudFacadeTest extends BaseTestEJB {
    
    private ComentarioSolicitudFacadeLocal comentariosFacade;
    
    public ComentarioSolicitudFacadeTest() {
    }
    
    @Before
    public void setUp() throws NamingException {
        comentariosFacade = lookupBy(ComentarioSolicitudFacade.class);
    }
    
    @After
    public void tearDown() {
        comentariosFacade = null;
    }

    @Test
    public void testLookup(){
        assertNotNull(comentariosFacade);
    }

    @Test
    public void testCRUD() {
        // Test Insert y Find
        ComentarioSolicitud c = EntityUtils.createComentarioSolicitud();
        comentariosFacade.create(c);        
        assertEquals(c, comentariosFacade.find(c.getIdComentario()));
        
        // Test Edit
        String commentEdit = "Comentario Editado";
        c.setComentario(commentEdit);
        comentariosFacade.edit(c);
        assertEquals( commentEdit, (comentariosFacade.find(c.getIdComentario())).getComentario() );
        
        // Test Remove
        comentariosFacade.remove(c);
        assertNull(comentariosFacade.find(c.getIdComentario()));
    }

    @Test
    public void testFindAll() throws Exception {
        long sizeExp = 0;
        List<ComentarioSolicitud> listaComentarios = comentariosFacade.findAll();
        assertEquals(listaComentarios.size(), sizeExp);
    }

    @Test
    public void testCount() throws Exception {
        int expResult = 0;
        int result = comentariosFacade.count();
        assertEquals(expResult, result);
    }

    @Test
    public void testBuscarComentariosPorSolicitud() throws Exception {
        long sizeExp = 5;
        
        for (int i = 0; i < sizeExp; i++) {
            ComentarioSolicitud c = EntityUtils.createComentarioSolicitud();
            comentariosFacade.create(c);  
        }
        
        List<ComentarioSolicitud> lc = comentariosFacade.buscarComentariosPorSolicitud(EntityUtils.ID_SOLICITUD_TEST);
        
        assertEquals(sizeExp, lc.size());
        
        for (ComentarioSolicitud comentarioSolicitud : lc) {
            assertEquals( EntityUtils.ID_SOLICITUD_TEST, comentarioSolicitud.getSolicitudRequerimiento().getIdSolicitudRequerimiento());
            comentariosFacade.remove(comentarioSolicitud);
        }
        
    }
}
