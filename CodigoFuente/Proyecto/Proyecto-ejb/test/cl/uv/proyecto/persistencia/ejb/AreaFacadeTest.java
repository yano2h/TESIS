/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.test.junit.base.BaseTestEJB;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;

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
        int expResult = 0;
        int result = ejb.count();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testInsertAndRemove(){
        Area a = new Area( (short) 0,"Area de Prueba");
        ejb.create(a);
        Area areaTest = ejb.find(a.getIdArea());
        assertEquals(areaTest, a);
        ejb.remove(a);
    }

}
