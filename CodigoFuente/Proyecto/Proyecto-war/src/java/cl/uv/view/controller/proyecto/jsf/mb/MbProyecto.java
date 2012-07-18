/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
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
public class MbProyecto {

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private List<FuncionarioDisico> funcionariosArea;
    private List<FuncionarioDisico> funcionariosAreaDisponibles;
    private List<ParticipanteProyecto> participantes;
    private ParticipanteProyecto nuevoParticipanteProyecto;
    private ParticipanteProyecto participanteSelected;
    private FuncionarioDisico jefeProyecto;
    private RolProyecto rolProyecto;
    private Proyecto nuevoProyecto;
    
    public MbProyecto() {
    }
    
    @PostConstruct
    private void init(){
        nuevoProyecto = new Proyecto();
        nuevoParticipanteProyecto =  new ParticipanteProyecto();
        participantes = new ArrayList<ParticipanteProyecto>();
        funcionariosArea = funcionarioDisicoFacade.buscarFuncrionariosPorArea(mbFuncionarioInfo.getFuncionario().getArea());
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

    public RolProyecto getRolProyecto() {
        return rolProyecto;
    }

    public void setRolProyecto(RolProyecto rolProyecto) {
        this.rolProyecto = rolProyecto;
    }

    public List<ParticipanteProyecto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteProyecto> participantes) {
        this.participantes = participantes;
    }

    public ParticipanteProyecto getParticipanteSelected() {
        return participanteSelected;
    }

    public void setParticipanteSelected(ParticipanteProyecto participanteSelected) {
        this.participanteSelected = participanteSelected;
    }
    
    
    public List<FuncionarioDisico> getFuncionariosAreaDisponibles() {
        return funcionariosAreaDisponibles;
    }

    public void setFuncionariosAreaDisponibles(List<FuncionarioDisico> funcionariosAreaDisponibles) {
        this.funcionariosAreaDisponibles = funcionariosAreaDisponibles;
    }
    
    public SelectItem[] getItemsAvailableSelectManyNombreCompleto() {
        SelectItem[] listaItems = new SelectItem[funcionariosArea.size()];
        int i = 0;
        for (FuncionarioDisico f : funcionariosArea) {
            listaItems[i++] = new SelectItem(f, f.getNombre() + " " + f.getApellidoPaterno() + " " + f.getApellidoMaterno());
        }
        return listaItems;
    }
    
    public void agregarParticipante(){
        System.out.println("::"+nuevoParticipanteProyecto.getParticipante());
        funcionariosArea.remove(nuevoParticipanteProyecto.getParticipante());
        nuevoParticipanteProyecto.setProyecto(nuevoProyecto);
        participantes.add(nuevoParticipanteProyecto);
        nuevoParticipanteProyecto = new ParticipanteProyecto();
    }
    
    public void quitarParticipante(){
        funcionariosArea.add(participanteSelected.getParticipante());
        participantes.remove(participanteSelected);
    }
    
}
