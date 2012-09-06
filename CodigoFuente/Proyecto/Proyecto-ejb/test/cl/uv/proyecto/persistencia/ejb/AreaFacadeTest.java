/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.test.junit.base.BaseTestEJB;
import cl.uv.test.junit.base.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jano
 */
public class AreaFacadeTest extends BaseTestEJB{

    private static AreaFacadeLocal ejb;
    
    public AreaFacadeTest() {
    }

    @Before
    public void setUp() throws NamingException {
        ejb = (AreaFacadeLocal) lookupBy(AreaFacade.class);
    }

    @After
    public void tearDown() {
        ejb = null;
    }

    @Test
    public void testLookup(){
        assertNotNull(ejb);
    }
    
    @Test
    public void testCount() throws Exception {
        List<Area> areas = new ArrayList<Area>();
        int expResult = 3; //Desarrollo, Redes, Fincom
        
        for (int i = 0; i < expResult; i++) {
            Area a = EntityUtils.createArea();
            ejb.create(a);
            areas.add(a);
        }
        
        int result = ejb.count();
        assertEquals(expResult, result);
        
        for (Area area : areas) {
            ejb.remove(area);
        }
        
        result = ejb.count();
        assertEquals(0, result);
    }
    
    @Test
    public void testCrudArea(){
        Area a = EntityUtils.createArea();
        ejb.create(a);
        Area areaTest = ejb.find(a.getIdArea());
        assertEquals(areaTest, a);
        
        a.setNombreArea("AreaEdit");
        ejb.edit(a);
        areaTest = ejb.find(a.getIdArea());
        assertEquals(areaTest.getNombreArea(), a.getNombreArea());
        
        //Restaurar estado bd
        ejb.remove(a);
        areaTest = ejb.find(a.getIdArea());
        assertNull(areaTest);
    }

}
