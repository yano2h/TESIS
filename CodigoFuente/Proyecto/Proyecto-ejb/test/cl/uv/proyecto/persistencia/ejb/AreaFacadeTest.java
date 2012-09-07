/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.test.junit.base.EntityUtils;
import cl.uv.test.junit.base.GenericTestEJB;
import static org.junit.Assert.*;

/**
 *
 * @author Jano
 */
public class AreaFacadeTest extends GenericTestEJB<AreaFacade, AreaFacadeLocal> {
    
    public AreaFacadeTest() {
        super(AreaFacade.class);
    }

    @Override
    public void testCRUD() {
        System.out.println("Test CRUD");
        // Test Create
        Area a = EntityUtils.createArea();
        ejb.create(a);
        Area areaTest = ejb.find(a.getIdArea());
        assertEquals(areaTest, a);
        
        // Test Edit
        String nuevoNombreArea = "AreaEdit";
        a.setNombreArea(nuevoNombreArea);
        ejb.edit(a);
        assertEquals(nuevoNombreArea, ejb.find(a.getIdArea()).getNombreArea());
        
        // Test Remove
        ejb.remove(a);
        assertNull(ejb.find(a.getIdArea()));
    }

    @Override
    public void testCount() {
        System.out.println("Test Count");
        int expResult = 3; //Desarrollo, Redes, Fincom
        int result = ejb.count();
        assertEquals(expResult, result);
    }
}
