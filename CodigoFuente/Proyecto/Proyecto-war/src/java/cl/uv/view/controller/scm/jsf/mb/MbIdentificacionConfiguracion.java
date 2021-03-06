/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ItemConfiguracionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
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
public class MbIdentificacionConfiguracion extends MbBase{
    
    @EJB
    private ItemConfiguracionFacadeLocal itemConfiguracionFacade;
    
    private List<ItemConfiguracion> listaItemConfiguracion;
    private ItemConfiguracion nuevoItem;
    private Proyecto proyecto;
    private ItemConfiguracion itemSelected;
    
    @PostConstruct
    private void init(){
        proyecto = (Proyecto)getValueOfFlashContext("proyecto");
        listaItemConfiguracion =  itemConfiguracionFacade.buscarItemsPorProyecto(proyecto);
        nuevoItem = new ItemConfiguracion();
        nuevoItem.setProyecto(proyecto);
    }

    public ItemConfiguracion getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(ItemConfiguracion itemSelected) {
        this.itemSelected = itemSelected;
    }

    public ItemConfiguracion getNuevoItem() {
        return nuevoItem;
    }

    public void setNuevoItem(ItemConfiguracion nuevoItem) {
        this.nuevoItem = nuevoItem;
    }

    public List<ItemConfiguracion> getListaItemConfiguracion() {
        return listaItemConfiguracion;
    }

    public void setListaItemConfiguracion(List<ItemConfiguracion> listaItemConfiguracion) {
        this.listaItemConfiguracion = listaItemConfiguracion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public void addItem(){
        listaItemConfiguracion.add(nuevoItem);
        nuevoItem = new ItemConfiguracion();
        nuevoItem.setProyecto(proyecto);
    }
    
    public void guardarItems(){
        if(listaItemConfiguracion.isEmpty()){
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", "Debe agregar algun item antes de poder guardar"));
        }else{
            itemConfiguracionFacade.guardarItems(listaItemConfiguracion);
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Guardado Exitoso", "Sus cambios han sido guardados satisfactoriamente"));  
        }   
    }
    
    public void redirectIC(ItemConfiguracion item){
        putValueOnFlashContext("item", item);
        JsfUtils.performNavigation("detalleItemConfiguracion", true);
    }
    
    public void redirectCancelar(){
        putValueOnFlashContext("proyecto", proyecto);
        JsfUtils.performNavigation("identificacionConfiguracion", true);
    }
}
