/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.requerimientos.ejb.CalculoDeIndicadoresEJBLocal;
import cl.uv.view.controller.base.utils.Resources;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbNotificacionesMenu extends MbBase{
    @EJB
    private CalculoDeIndicadoresEJBLocal calcIndicadoresEJB;
    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    
    public String getMsgSolicitudesArea(){
        Long numSol = calcIndicadoresEJB.contarSolicitudes(getFuncionarioDisico().getArea(), Resources.getValueShort("const", "EstadoSR_ENVIADA"));
        return (numSol > 0)? "("+numSol+")" : "";
    }
    
    public String getMsgMisSolicitudes(){
        Long numSol = calcIndicadoresEJB.contarSolicitudes(getFuncionarioDisico(), Resources.getValueShort("const", "EstadoSR_ASIGNADA"));
        return (numSol > 0)? "("+numSol+")" : "";
    }
    
    public String getMsgSolicitudAnalisis(){
        Long numSol = solicitudCambioFacade.contarSolicitudAnalisisPendiente(getFuncionarioDisico());
        return (numSol > 0)? "("+numSol+")" : "";
    }
    
    public String getMsgSolicitudEvaluacion(){
        Long numSol = solicitudCambioFacade.contarSolicitudEvaluacionPendiente(getFuncionarioDisico());
        return (numSol > 0)? "("+numSol+")" : "";
    }
    
    public String getMsgSolicitudImplementacion(){
        Long numSol = solicitudCambioFacade.contarSolicitudImplementacionPendiente(getFuncionarioDisico());
        return (numSol > 0)? "("+numSol+")" : "";
    }
}
