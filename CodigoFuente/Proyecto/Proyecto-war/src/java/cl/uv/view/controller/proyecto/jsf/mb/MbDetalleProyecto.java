/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ParticipanteProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.RolProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.ParticipanteProyecto;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.RolProyecto;
import cl.uv.proyecto.proyectos.ejb.ProyectoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class MbDetalleProyecto implements Serializable{

    @EJB 
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private ParticipanteProyectoFacadeLocal participanteProyectoFacade;
    @EJB
    private ProyectoEJBLocal proyectoEJB;
    @EJB
    private RolProyectoFacadeLocal rolProyectoFacade;
    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacadeLocal;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private Proyecto proyecto;
    private Integer idProyecto;

    private List<FuncionarioDisico> funcionariosDisponibles;
    private List<RolProyecto> rolesDisponibles;
    private List<ParticipanteProyecto> participantes;
    
    public MbDetalleProyecto() {
        
    }
    
    public void init() {
        proyecto = proyectoFacade.find(idProyecto);
        if (proyecto == null) {
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El proyecto con id " + idProyecto + " no pudo ser encontrado"));
        }else{
            proyecto.setParticipantes(participanteProyectoFacade.buscarParticipantesProyecto(proyecto));
            funcionariosDisponibles = funcionarioDisicoFacadeLocal.buscarFuncrionariosPorArea(proyecto.getParticipantes().get(0).getParticipante().getArea());
            rolesDisponibles = rolProyectoFacade.findAll();
            
//            for (ParticipanteProyecto participante : proyecto.getParticipantes()) {
//                funcionariosDisponibles.remove(participante.getParticipante());
//                rolesDisponibles.remove(participante.getRol());
//            }
            
            participantes = proyecto.getParticipantes();
        }
        
    }

    public List<ParticipanteProyecto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteProyecto> participantes) {
        this.participantes = participantes;
    }

    public SelectItem[] getFuncionariosDisponibles() {
        return  JsfUtils.getSelectItems(funcionariosDisponibles, "getNombreCompleto", false);
    }

    public SelectItem[] getRolesDisponiblesSelectItems() {
        return JsfUtils.getSelectItems(rolesDisponibles, "getNombreRol", false);
    }

    
    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public void eliminarProyecto(){
        proyectoFacade.remove(proyecto);
    }
    
    public String verResumenAvance(){
        JsfUtils.addParametro("proyecto", proyecto);
        return "avanceProyecto?faces-redirect=true";
    }
    
    public String editarProyecto(){
        return "editProyecto?faces-redirect=true&idProyecto="+proyecto.getIdProyecto();
    }
    
    public void cerrarProyecto(){
        proyectoEJB.cerrarProyecto(proyecto);
    }
    
    public String guardarCambios(){
        proyectoFacade.edit(proyecto);
        return "detalleProyecto_1?faces-redirect=true&idProyecto="+proyecto.getIdProyecto();
    }
}
