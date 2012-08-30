/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.timer;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacade;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.test.junit.base.BaseTestEJB;
import javax.naming.NamingException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Alejandro
 */
public class TimerSolicitudRequerimientosUpdateTest extends BaseTestEJB {

    private static final long IDSOL_ASIGNADA_VENCIDA = 1L;
    private static final long IDSOL_VENCIDA = 2L;
    private static final long IDSOL_FINALIZADA_SIN_RESPUESTA = 3L;
    private static final long IDSOL_CERRADA = 4L;
    private static final long IDSOL_SIN_FECHA_VENCIMIENTO = 5L;
    private TimerSolicitudRequerimientosUpdate ejb;
    private SolicitudRequerimientoFacadeLocal solicitudFacade;

    public TimerSolicitudRequerimientosUpdateTest() {
    }

    @Before
    public void setUp() throws NamingException {
        ejb = lookupBy(TimerSolicitudRequerimientosUpdate.class);
        solicitudFacade = (SolicitudRequerimientoFacadeLocal) lookupBy(SolicitudRequerimientoFacade.class);
    }

    @After
    public void tearDown() {
        /*
         * Se restaura el estado de la solicitud que el procedimiento devio
         * modificar
         */
        SolicitudRequerimiento s = solicitudFacade.find(IDSOL_ASIGNADA_VENCIDA);
        s.getEstadoSolicitud().setIdEstadoSolicitudRequerimiento(Resources.getValueShort("Estados", "EstadoSR_ENVIADA"));
        solicitudFacade.edit(s);

        ejb = null;
        solicitudFacade = null;
    }

    /*
     * Verifica que el metodo buscarSolicitudesVencidas haya cambiado el estado
     * de una solicitud enviada cuya fecha de vencimiento ya fue superada
     */
    @Test
    public void testBuscarSolicitudesVencidasVerificarCambio() throws Exception {
        ejb.buscarSolicitudesVencidas();
        SolicitudRequerimiento s = solicitudFacade.find(IDSOL_ASIGNADA_VENCIDA);
        assertEquals(s.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento(),
                Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
    }

    /*
     * Verifica que el metodo buscarSolicitudesVencidas no haya cambiado el
     * estado de solicitudes que ya se encontraban en estado vencida
     */
    @Test
    public void testBuscarSolicitudesVencidasVerificarNoCambioVencida() throws Exception {
        ejb.buscarSolicitudesVencidas();
        SolicitudRequerimiento s = solicitudFacade.find(IDSOL_VENCIDA);
        assertEquals(s.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento(),
                Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
    }

    /*
     * Verifica que el metodo buscarSolicitudesVencidas no haya cambiado el
     * estado de solicitudes que se Cerraron despues de la fecha de vencimiento
     */
    @Test
    public void testBuscarSolicitudesVencidasVerificarNoCambioCerrada() throws Exception {
        ejb.buscarSolicitudesVencidas();
        SolicitudRequerimiento s = solicitudFacade.find(IDSOL_CERRADA);
        assertEquals(s.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento(),
                Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
    }

    /*
     * Verifica que el metodo buscarSolicitudesVencidas no haya cambiado el
     * estado de solicitudes que se Finalizaron sin respuesta despues de la
     * fecha de vencimiento
     */
    @Test
    public void testBuscarSolicitudesVencidasVerificarNoCambioFinalizadaSinRespuesta() throws Exception {
        ejb.buscarSolicitudesVencidas();
        SolicitudRequerimiento s = solicitudFacade.find(IDSOL_FINALIZADA_SIN_RESPUESTA);
        assertEquals(s.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento(),
                Resources.getValueShort("Estados", "EstadoSR_FINALIZADA_SIN_RESPUESTA"));
    }

    /*
     * Verifica que el metodo buscarSolicitudesVencidas no haya cambiado el
     * estado de solicitudes que no poseen fecha de vencimiento
     */
    @Test
    public void testBuscarSolicitudesVencidasVerificarNoCambioSolicitudSinFechaVencimiento() throws Exception {
        SolicitudRequerimiento s = solicitudFacade.find(IDSOL_SIN_FECHA_VENCIMIENTO);
        short idEstadoInicial = s.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento();

        ejb.buscarSolicitudesVencidas();
        s = solicitudFacade.find(IDSOL_SIN_FECHA_VENCIMIENTO);
        assertTrue((s.getEstadoSolicitud().getIdEstadoSolicitudRequerimiento() == idEstadoInicial));
    }
}
