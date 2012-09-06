/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Entregable;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class EntregableFacadeTest {
    
    public EntregableFacadeTest() {
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
     * Test of create method, of class EntregableFacade.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Entregable entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EntregableFacadeLocal instance = (EntregableFacadeLocal)container.getContext().lookup("java:global/classes/EntregableFacade");
        instance.create(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class EntregableFacade.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Entregable entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EntregableFacadeLocal instance = (EntregableFacadeLocal)container.getContext().lookup("java:global/classes/EntregableFacade");
        instance.edit(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class EntregableFacade.
     */
    @Test
    public void testRemove() throws Exception {
        System.out.println("remove");
        Entregable entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EntregableFacadeLocal instance = (EntregableFacadeLocal)container.getContext().lookup("java:global/classes/EntregableFacade");
        instance.remove(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class EntregableFacade.
     */
    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        Object id = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EntregableFacadeLocal instance = (EntregableFacadeLocal)container.getContext().lookup("java:global/classes/EntregableFacade");
        Entregable expResult = null;
        Entregable result = instance.find(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class EntregableFacade.
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EntregableFacadeLocal instance = (EntregableFacadeLocal)container.getContext().lookup("java:global/classes/EntregableFacade");
        List expResult = null;
        List result = instance.findAll();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRange method, of class EntregableFacade.
     */
    @Test
    public void testFindRange() throws Exception {
        System.out.println("findRange");
        int[] range = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EntregableFacadeLocal instance = (EntregableFacadeLocal)container.getContext().lookup("java:global/classes/EntregableFacade");
        List expResult = null;
        List result = instance.findRange(range);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class EntregableFacade.
     */
    @Test
    public void testCount() throws Exception {
        System.out.println("count");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EntregableFacadeLocal instance = (EntregableFacadeLocal)container.getContext().lookup("java:global/classes/EntregableFacade");
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
