/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbIdentificacionConfiguracion implements Serializable{
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private ItemConfiguracion nuevoItem;
    
    public MbIdentificacionConfiguracion() {
        nuevoItem = new ItemConfiguracion();
    }

    @PostConstruct
    private void init(){
        
    }
    
    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public ItemConfiguracion getNuevoItem() {
        return nuevoItem;
    }

    public void setNuevoItem(ItemConfiguracion nuevoItem) {
        this.nuevoItem = nuevoItem;
    }
    
    public void addItem(){
        
    }
    
}
