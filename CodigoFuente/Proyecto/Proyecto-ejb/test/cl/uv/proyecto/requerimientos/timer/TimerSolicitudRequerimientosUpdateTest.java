/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.timer;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacade;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import cl.uv.test.junit.base.BaseTestEJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class TimerSolicitudRequerimientosUpdateTest extends BaseTestEJB {
    private EntityManager em;
    
    private TimerSolicitudRequerimientosUpdate ejb;
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    //SolicitudRequerimientoEJBLocal solicitudEJB;
    
    public TimerSolicitudRequerimientosUpdateTest() {
    }

    @Before
    public void setUp() throws NamingException {
        ejb = lookupBy(TimerSolicitudRequerimientosUpdate.class);
        solicitudFacade = (SolicitudRequerimientoFacadeLocal) lookupBy(SolicitudRequerimientoFacade.class);
        em = lookupEntityManager();
    }
    
    @After
    public void tearDown() {
        ejb = null;
        solicitudFacade = null;
    }

    /**
     * Test of buscarSolicitudesVencidas method, of class TimerSolicitudRequerimientosUpdate.
     */
    @Test
    public void testBuscarSolicitudesVencidas() throws Exception {
        SolicitudRequerimiento solTest =  solicitudFacade.find(1L);
        assertFalse(solTest.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento() == Resources.getValueShort("Estados","EstadoSR_VENCIDA"));
        
        ejb.buscarSolicitudesVencidas();
        solTest =  solicitudFacade.find(1L);
        assertEquals(solTest.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento(), Resources.getValueShort("Estados","EstadoSR_VENCIDA"));
        
        //Restaurar estado
        String query = "UPDATE SolicitudRequerimiento s SET s.idSolicitudRequerimiento = 0 WHERE s.estadoSolicitud.idEstadoSolicitudRequerimiento = 1";
        Query q = em.createQuery(query);
        q.executeUpdate();
    }
}
