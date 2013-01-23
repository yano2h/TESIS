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
import java.util.ArrayList;
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
public class MbTareas extends MbBase {

    @EJB
    private TareaProyectoFacadeLocal tareaProyectoFacade;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    private TareaProyecto nuevaTarea;
    private TareaProyecto tareaSelected;
    private List<TareaProyecto> listaDeTareas;
    private List<Proyecto> listaProyectosEnQueParticipa;
    private SelectItem[] proyectosEnQueParticipa;
    private Proyecto proyectoSelected;

    public MbTareas() {
    }

    @PostConstruct
    private void init() {
        proyectoSelected = (Proyecto) getValueOfFlashContext("proyectoTarea");
        listaProyectosEnQueParticipa = proyectoFacade.buscarProyectosPorParticipante(getFuncionarioDisico());
        proyectosEnQueParticipa = JsfUtils.getSelectItems(listaProyectosEnQueParticipa, "getFullNameProyecto", "---");
        listaDeTareas = tareaProyectoFacade.buscarTareas(getFuncionarioDisico());
        nuevaTarea = generarNuevaTarea();
    }

    private TareaProyecto generarNuevaTarea() {
        TareaProyecto t = new TareaProyecto();
        t.setResponsableTarea(getFuncionarioDisico());
        t.setFechaCreacion(new Date());
        t.setFechaInicioPropuesta(new Date());
        t.setVisible(true);
        t.setNivelAvance((short) 0);
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

    public void guardarTarea() {
        nuevaTarea.setResponsableTarea(getFuncionarioDisico());
        tareaProyectoFacade.create(nuevaTarea);
        JsfUtils.performNavigation("misTareas", true);
    }

    public void guardar() {
        nuevaTarea.setProyecto(proyectoSelected);
        nuevaTarea.setResponsableTarea(getFuncionarioDisico());
        tareaProyectoFacade.create(nuevaTarea);
        MbTareasProyecto mbTareasProyecto = (MbTareasProyecto) JsfUtils.getValue("mbTareasProyecto");
        if (mbTareasProyecto.getTareasProyecto() == null) {
            mbTareasProyecto.setTareasProyecto(new ArrayList<TareaProyecto>());
        }
        mbTareasProyecto.getTareasProyecto().add(nuevaTarea);
        mbTareasProyecto.updatePorcentajeAvance();
    }

    public Proyecto getProyectoSelected() {
        return proyectoSelected;
    }

    public void setProyectoSelected(Proyecto proyectoSelected) {
        this.proyectoSelected = proyectoSelected;
    }

    public Date getMinDateFechaIni() {
        return new Date();
    }

    public SelectItem[] getProyectosEnQueParticipa() {
        return proyectosEnQueParticipa;
    }

    public void onRowSelect() {
        putValueOnFlashContext("tarea", tareaSelected);
        JsfUtils.performNavigation("detalleTarea", true);
    }

    public void onProyectoSelect() {
        if (proyectoSelected == null) {
            listaDeTareas = tareaProyectoFacade.buscarTareas(getFuncionarioDisico());
        } else {
            listaDeTareas = tareaProyectoFacade.buscarTareas(proyectoSelected, getFuncionarioDisico());
        }
    }
}
