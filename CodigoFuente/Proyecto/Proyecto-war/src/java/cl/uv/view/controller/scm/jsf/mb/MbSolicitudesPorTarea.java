/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbSolicitudesPorTarea {

    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private SolicitudCambio solicitudCambioSelected;
    private List<SolicitudCambio> solicitudesPendientes;
    private List<SolicitudCambio> solicitudesListas;
    
    public MbSolicitudesPorTarea() {
    }
    
    @PostConstruct
    private void init(){
        String url = JsfUtils.getFacesContext().getViewRoot().getViewId();
        if ( url.equals( Resources.getValue("pages", "analisis_solicitud")) ){
            solicitudesPendientes = solicitudCambioFacade.buscarSolicitudAnalisisPendiente(mbFuncionarioInfo.getFuncionario());
            solicitudesListas     = solicitudCambioFacade.buscarSolicitudAnalisadas(mbFuncionarioInfo.getFuncionario());
        }else if ( url .equals( Resources.getValue("pages", "evaluacion_solicitud") )){
            solicitudesPendientes = solicitudCambioFacade.buscarSolicitudEvaluacionPendiente(mbFuncionarioInfo.getFuncionario());
            solicitudesListas     = solicitudCambioFacade.buscarSolicitudEvaluadas(mbFuncionarioInfo.getFuncionario());
        }else if ( url .equals( Resources.getValue("pages", "implementacion_solicitud") )){
            solicitudesPendientes = solicitudCambioFacade.buscarSolicitudImplementacionPendiente(mbFuncionarioInfo.getFuncionario());
            solicitudesListas     = solicitudCambioFacade.buscarSolicitudImplementadas(mbFuncionarioInfo.getFuncionario());
        }
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }
    
    public void redirctToDetalle(){
        JsfUtils.addParametro("solicitudCambio", solicitudCambioSelected);
        JsfUtils.redirect("solicitudCambio.xhtml");
    }

    public SolicitudCambio getSolicitudCambioSelected() {
        return solicitudCambioSelected;
    }

    public void setSolicitudCambioSelected(SolicitudCambio solicitudCambioSelected) {
        this.solicitudCambioSelected = solicitudCambioSelected;
    }

    public List<SolicitudCambio> getSolicitudesListas() {
        return solicitudesListas;
    }

    public void setSolicitudesListas(List<SolicitudCambio> solicitudesListas) {
        this.solicitudesListas = solicitudesListas;
    }

    public List<SolicitudCambio> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public void setSolicitudesPendientes(List<SolicitudCambio> solicitudesPendientes) {
        this.solicitudesPendientes = solicitudesPendientes;
    }
    
    
}
