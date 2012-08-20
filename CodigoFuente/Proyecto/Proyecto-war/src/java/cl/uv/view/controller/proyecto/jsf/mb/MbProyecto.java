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
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbProyecto implements Serializable{

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @EJB
    private RolProyectoFacadeLocal rolProyectoFacade;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private ParticipanteProyectoFacadeLocal participanteProyectoFacade;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
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
    private void init(){
        nuevoProyecto = new Proyecto();
        nuevoParticipanteProyecto =  new ParticipanteProyecto();
        participantes = new ArrayList<ParticipanteProyecto>();
        funcionariosArea = funcionarioDisicoFacade.buscarFuncrionariosPorArea(mbFuncionarioInfo.getFuncionario().getArea());
        rolJefeProyecto = rolProyectoFacade.find(new Short(Resources.getValue("const", "RolProyecto_JP")));
        rolesProyecto = rolProyectoFacade.findAll();
        rolesProyecto.remove(rolJefeProyecto);
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
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
//        SelectItem[] listaItems = new SelectItem[funcionariosArea.size()];
//        int i = 0;
//        for (FuncionarioDisico f : funcionariosArea) {
//            listaItems[i++] = new SelectItem(f, f.getNombreCompleto());
//        }
//        return listaItems;
        return JsfUtils.getSelectItems(funcionariosArea, "getNombreCompleto", false);
    }
    
//    public void agregarParticipante(){
//        System.out.println("::"+nuevoParticipanteProyecto.getParticipante());
//        System.out.println("::"+nuevoParticipanteProyecto.getRol());
//        funcionariosArea.remove(nuevoParticipanteProyecto.getParticipante());
//     //   nuevoParticipanteProyecto.setProyecto(nuevoProyecto);
//        nuevoParticipanteProyecto.setParticipanteProyectoPK(new ParticipanteProyectoPK());
//        participantes.add(nuevoParticipanteProyecto);
//     //   nuevoParticipanteProyecto = new ParticipanteProyecto();
//    }
//    
//    public void quitarParticipante(ParticipanteProyecto p){
//        System.out.println("Eliminar"+p);
//        funcionariosArea.add(p.getParticipante());
//        System.out.println("Size"+participantes.size());
//        participantes.remove(p);
//        System.out.println("Size"+participantes.size());
//    }
//    
//    public void fijarJefeProyecto(){
//        System.out.println("Jefe:"+jefeProyecto);
//        
//        ParticipanteProyecto p = new ParticipanteProyecto();
//        p.setRol(rolJefeProyecto);
//        p.setParticipante(jefeProyecto);
//        participantes.add(p);
//        funcionariosArea.remove(jefeProyecto);
//    }
    
    public void crearProyecto(){
        proyectoFacade.create(nuevoProyecto);
        ParticipanteProyecto p = new ParticipanteProyecto(jefeProyecto.getRut(), nuevoProyecto.getIdProyecto());
        p.setProyecto(nuevoProyecto);
        p.setParticipante(jefeProyecto);
        p.setRol(rolJefeProyecto);
        participanteProyectoFacade.create(p);
    }
}