/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ParticipanteProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.RolProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.proyecto.proyectos.ejb.ProyectoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.jsf.mb.MbFilesUpload;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
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
public class MbProyecto extends MbBase {

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @EJB
    private RolProyectoFacadeLocal rolProyectoFacade;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private ParticipanteProyectoFacadeLocal participanteProyectoFacade;
    @EJB
    private ProyectoEJBLocal proyectoEJB;
    @ManagedProperty(value = "#{mbFilesUpload}")
    private MbFilesUpload mbFilesUpload;
    private String tipoInformacion;
    private List<ArchivoProyecto> archivosProyectoAdjuntos;
    private List<FuncionarioDisico> funcionariosArea;
    private List<ParticipanteProyecto> participantes;
    private ParticipanteProyecto nuevoParticipanteProyecto;
    private FuncionarioDisico jefeProyecto;
    private List<RolProyecto> rolesProyecto;
    private RolProyecto rolJefeProyecto;
    private Proyecto nuevoProyecto;

    public MbProyecto() {
    }

    @PostConstruct
    private void init() {
        buildNuevoProyecto();
        funcionariosArea = funcionarioDisicoFacade.buscarFuncrionariosPorArea(getFuncionarioDisico().getArea());
        rolJefeProyecto = rolProyectoFacade.find(new Short(Resources.getValue("const", "RolProyecto_JP")));
        rolesProyecto = rolProyectoFacade.findAll();
        rolesProyecto.remove(rolJefeProyecto);
    }

    public void setMbFilesUpload(MbFilesUpload mbFilesUpload) {
        this.mbFilesUpload = mbFilesUpload;
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
    
    
    public Proyecto getNuevoProyecto() {
        return nuevoProyecto;
    }

    public void setNuevoProyecto(Proyecto nuevoProyecto) {
        this.nuevoProyecto = nuevoProyecto;
    }

    public List<FuncionarioDisico> getFuncionariosArea() {
        return funcionariosArea;
    }

    public void setFuncionariosArea(List<FuncionarioDisico> funcionariosArea) {
        this.funcionariosArea = funcionariosArea;
    }

    public ParticipanteProyecto getNuevoParticipanteProyecto() {
        return nuevoParticipanteProyecto;
    }

    public void setNuevoParticipanteProyecto(ParticipanteProyecto nuevoParticipanteProyecto) {
        this.nuevoParticipanteProyecto = nuevoParticipanteProyecto;
    }

    public FuncionarioDisico getJefeProyecto() {
        return jefeProyecto;
    }

    public void setJefeProyecto(FuncionarioDisico jefeProyecto) {
        this.jefeProyecto = jefeProyecto;
    }

    public SelectItem[] getRolesDisponibles() {
        return JsfUtils.getSelectItems(rolesProyecto, "getNombreRol", false);
    }

    public List<ParticipanteProyecto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteProyecto> participantes) {
        this.participantes = participantes;
    }

    public SelectItem[] getItemsAvailableSelectManyNombreCompleto() {
        return JsfUtils.getSelectItems(funcionariosArea, "getNombreCompleto", false);
    }

    public void crearProyecto() {
        nuevoProyecto.setArchivoProyectoList(archivosProyectoAdjuntos);
        proyectoEJB.crearProyecto(nuevoProyecto, jefeProyecto);
    }

    public void buildNuevoProyecto() {
        nuevoProyecto = new Proyecto();
        String s = proyectoEJB.sugerirCodigoInterno(getFuncionarioDisico().getArea());
        nuevoProyecto.setCodigoInterno(s);
        nuevoProyecto.setAreaResponsable(getFuncionarioDisico().getArea());
        nuevoParticipanteProyecto = new ParticipanteProyecto();
        participantes = new ArrayList<ParticipanteProyecto>();
        jefeProyecto = new FuncionarioDisico();
    }

    public void validateCode(AjaxBehaviorEvent e) {
        boolean exist = proyectoFacade.existCode(nuevoProyecto.getCodigoInterno());
        if (exist) {
            FacesMessage f = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El codigo " + nuevoProyecto.getCodigoInterno() + " ya existe.");
            JsfUtils.getFacesContext().addMessage(e.getComponent().getClientId(), f);
        }
    }

    public void goDetalleProyecto() {
        putValueOnFlashContext("proyecto", nuevoProyecto);
        JsfUtils.performNavigation("detalleProyecto_1", true);
    }
    
    public void addArchivoAdjunto(){
        System.out.println("--addArchivoAdjunto--");
        if (archivosProyectoAdjuntos==null) {
            archivosProyectoAdjuntos = new ArrayList<ArchivoProyecto>();
        }
        
        ArchivoAdjunto file = mbFilesUpload.extraerArchivoAdjunto();
        if (file!=null) {
            System.out.println("Archivo Adjunto no es nulo:"+file.getNombre());
            ArchivoProyecto a = new ArchivoProyecto();
            a.setArchivoAdjunto(file);
            a.setProyecto(nuevoProyecto);
            a.setTipoInformacion(tipoInformacion);
            archivosProyectoAdjuntos.add(a);
            tipoInformacion = null;
        }
    }
    
    public void remove(ArchivoProyecto a){
        for (int i = 0; i < archivosProyectoAdjuntos.size(); i++) {
            if ( archivosProyectoAdjuntos.get(i).getArchivoAdjunto().equals(a.getArchivoAdjunto())) {
                archivosProyectoAdjuntos.remove(i);
                break;
            }
        }
    }

}
