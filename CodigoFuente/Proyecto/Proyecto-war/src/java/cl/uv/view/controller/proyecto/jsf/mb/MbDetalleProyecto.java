/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ParticipanteProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

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
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private Proyecto proyecto;
    private Integer idProyecto;
    
    public MbDetalleProyecto() {
    }
    
    public void init() {
        proyecto = proyectoFacade.find(idProyecto);
        if (proyecto == null) {
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El proyecto con id " + idProyecto + " no pudo ser encontrado"));
        }else{
            proyecto.setParticipantes(participanteProyectoFacade.buscarParticipantesProyecto(proyecto));
        }
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
}
