/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.test.junit.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author Alejandro
 */
public class BaseTestEJB {

    private static Context ctx;  
    private static EJBContainer container;
    
    private static final String TARGET_BUILD = "build/classes";
    private static final String MODULE_NAME = "embedded";
    private static final String TARGET_DIR = "build/" + MODULE_NAME;
    private static final String PATH_RESOURCES_TEST = "test/resources/";

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
        //FileUtils.cleanDirectory(new File(TARGET_DIR));
    }
    
    private static File prepareModuleDirectory() throws IOException {
        File result = new File(TARGET_DIR);
        FileUtils.copyDirectory(new File(TARGET_BUILD), result);
        FileUtils.copyFile(new File(PATH_RESOURCES_TEST + "test-persistence.xml"),                
                new File(TARGET_DIR + "/META-INF/persistence.xml"));
        return result;
    }

    protected <T> T lookupBy(Class<T> type) throws NamingException {
        return (T) ctx.lookup("java:global/" + MODULE_NAME + "/" + type.getSimpleName());
    }
    
    protected EntityManager lookupEntityManager() throws NamingException{
        return (EntityManager) ctx.lookup("java:global/EntityManager/Proyecto-ejbPU");
    }
}
