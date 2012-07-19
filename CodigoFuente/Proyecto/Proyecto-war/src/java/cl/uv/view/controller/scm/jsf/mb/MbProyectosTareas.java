/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
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
public class MbProyectosTareas implements Serializable {

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private TareaScmProyectoFacadeLocal tareaScmProyectoFacade;
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    private List<Proyecto> proyectosIdentificacionConfiguracion;
    
    private Proyecto proyectoSelected;

    public MbProyectosTareas() {
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public List<Proyecto> getProyectosIdentificacionConfiguracion() {
        if (proyectosIdentificacionConfiguracion == null) {
            proyectosIdentificacionConfiguracion = proyectoFacade.buscarProyectoPorTareaFuncionario(mbFuncionarioInfo.getFuncionario(), new Integer(Resources.getValue("const", "Tarea_IC")));
        }
        return proyectosIdentificacionConfiguracion;
    }
//    public List<Proyecto> getProyectosIdentificacionConfiguracion(){
//    
//    }
//    
//    public List<Proyecto> getProyectosIdentificacionConfiguracion(){
//    
//    }

    public Proyecto getProyectoSelected() {
        return proyectoSelected;
    }

    public void setProyectoSelected(Proyecto proyectoSelected) {
        this.proyectoSelected = proyectoSelected;
        JsfUtils.addParametro("proyecto", proyectoSelected);
    }
    
    public void redirectIC(){
        JsfUtils.redirect("identificacionConfiguracion.xhtml");
    }
    
    
}
