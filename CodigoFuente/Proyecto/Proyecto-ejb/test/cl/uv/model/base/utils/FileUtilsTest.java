/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.model.base.utils;

import java.io.InputStream;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class FileUtilsTest {
    
    public FileUtilsTest() {
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
     * Test of writeUploadFile method, of class FileUtils.
     */
//    @Test
//    public void testWriteUploadFile() {
//        System.out.println("writeUploadFile");
//        String path = "";
//        InputStream input = null;
//        FileUtils.writeUploadFile(path, input);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of createDirectory method, of class FileUtils.
     */
    @Test
    public void testCreateDirectory() {
        System.out.println("createDirectory");
        String path = "D:/A/B/C";
        boolean expResult = true;
        assertEquals(expResult, FileUtils.createDirectory(path));
    }
}
