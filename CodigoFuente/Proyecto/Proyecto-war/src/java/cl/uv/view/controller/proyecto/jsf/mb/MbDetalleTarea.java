/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.TareaProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
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
public class MbDetalleTarea extends MbBase{

    @EJB
    private TareaProyectoFacadeLocal tareaProyectoFacade;
    
    private TareaProyecto tarea;
    private Short minAvance;
            
    public MbDetalleTarea() {
    }
    
    @PostConstruct
    private void init(){
        tarea = (TareaProyecto) getValueOfFlashContext("tarea");
        minAvance = tarea.getNivelAvance();
    }

    public TareaProyecto getTarea() {
        return tarea;
    }

    public void setTarea(TareaProyecto tarea) {
        this.tarea = tarea;
    }

    public Short getMinAvance() {
        return minAvance;
    }

    public void setMinAvance(Short minAvance) {
        this.minAvance = minAvance;
    }
    
    public void guardarCambios(){
        tareaProyectoFacade.edit(tarea);
    }
    
}
