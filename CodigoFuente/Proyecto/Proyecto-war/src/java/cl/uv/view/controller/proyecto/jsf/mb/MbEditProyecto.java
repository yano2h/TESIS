/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.file.ejb.FileManagerEJBLocal;
import cl.uv.proyecto.persistencia.ejb.*;
import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.proyecto.proyectos.ejb.ProyectoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.jsf.mb.MbFilesUpload;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
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
    @EJB
    private ArchivoProyectoFacadeLocal archivoProyectoFacade;
    @EJB
    private FileManagerEJBLocal fileManagerEJB;
    @ManagedProperty(value = "#{mbFilesUpload}")
    private MbFilesUpload mbFilesUpload;
    private String tipoInformacion;
    private List<ArchivoProyecto> archivosProyectoAdjuntos;
    private Proyecto proyecto;
    private List<FuncionarioDisico> funcionariosDisponibles;
    private List<RolProyecto> rolesDisponibles;
    private List<ParticipanteProyecto> participantes;
    private List<ArchivoProyecto> archivosPorBorrar;
    private FuncionarioDisico funcionarioSelected;
    private ParticipanteProyecto nuevoJefe = null;
    private ParticipanteProyecto antiguoJefe = null;
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

    public void setMbFilesUpload(MbFilesUpload mbFilesUpload) {
        this.mbFilesUpload = mbFilesUpload;
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

    public List<ArchivoProyecto> getArchivosProyectoAdjuntos() {
        return archivosProyectoAdjuntos;
    }

    public void setArchivosProyectoAdjuntos(List<ArchivoProyecto> archivosProyectoAdjuntos) {
        this.archivosProyectoAdjuntos = archivosProyectoAdjuntos;
    }

    public String getTipoInformacion() {
        return tipoInformacion;
    }

    public void setTipoInformacion(String tipoInformacion) {
        this.tipoInformacion = tipoInformacion;
    }

    public SelectItem[] getFuncionariosDisponibles() {
        return JsfUtils.getSelectItems(funcionariosDisponibles, "getNombreCompleto", false);
    }

    public SelectItem[] getRolesDisponibles() {
        return JsfUtils.getSelectItems(rolesDisponibles, "getNombreRol", false);
    }

    public String guardarCambios() {
        if (antiguoJefe != null && nuevoJefe != null) {
            participanteProyectoFacade.remove(antiguoJefe);
            participantes.remove(antiguoJefe);
            participanteProyectoFacade.create(nuevoJefe);
            participantes.add(nuevoJefe);
        }

        proyectoEJB.removerArchivosAdjuntos(archivosPorBorrar);
        if (archivosProyectoAdjuntos != null && !archivosProyectoAdjuntos.isEmpty()) {
            fileManagerEJB.adjuntarArchivosProyecto(archivosProyectoAdjuntos, proyecto);
        }

        System.out.println("EDIT PROYECTO");
        proyectoFacade.edit(proyecto);
        putValueOnFlashContext("proyecto", proyecto);
        return "detalleProyecto_1?faces-redirect=true";
    }

    public void agregarParticipante() {
        ParticipanteProyecto p = new ParticipanteProyecto(funcionarioSelected.getRut(), proyecto.getIdProyecto());
        p.setProyecto(proyecto);
        p.setRol(rolSelected);
        p.setParticipante(funcionarioSelected);
        participantes.add(p);
        funcionariosDisponibles.remove(p.getParticipante());
        rolesDisponibles.remove(p.getRol());
    }

    public void removerParticipante(ParticipanteProyecto p) {
        participantes.remove(p);
        funcionariosDisponibles.add(p.getParticipante());
        rolesDisponibles.add(p.getRol());
    }

    public void removerArchivo(ArchivoProyecto a) {
        if (archivosPorBorrar == null) {
            archivosPorBorrar = new ArrayList<ArchivoProyecto>();
        }
        archivosPorBorrar.add(a);
        proyecto.getArchivoProyectoList().remove(a);
    }

    public void handleFuncionario() {
        antiguoJefe = null;
        nuevoJefe = null;
        funcionariosDisponibles = funcionarioDisicoFacadeLocal.buscarFuncrionariosPorArea(proyecto.getAreaResponsable());

        for (ParticipanteProyecto p : participantes) {
            funcionariosDisponibles.remove(p.getParticipante());
            if (!p.getParticipante().getRut().equals(p.getParticipanteProyectoPK().getRutParticipante())) {
                System.out.println("DIF (" + p.getParticipante().getRut() + " != " + p.getParticipanteProyectoPK().getRutParticipante() + ")");
                nuevoJefe = new ParticipanteProyecto(p.getParticipante().getRut(), proyecto.getIdProyecto());
                nuevoJefe.setRol(p.getRol());
                nuevoJefe.setProyecto(proyecto);
                nuevoJefe.setParticipante(p.getParticipante());
                antiguoJefe = p;
            }
        }
    }

    public void handleRoles() {
        rolesDisponibles = rolProyectoFacade.findAll();
        for (ParticipanteProyecto participante : participantes) {
            rolesDisponibles.remove(participante.getRol());
        }
    }

    public void volverDetalleProyecto() {
        putValueOnFlashContext("proyecto", proyecto);
        JsfUtils.performNavigation("detalleProyecto_1", true);
    }

    public ParticipanteProyecto getAntiguoJefe() {
        return antiguoJefe;
    }

    public void setAntiguoJefe(ParticipanteProyecto antiguoJefe) {
        this.antiguoJefe = antiguoJefe;
    }

    public ParticipanteProyecto getNuevoJefe() {
        return nuevoJefe;
    }

    public void setNuevoJefe(ParticipanteProyecto nuevoJefe) {
        this.nuevoJefe = nuevoJefe;
    }

    public void addArchivoAdjunto() {
        System.out.println("--addArchivoAdjunto--");
        if (archivosProyectoAdjuntos == null) {
            archivosProyectoAdjuntos = new ArrayList<ArchivoProyecto>();
        }

        ArchivoAdjunto file = mbFilesUpload.extraerArchivoAdjunto();
        if (file != null) {
            System.out.println("Archivo Adjunto no es nulo:" + file.getNombre());
            ArchivoProyecto a = new ArchivoProyecto();
            a.setArchivoAdjunto(file);
            a.setProyecto(proyecto);
            a.setTipoInformacion(tipoInformacion);
            archivosProyectoAdjuntos.add(a);
            tipoInformacion = null;
        }
    }

    public void remove(ArchivoProyecto a) {
        for (int i = 0; i < archivosProyectoAdjuntos.size(); i++) {
            if (archivosProyectoAdjuntos.get(i).getArchivoAdjunto().equals(a.getArchivoAdjunto())) {
                archivosProyectoAdjuntos.remove(i);
                break;
            }
        }
    }
    
    public String getContadorArchivosAdjuntos(){
        return (archivosProyectoAdjuntos==null || archivosProyectoAdjuntos.isEmpty())?"":" ("+archivosProyectoAdjuntos.size()+")...";
    }
}
