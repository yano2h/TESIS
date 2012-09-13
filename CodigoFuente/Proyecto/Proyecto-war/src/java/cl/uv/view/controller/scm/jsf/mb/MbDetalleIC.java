/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ItemConfiguracionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
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
    
    private ItemConfiguracion ic;
    
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
    
    public void volver(){
        putValueOnFlashContext("proyecto", ic.getProyecto());
        JsfUtils.performNavigation("identificacionConfiguracion", true);
    }
    
    public void editar(){
        putValueOnFlashContext("item", ic);
        JsfUtils.performNavigation("editItemConfiguracion", true);
    }
    
}
