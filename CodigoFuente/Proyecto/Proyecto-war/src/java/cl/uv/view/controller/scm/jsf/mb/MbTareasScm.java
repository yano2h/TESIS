/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ParticipanteProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaScm;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyecto;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbTareasScm implements Serializable{

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private ParticipanteProyectoFacadeLocal participanteProyectoFacade;
    @EJB
    private TareaScmProyectoFacadeLocal tareaScmProyectoFacade;
    @EJB
    private TareaScmFacadeLocal tareaScmFacade;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private List<TareaScm> tareasScm;
    private List<TareaScmProyecto> tareasScmProyecto;
    private TareaScm tareaSelected;
    private Proyecto proyecto;
    
    public MbTareasScm() {
    }

    @PostConstruct
    private void init(){
        proyecto = (Proyecto) JsfUtils.getParametro("proyecto");
        tareasScm = tareaScmFacade.findAll();
        tareasScmProyecto = tareaScmProyectoFacade.buscarTareasSCMPorIdProyecto(proyecto.getIdProyecto());
        for (TareaScmProyecto tareaScmProyecto : tareasScmProyecto) {
            tareasScm.remove(tareaScmProyecto.getTareaScm());
        }
        System.out.println("INIT");
    }
    
    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public List<TareaScm> getTareasScm() {
        return tareasScm;
    }

    public TareaScm getTareaSelected() {
        return tareaSelected;
    }

    public void setTareaSelected(TareaScm tareaSelected) {
        this.tareaSelected = tareaSelected;
    }
    
    

    public SelectItem[] getSelectItemsTareasScm(){
        return JsfUtils.getSelectItems(tareasScm, "getNombreTarea",false);
    }
    
    public List<TareaScmProyecto> getTareasScmProyecto() {
        return tareasScmProyecto;
    }
    
    
   public void addTarea(){
       TareaScmProyecto newTarea = new TareaScmProyecto(tareaSelected.getIdTareaScm(), proyecto.getIdProyecto());
       newTarea.setTareaScm(tareaSelected);
       newTarea.setResponsable(mbFuncionarioInfo.getFuncionario());
       newTarea.setFechaInicio(new Date());
       newTarea.setFechaTermino(new Date());
       newTarea.setProyecto(proyecto);
       tareasScmProyecto.add(newTarea);
       tareasScm.remove(tareaSelected);
   }
   
   public void guardarCambios(){
       tareaScmProyectoFacade.guardarListaDeTareas(tareasScmProyecto);
       System.out.println("PASE");
         
   }
   
   public Boolean isDisabledAddTarea(){
       return (tareasScm.isEmpty());
   }
    
    
}
