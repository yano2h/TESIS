/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.test.integracion;

import cl.uv.proyecto.persistencia.ejb.*;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.TipoSolicitudRequerimiento;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJB;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import cl.uv.test.junit.base.BaseTestEJB;
import cl.uv.test.junit.base.EntityUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class IntegracionEnvioAtencionSolicitud extends BaseTestEJB {

    private SolicitudRequerimientoEJBLocal solicitudEjb;
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    private Area a;
    private Funcionario f;
    private TipoSolicitudRequerimiento t;
    private AreaFacadeLocal areaFacade;
    private FuncionarioFacadeLocal funcionarioFacade;
    private TipoSolicitudRequerimientoFacadeLocal tipoSolicitudRequerimientoFacade;

    public IntegracionEnvioAtencionSolicitud() {
    }

    @Before
    public void setUp() throws NamingException {
        solicitudEjb = lookupBy(SolicitudRequerimientoEJB.class);
        solicitudFacade = lookupBy(SolicitudRequerimientoFacade.class);
        areaFacade = lookupBy(AreaFacade.class);
        funcionarioFacade = lookupBy(FuncionarioFacade.class);
        tipoSolicitudRequerimientoFacade = lookupBy(TipoSolicitudRequerimientoFacade.class);

        a = EntityUtils.createArea();
        f = EntityUtils.createFuncionario();
        t = EntityUtils.createTipoSolicitudRequerimiento();

        areaFacade.create(a);
        funcionarioFacade.create(f);
        tipoSolicitudRequerimientoFacade.create(t);
    }

    @After
    public void tearDown() {
        tipoSolicitudRequerimientoFacade.remove(t);
        areaFacade.remove(a);
        funcionarioFacade.remove(f);


        solicitudEjb = null;
        solicitudFacade = null;
    }

    @Test
    public void enviarYAtender() {
        SolicitudRequerimiento s = EntityUtils.createSolicitudReq(a, t);
        String codigo;
        try {
            codigo = solicitudEjb.enviarSolicitud(s, f, null);
            assertTrue(!codigo.equals(""));

            List<SolicitudRequerimiento> solicitudesArea = solicitudFacade.buscarSolicitudesPorArea(a);

            SolicitudRequerimiento result = null;

            for (SolicitudRequerimiento solicitudRequerimiento : solicitudesArea) {
                if (solicitudRequerimiento.getCodigoConsulta().equals(s.getCodigoConsulta())) {
                    result = solicitudRequerimiento;
                    break;
                }
            }

            assertTrue(result != null);
            if (result != null) {
                solicitudFacade.remove(result);
            }
        } catch (AddressException ex) {
            Logger.getLogger(IntegracionEnvioAtencionSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(IntegracionEnvioAtencionSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
