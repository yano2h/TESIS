/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ItemConfiguracionFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbDetalleIC extends MbBase{

    @EJB
    private ItemConfiguracionFacadeLocal itemConfiguracionFacade;
    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    
    private ItemConfiguracion ic;
    private List<SolicitudCambio> solicitudesRelacionadas;
    private SolicitudCambio solicitudCambioSelected;
    
    public MbDetalleIC() {
    }
    
    @PostConstruct
    private void init(){
        ic = (ItemConfiguracion) getValueOfFlashContext("item");
        if (ic == null) {
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El item no pudo ser encontrado"));
        }
    }

    public ItemConfiguracion getIc() {
        return ic;
    }

    public void setIc(ItemConfiguracion ic) {
        this.ic = ic;
    }

    public List<SolicitudCambio> getSolicitudesRelacionadas() {
        return solicitudesRelacionadas;
    }

    public void setSolicitudesRelacionadas(List<SolicitudCambio> solicitudesRelacionadas) {
        this.solicitudesRelacionadas = solicitudesRelacionadas;
    }

    public SolicitudCambio getSolicitudCambioSelected() {
        return solicitudCambioSelected;
    }

    public void setSolicitudCambioSelected(SolicitudCambio solicitudCambioSelected) {
        this.solicitudCambioSelected = solicitudCambioSelected;
    }
    
    public void redirctToDetalle(){
        JsfUtils.addParametro("solicitudCambio", solicitudCambioSelected);
        JsfUtils.redirect("solicitudCambio.xhtml");
    }
    
    public void volver(){
        putValueOnFlashContext("proyecto", ic.getProyecto());
        JsfUtils.performNavigation("identificacionConfiguracion", true);
    }
    
    public void editar(){
        putValueOnFlashContext("item", ic);
        JsfUtils.performNavigation("editIdentificacionConfiguracion", true);
    }
    
    public void cargarSolicitudesDeCambio(){
        solicitudesRelacionadas = solicitudCambioFacade.buscarSolicitudesPorIC(ic);
    }
    
}
