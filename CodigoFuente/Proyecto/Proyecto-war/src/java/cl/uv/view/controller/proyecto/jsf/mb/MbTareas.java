/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.TareaProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
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
public class MbTareas extends MbBase{

    @EJB
    private TareaProyectoFacadeLocal tareaProyectoFacade;
    
    private TareaProyecto nuevaTarea;
    
    public MbTareas() {
        nuevaTarea = new TareaProyecto();
    }

    public TareaProyecto getNuevaTarea() {
        return nuevaTarea;
    }

    public void setNuevaTarea(TareaProyecto nuevaTarea) {
        this.nuevaTarea = nuevaTarea;
    }
    
    
    
    
    
}
