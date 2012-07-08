/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.ejb.EstadoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TipoPrioridadFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Jano
 */
@Stateless
public class SolicitudRequerimientoEJB implements SolicitudRequerimientoEJBLocal {
    
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private EstadoSolicitudRequerimientoFacadeLocal estadoSolicitudFacade;
    @EJB
    private TipoPrioridadFacadeLocal tipoPrioridadFacade;
    
    
    @Override
    public String generarCodigoConsulta(SolicitudRequerimiento solicitud) {
        return "123-456";
    }

    @Override
    public boolean validarCodigoConsulta(String codigoConsulta) {
        return false;
    }

    @Override
    public String enviarSolicitud(SolicitudRequerimiento solicitud, Funcionario solicitante) {
        Date fechaActual = new Date();
        solicitud.setFechaEnvio(fechaActual);
        solicitud.setFechaUltimaActualizacion(fechaActual);
//        solicitud.setPrioridadSolicitud(tipoPrioridadFacade.find(0));
//        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(0));
        solicitud.setCodigoConsulta(generarCodigoConsulta(solicitud));
//        solicitudFacade.create(solicitud);
        return solicitud.getCodigoConsulta();
    }
    
}
