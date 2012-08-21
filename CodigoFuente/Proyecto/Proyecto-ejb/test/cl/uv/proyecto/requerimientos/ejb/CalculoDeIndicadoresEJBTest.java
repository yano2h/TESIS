/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Alejandro
 */
@RunWith(Arquillian.class)
public class CalculoDeIndicadoresEJBTest {
    
    @EJB
    CalculoDeIndicadoresEJBLocal calculoDeIndicadoresEJB;
    
    public CalculoDeIndicadoresEJBTest() {
    }

    @Deployment
    public static JavaArchive createTestArchive() {
        System.out.println("createTestArchive -->1");
        JavaArchive test = ShrinkWrap.create(JavaArchive.class, "test.jar");
        System.out.println("createTestArchive -->2");
        test.addPackage(CalculoDeIndicadoresEJBLocal.class.getPackage());
        System.out.println("createTestArchive -->3");
        test.addPackage("cl.uv.proyecto.persistencia.entidades");
        System.out.println("createTestArchive -->4");
        test.addPackage("cl.uv.model.base.utils");
        System.out.println("createTestArchive -->5");
        test.addAsManifestResource(EmptyAsset.INSTANCE,ArchivePaths.create("beans.xml"));
        System.out.println("createTestArchive -->6");
        //test.addAsManifestResource("/src/conf/persistence.xml",ArchivePaths.create("persistence.xml"));
        test.addAsManifestResource("persistence.xml");
        System.out.println("createTestArchive -->7");
        return test;
    }
    

    @Test
    public void testContarSolicitudes() throws Exception {
        Long result = calculoDeIndicadoresEJB.contarSolicitudes(new Short("1"));
        Long expResult = 2L;
        assertEquals(expResult, result);
    }

}
