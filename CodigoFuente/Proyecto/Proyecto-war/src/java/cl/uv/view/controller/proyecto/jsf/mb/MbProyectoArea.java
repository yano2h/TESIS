/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
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
public class MbProyectoArea extends MbBase{

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    
    private List<Proyecto> proyectosDelArea;
    private Proyecto proyectoSelected;
    
    @PostConstruct
    private void init(){
        proyectosDelArea = proyectoFacade.buscarProyectosPorArea(getFuncionarioDisico().getArea());      
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
        putValueOnFlashContext("proyecto", proyectoSelected);
        JsfUtils.performNavigation("detalleProyecto_1", true);
    }
    
     public void onRowSelectSCM(){
        JsfUtils.addParametro("proyecto", proyectoSelected);
        JsfUtils.redirect("tareasScm.xhtml");
    }
    
    
}
