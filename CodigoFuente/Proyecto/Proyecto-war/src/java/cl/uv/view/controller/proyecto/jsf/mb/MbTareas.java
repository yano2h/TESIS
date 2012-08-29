/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbTareas extends MbBase{

    @EJB
    private TareaProyectoFacadeLocal tareaProyectoFacade;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    
    private TareaProyecto nuevaTarea;
    private TareaProyecto tareaSelected;
    private List<TareaProyecto> listaDeTareas;
    private List<Proyecto> listaProyectosEnQueParticipa;
    
    public MbTareas() {
    }

    @PostConstruct
    private void init(){
        listaProyectosEnQueParticipa = proyectoFacade.buscarProyectosPorParticipante(getFuncionarioDisico());
        listaDeTareas = tareaProyectoFacade.buscarTareasPorResponsable(getFuncionarioDisico());
        nuevaTarea = generarNuevaTarea();
    }
    
    private TareaProyecto generarNuevaTarea(){
        TareaProyecto t = new TareaProyecto();
        t.setResponsableTarea(getFuncionarioDisico());
        t.setFechaCreacion(new Date());
        t.setFechaInicioPropuesta(new Date());
        t.setVisible(true);
        t.setNivelAvance( (short) 0);
        return t;
    }
    
    public TareaProyecto getNuevaTarea() {
        return nuevaTarea;
    }

    public void setNuevaTarea(TareaProyecto nuevaTarea) {
        this.nuevaTarea = nuevaTarea;
    }

    public TareaProyecto getTareaSelected() {
        return tareaSelected;
    }

    public void setTareaSelected(TareaProyecto tareaSelected) {
        this.tareaSelected = tareaSelected;
    }

    public List<TareaProyecto> getListaDeTareas() {
        return listaDeTareas;
    }

    public void setListaDeTareas(List<TareaProyecto> listaDeTareas) {
        this.listaDeTareas = listaDeTareas;
    }
    
    public void guardarTarea(){
        nuevaTarea.setResponsableTarea(getFuncionarioDisico());
        tareaProyectoFacade.create(nuevaTarea);
        JsfUtils.performNavigation("misTareas", true);
    }
    
    public Date getMinDateFechaIni(){
        return new Date();
    }
    
    public SelectItem[] getProyectosEnQueParticipa(){
        return JsfUtils.getSelectItems(listaProyectosEnQueParticipa, "getNombre", "Seleccione un Proyecto");
    }
    
    public void onRowSelect(){
        putValueOnFlashContext("tarea", tareaSelected);
        JsfUtils.performNavigation("detalleTarea", true);
    }
}
