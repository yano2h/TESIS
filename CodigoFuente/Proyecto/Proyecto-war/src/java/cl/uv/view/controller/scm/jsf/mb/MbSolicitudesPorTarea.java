/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbSolicitudesPorTarea extends MbBase{

    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    
    private SolicitudCambio solicitudCambioSelected;
    private List<SolicitudCambio> solicitudesPendientes;
    private List<SolicitudCambio> solicitudesListas;
    
    public MbSolicitudesPorTarea() {
    }
    
    @PostConstruct
    private void init(){
        String url = JsfUtils.getFacesContext().getViewRoot().getViewId();
        if ( url.equals( Resources.getValue("pages", "analisis_solicitud")) ){
            System.out.println("ANALISIS");
            solicitudesPendientes = solicitudCambioFacade.buscarSolicitudAnalisisPendiente(getFuncionarioDisico());
            solicitudesListas     = solicitudCambioFacade.buscarSolicitudAnalisadas(getFuncionarioDisico());
        }else if ( url .equals( Resources.getValue("pages", "evaluacion_solicitud") )){
            solicitudesPendientes = solicitudCambioFacade.buscarSolicitudEvaluacionPendiente(getFuncionarioDisico());
            solicitudesListas     = solicitudCambioFacade.buscarSolicitudEvaluadas(getFuncionarioDisico());
        }else if ( url .equals( Resources.getValue("pages", "implementacion_solicitud") )){
            solicitudesPendientes = solicitudCambioFacade.buscarSolicitudImplementacionPendiente(getFuncionarioDisico());
            solicitudesListas     = solicitudCambioFacade.buscarSolicitudImplementadas(getFuncionarioDisico());
        }
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
