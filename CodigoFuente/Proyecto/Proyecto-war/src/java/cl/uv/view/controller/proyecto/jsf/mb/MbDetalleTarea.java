/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.TareaProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import java.util.Date;
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
    private Boolean editar;
            
    public MbDetalleTarea() {
    }
    
    @PostConstruct
    private void init(){
        tarea = (TareaProyecto) getValueOfFlashContext("tarea");
        minAvance = tarea.getNivelAvance();
        editar = false;
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

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }
    
    public void guardarCambios(){

        tareaProyectoFacade.edit(tarea);
        activarDesactivarEdicion();
    }
    
    public void guardar(){
        tareaProyectoFacade.edit(tarea);
    }
    
    public void cancelarEdicion(){
        tarea = tareaProyectoFacade.find(tarea.getIdTareaProyecto());
        activarDesactivarEdicion();
    }
    
    public void activarDesactivarEdicion(){
        editar = !editar;
    }
}
