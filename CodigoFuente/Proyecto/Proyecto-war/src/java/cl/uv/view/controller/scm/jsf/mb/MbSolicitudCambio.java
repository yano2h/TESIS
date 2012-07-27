/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ItemConfiguracionFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbSolicitudCambio implements Serializable{

    @EJB
    private ItemConfiguracionFacadeLocal itemConfiguracionFacade;
    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private List<ItemConfiguracion> listaItemsConfiguracion;
    private SolicitudCambio nuevaSolicitud;
    
    public MbSolicitudCambio() {
        nuevaSolicitud = new SolicitudCambio();
        listaItemsConfiguracion = new ArrayList<ItemConfiguracion>();
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public SolicitudCambio getNuevaSolicitud() {
        return nuevaSolicitud;
    }

    public void setNuevaSolicitud(SolicitudCambio nuevaSolicitud) {
        this.nuevaSolicitud = nuevaSolicitud;
    }
    
    public SelectItem[] getSelectItemsItemsConfiguracion(){
        if (listaItemsConfiguracion.isEmpty()) {
            SelectItem[] s = new SelectItem[1];
            s[0] = new SelectItem("", "---");
            return s;
        }
        return JsfUtils.getSelectItems(listaItemsConfiguracion, "getNombreItemConfiguracion", true);
    }
    
    public void changeProyecto(){
        listaItemsConfiguracion =  itemConfiguracionFacade.buscarItemsPorProyecto(nuevaSolicitud.getProyecto());
    }
    
    public void enviarSolicitud(){
        solicitudCambioFacade.enviarSolicitudCambio(nuevaSolicitud, mbFuncionarioInfo.getFuncionario());
        JsfUtils.addMessage(FacesMessage.SEVERITY_INFO, "Envio Exitoso", "Su solicitud han sido enviada con exito.");
        nuevaSolicitud = new SolicitudCambio();
    }
    
}
