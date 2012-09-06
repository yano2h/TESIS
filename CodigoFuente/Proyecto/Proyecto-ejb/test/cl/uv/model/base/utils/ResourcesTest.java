/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.model.base.utils;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class ResourcesTest {
    
    private static String TEST_PROPERTIES = "Test";
    
    public ResourcesTest() {
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
     * Test of getValue method, of class Resources.
     */
    @Test
    public void testGetValue() {
        System.out.println("testGetValue");
        String expResult = "ABCD";
        String result = Resources.getValue(TEST_PROPERTIES, "string");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetValueConEspacios() {
        System.out.println("testGetValueConEspacios");
        String expResult = "A B C   D";
        String result = Resources.getValue(TEST_PROPERTIES, "stringConEspacios");
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getValueShort method, of class Resources.
     */
    @Test
    public void testGetValueShort() {
        System.out.println("testGetValueShort");
        Short expResult = Short.MAX_VALUE;
        Short result = Resources.getValueShort(TEST_PROPERTIES, "shortPositivo");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetValueShortNegativo() {
        System.out.println("testGetValueShortNegativo");
        Short expResult = Short.MIN_VALUE;
        Short result = Resources.getValueShort(TEST_PROPERTIES, "shortNegativo");
        assertEquals(expResult, result);
    }
    
    @Test(expected=NumberFormatException.class)
    public void testGetValueShortErrorEnString() {
        System.out.println("testGetValueShortErrorEnString");
        Short result = Resources.getValueShort(TEST_PROPERTIES, "string");
    }

    @Test(expected=NumberFormatException.class)
    public void testGetValueShortErrorValorMayorAShort() {
        System.out.println("testGetValueShortErrorValorMayorAShort");
        Short result = Resources.getValueShort(TEST_PROPERTIES, "shortFueraDeRango");
    }
    
    @Test(expected=NumberFormatException.class)
    public void testGetValueShortErrorValorDecimal() {
        System.out.println("testGetValueShortErrorValorDecimal");
        Short result = Resources.getValueShort(TEST_PROPERTIES, "decimal");
    }
    
    /**
     * Test of getValueInteger method, of class Resources.
     */
    
    @Test
    public void testGetValueInteger() {
        System.out.println("testGetValueInteger");
        Integer expResult = Integer.MAX_VALUE;
        Integer result = Resources.getValueInteger(TEST_PROPERTIES, "integerPositivo");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetValueIntegerNegativo() {
        System.out.println("testGetValueIntegerNegativo");
        Integer expResult = Integer.MIN_VALUE;
        Integer result = Resources.getValueInteger(TEST_PROPERTIES, "integerNegativo");
        assertEquals(expResult, result);
    }
    
    @Test(expected=NumberFormatException.class)
    public void testGetValueIntegerErrorEnString() {
        System.out.println("testGetValueIntegerErrorEnString");
        Integer result = Resources.getValueInteger(TEST_PROPERTIES, "string");
    }

    @Test(expected=NumberFormatException.class)
    public void testGetValueIntegerErrorValorMayorAInteger() {
        System.out.println("testGetValueIntegerErrorValorMayorAInteger");
        Integer result = Resources.getValueInteger(TEST_PROPERTIES, "integerFueraDeRango");
    }
    
    @Test(expected=NumberFormatException.class)
    public void testGetValueIntegerErrorValorDecimal() {
        System.out.println("testGetValueIntegerErrorValorDecimal");
        Integer result = Resources.getValueInteger(TEST_PROPERTIES, "decimal");
    }

    /**
     * Test of getValueLong method, of class Resources.
     */
    @Test
    public void testGetValueLong() {
        System.out.println("testGetValueLong");
        Long expResult = Long.MAX_VALUE;
        Long result = Resources.getValueLong(TEST_PROPERTIES, "longPositivo");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetValueLongNegativo() {
        System.out.println("testGetValueLongNegativo");
        Long expResult = Long.MIN_VALUE;
        Long result = Resources.getValueLong(TEST_PROPERTIES, "longNegativo");
        assertEquals(expResult, result);
    }
    
    @Test(expected=NumberFormatException.class)
    public void testGetValueLongErrorEnString() {
        System.out.println("testGetValueLongErrorEnString");
        Long result = Resources.getValueLong(TEST_PROPERTIES, "string");
    }

    @Test(expected=NumberFormatException.class)
    public void testGetValueLongErrorValorMayorALong() {
        System.out.println("testGetValueLongErrorValorMayorALong");
        Long result = Resources.getValueLong(TEST_PROPERTIES, "longFueraDeRango");
    }
    
    @Test(expected=NumberFormatException.class)
    public void testGetValueLongErrorValorDecimal() {
        System.out.println("testGetValueLongErrorValorDecimal");
        Long result = Resources.getValueLong(TEST_PROPERTIES, "decimal");
    }
    
    /**
     * Test of getPropertiesPath method, of class Resources.
     */
    @Test
    public void testGetPropertiesPath() {
        System.out.println("testGetPropertiesPath");
        String expResult = "cl.uv.model.base.properties.Test";
        String result = Resources.getPropertiesPath(TEST_PROPERTIES);
        assertEquals(expResult, result);
    }

    @Test(expected=MissingResourceException.class)
    public void testGetPropertiesPathNotFound() {
        System.out.println("testGetPropertiesPath");
        String result = Resources.getPropertiesPath("NotFound");
    }
    
    /**
     * Test of getPageList method, of class Resources.
     */
    @Test
    public void testGetPageList() {
        System.out.println("getPageList");
        ResourceBundle result = Resources.getPageList(TEST_PROPERTIES);
        assertTrue( result.containsKey("string") );
    }

    /**
     * Test of getMapPageList method, of class Resources.
     */
    @Test
    public void testGetMapPageList() {
        System.out.println("getMapPageList");
        Map result = Resources.getMapPageList(TEST_PROPERTIES);
        assertTrue( result.containsKey("string") );
    }
}
