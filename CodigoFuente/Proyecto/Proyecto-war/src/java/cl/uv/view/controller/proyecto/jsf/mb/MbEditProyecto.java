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
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbEditProyecto extends MbBase {

    @EJB
    private RolProyectoFacadeLocal rolProyectoFacade;
    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacadeLocal;
    @EJB
    private ProyectoEJBLocal proyectoEJB;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private ParticipanteProyectoFacadeLocal participanteProyectoFacade;
    
    private Proyecto proyecto;
    private List<FuncionarioDisico> funcionariosDisponibles;
    private List<RolProyecto> rolesDisponibles;
    private List<ParticipanteProyecto> participantes;
    private FuncionarioDisico funcionarioSelected;
    private RolProyecto rolSelected;

    @PostConstruct
    public void init() {
        proyecto = (Proyecto) getValueOfFlashContext("proyecto");
        if (proyecto == null) {
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El proyecto no pudo ser encontrado"));
        } else {
            funcionariosDisponibles = funcionarioDisicoFacadeLocal.buscarFuncrionariosPorArea(proyecto.getParticipantes().get(0).getParticipante().getArea());
            rolesDisponibles = rolProyectoFacade.findAll();

            for (ParticipanteProyecto participante : proyecto.getParticipantes()) {
                funcionariosDisponibles.remove(participante.getParticipante());
                rolesDisponibles.remove(participante.getRol());
            }

            participantes = proyecto.getParticipantes();
        }

    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public FuncionarioDisico getFuncionarioSelected() {
        return funcionarioSelected;
    }

    public boolean isDisableBtnAgregar() {
        return (rolesDisponibles.isEmpty() || funcionariosDisponibles.isEmpty());
    }

    public void setFuncionarioSelected(FuncionarioDisico funcionarioSelected) {
        this.funcionarioSelected = funcionarioSelected;
    }

    public RolProyecto getRolSelected() {
        return rolSelected;
    }

    public void setRolSelected(RolProyecto rolSelected) {
        this.rolSelected = rolSelected;
    }

    public List<ParticipanteProyecto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteProyecto> participantes) {
        this.participantes = participantes;
    }

    public SelectItem[] getFuncionariosDisponibles() {
        return JsfUtils.getSelectItems(funcionariosDisponibles, "getNombreCompleto", false);
    }

    public SelectItem[] getRolesDisponibles() {
        return JsfUtils.getSelectItems(rolesDisponibles, "getNombreRol", false);
    }

    public String guardarCambios() {
        proyectoFacade.edit(proyecto);
        putValueOnFlashContext("proyecto", proyecto);
        return "detalleProyecto_1?faces-redirect=true";
    }

    public void agregarParticipante() {
        System.out.println("AGREGAR P:" + funcionarioSelected + " -" + proyecto);
        ParticipanteProyecto p = new ParticipanteProyecto(funcionarioSelected.getRut(), proyecto.getIdProyecto());
        p.setProyecto(proyecto);
        p.setRol(rolSelected);
        p.setParticipante(funcionarioSelected);
        participantes.add(p);
        System.out.println("SIZE P:" + participantes.size());
        funcionariosDisponibles.remove(p.getParticipante());
        rolesDisponibles.remove(p.getRol());
    }

    public void removerParticipante(ParticipanteProyecto p) {
        participantes.remove(p);
        funcionariosDisponibles.add(p.getParticipante());
        rolesDisponibles.add(p.getRol());
    }

    public void handleFuncionario() {
        funcionariosDisponibles = funcionarioDisicoFacadeLocal.buscarFuncrionariosPorArea(proyecto.getParticipantes().get(0).getParticipante().getArea());
        for (ParticipanteProyecto participante : participantes) {
            funcionariosDisponibles.remove(participante.getParticipante());
        }


    }

    public void handleRoles() {
        rolesDisponibles = rolProyectoFacade.findAll();
        for (ParticipanteProyecto participante : participantes) {
            rolesDisponibles.remove(participante.getRol());
        }
    }
    
    public void volverDetalleProyecto(){
        putValueOnFlashContext("proyecto", proyecto);
        JsfUtils.performNavigation("detalleProyecto_1", true);
    }
}
