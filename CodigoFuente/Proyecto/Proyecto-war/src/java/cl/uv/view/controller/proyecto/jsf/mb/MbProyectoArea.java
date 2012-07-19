/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
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
public class MbProyectoArea implements Serializable{

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private List<Proyecto> proyectosDelArea;
    private Proyecto proyectoSelected;
    
    public MbProyectoArea() {
    }
    
    @PostConstruct
    private void init(){
        proyectosDelArea = proyectoFacade.buscarProyectosPorArea(mbFuncionarioInfo.getFuncionario().getArea());
        
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public List<Proyecto> getProyectosDelArea() {
        return proyectosDelArea;
    }

    public Proyecto getProyectoSelected() {
        return proyectoSelected;
    }

    public void setProyectoSelected(Proyecto proyectoSelected) {
        this.proyectoSelected = proyectoSelected;
    }
    
    public void onRowSelect(){
        JsfUtils.redirect("detalleProyecto.xhtml?idProyecto=" + proyectoSelected.getIdProyecto());
    }
    
     public void onRowSelectSCM(){
        JsfUtils.addParametro("proyecto", proyectoSelected);
        JsfUtils.redirect("tareasScm.xhtml");
    }
    
    
}
