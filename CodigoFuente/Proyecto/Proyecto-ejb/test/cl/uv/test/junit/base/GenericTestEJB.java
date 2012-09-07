/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.test.junit.base;

import cl.uv.proyecto.persistencia.ejb.ComentarioSolicitudFacade;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public abstract class GenericTestEJB <T,I>{
    private static final String TARGET_BUILD = "build/classes";
    private static final String MODULE_NAME = "embedded";
    private static final String TARGET_DIR = "build/" + MODULE_NAME;
    private static final String PATH_RESOURCES_TEST = "test/resources/";
    
    private static Context ctx;  
    private static EJBContainer container;
    public I ejb;
    private Class<T> implClassEjb;

    public GenericTestEJB(Class<T> implClassEjb) {
        this.implClassEjb = implClassEjb;
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, prepareModuleDirectory());
        properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", PATH_RESOURCES_TEST + "domain.xml");
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
        ctx = container.getContext();
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        container.close();
        FileUtils.cleanDirectory(new File(TARGET_DIR));
    }
    
     @Before
    public void setUp() throws NamingException {
        ejb = lookupBy(implClassEjb);
    }
    
    @After
    public void tearDown() {
        ejb = null;
    }
    
    protected <T> I lookupBy(Class<T> type) throws NamingException {
        System.out.println("Test Loookup");
        return (I) ctx.lookup("java:global/" + MODULE_NAME + "/" + type.getSimpleName());
    }
    
    private static File prepareModuleDirectory() throws IOException {
        File result = new File(TARGET_DIR);
        FileUtils.copyDirectory(new File(TARGET_BUILD), result);
        FileUtils.copyFile(new File(PATH_RESOURCES_TEST + "test-persistence.xml"),                
                           new File(TARGET_DIR + "/META-INF/persistence.xml"));
        return result;
    }
    
    @Test
    public void testLookup(){
        assertNotNull(ejb);
    }
    
    @Test
    public abstract void testCRUD();
    @Test
    public abstract void testCount();
}
