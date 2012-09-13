/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbProyectosTareas extends MbBase {

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private TareaScmProyectoFacadeLocal tareaScmProyectoFacade;
    private List<Proyecto> proyectosIdentificacionConfiguracion;
    
    private Proyecto proyectoSelected;

    public List<Proyecto> getProyectosIdentificacionConfiguracion() {
        if (proyectosIdentificacionConfiguracion == null) {
            proyectosIdentificacionConfiguracion = proyectoFacade.buscarProyectoPorTareaFuncionario(getFuncionarioDisico(), new Integer(Resources.getValue("const", "Tarea_IC")));
        }
        return proyectosIdentificacionConfiguracion;
    }

    public Proyecto getProyectoSelected() {
        return proyectoSelected;
    }

    public void setProyectoSelected(Proyecto proyectoSelected) {
        this.proyectoSelected = proyectoSelected;
    }
    
    public void redirectIC(){
        putValueOnFlashContext("proyecto", proyectoSelected);
        JsfUtils.performNavigation("identificacionConfiguracion", true);
    }
    
    
}
