/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.test.junit.base.BaseTestEJB;
import cl.uv.test.junit.base.EntityUtils;
import cl.uv.test.junit.base.GenericTestEJB;
import java.util.List;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Jano
 */
public class ComentarioSolicitudFacadeTest extends GenericTestEJB<ComentarioSolicitudFacade, ComentarioSolicitudFacadeLocal> {
    
    public ComentarioSolicitudFacadeTest() {
        super(ComentarioSolicitudFacade.class);
    }
    
    @Override
    public void testCRUD() {
        // Test Insert y Find
        ComentarioSolicitud c = EntityUtils.createComentarioSolicitud();
        ejb.create(c);        
        assertEquals(c, ejb.find(c.getIdComentario()));
        
        // Test Edit
        String commentEdit = "Comentario Editado";
        c.setComentario(commentEdit);
        ejb.edit(c);
        assertEquals( commentEdit, (ejb.find(c.getIdComentario())).getComentario() );
        
        // Test Remove
        ejb.remove(c);
        assertNull(ejb.find(c.getIdComentario()));
    }

    @Test
    public void testFindAll() throws Exception {
        long sizeExp = 0;
        List<ComentarioSolicitud> listaComentarios = ejb.findAll();
        assertEquals(listaComentarios.size(), sizeExp);
    }

    @Override
    public void testCount()  {
        int expResult = 0;
        int result = ejb.count();
        assertEquals(expResult, result);
    }

    @Test
    public void testBuscarComentariosPorSolicitud() throws Exception {
        long sizeExp = 5;
        
        for (int i = 0; i < sizeExp; i++) {
            ComentarioSolicitud c = EntityUtils.createComentarioSolicitud();
            ejb.create(c);  
        }
        
        List<ComentarioSolicitud> lc = ejb.buscarComentariosPorSolicitud(EntityUtils.ID_SOLICITUD_TEST);
        
        assertEquals(sizeExp, lc.size());
        
        for (ComentarioSolicitud comentarioSolicitud : lc) {
            assertEquals( EntityUtils.ID_SOLICITUD_TEST, comentarioSolicitud.getSolicitudRequerimiento().getIdSolicitudRequerimiento());
            ejb.remove(comentarioSolicitud);
        }     
    }
}
