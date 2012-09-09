/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Entregable;
import cl.uv.test.junit.base.EntityUtils;
import cl.uv.test.junit.base.GenericTestEJB;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class EntregableFacadeTest extends GenericTestEJB<EntregableFacade, EntregableFacadeLocal> {
    
    public EntregableFacadeTest() {
        super(EntregableFacade.class);
    }

    @Override
    public void testCRUD() {
        // Test create
        Entregable e = EntityUtils.createEntregable();
        ejb.create(e);
        assertEquals(e, ejb.find(e.getIdEntregable()));
        
        // Test Edit
        String nuevoNombreEntregable = "Entregable Editado";
        e.setNombreEntregable(nuevoNombreEntregable);
        ejb.edit(e);
        assertEquals(nuevoNombreEntregable, ejb.find(e.getIdEntregable()).getNombreEntregable());
        
        // Test remove
        ejb.remove(e);
        assertNull(ejb.find(e.getIdEntregable()));
    }

    @Override
    public void testCount() {
        int expSize = 8;
        assertEquals(expSize, ejb.count());
    }

   
  
}
