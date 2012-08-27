/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Jano
 */
public class AreaFacadeTest {

    private static EJBContainer container;
    private static Context ctx;

    public AreaFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
//        Map<String, Object> properties = new HashMap<String, Object>();  
//        properties.put(EJBContainer.MODULES, new File("build/classes"));
//        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "/Applications/NetBeans/glassfish-3.1.1/glassfish");
//        
//        container = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
//        ctx = container.getContext();
        Map<String, Object> properties = new HashMap<String, Object>();
        //properties.put(EJBContainer.MODULES, new File("build/classes"));
        properties.put(EJBContainer.MODULES, prepareModuleDirectory());
        properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "test/resources/domain.xml");

        container = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
        ctx = container.getContext();
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

    @Test
    public void testCount() throws Exception {
        System.out.println("count");

        AreaFacadeLocal instance = (AreaFacadeLocal) container.getContext().lookup("java:global/module/AreaFacade");
        int expResult = 3;
        int result = instance.count();
        assertEquals(expResult, result);
        container.close();
    }

    private static File prepareModuleDirectory() throws IOException {
        File result = new File("build/module");
        FileUtils.copyDirectory(new File("build/classes"), result);
        FileUtils.copyFile(new File("build/classes/META-INF/persistence.xml"),
                new File("build/module/META-INF/persistence.xml"));
        return result;
    }
}
