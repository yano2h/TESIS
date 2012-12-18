/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.model.base.utils.FileUtils;
import cl.uv.proyecto.persistencia.ejb.ArchivoProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ParticipanteProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.proyectos.ejb.ProyectoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbDetalleProyecto extends MbBase {

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private ParticipanteProyectoFacadeLocal participanteProyectoFacade;
    @EJB
    private ProyectoEJBLocal proyectoEJB;
    @EJB
    private ArchivoProyectoFacadeLocal archivoProyectoFacade;
    
    private Proyecto proyecto;

    @PostConstruct
    public void init() {
        System.out.println("INITTT");
        proyecto = (Proyecto) getValueOfFlashContext("proyecto");

        if (proyecto == null) {
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El proyecto no pudo ser encontrado"));
        } else {
            proyecto = proyectoFacade.find(proyecto.getIdProyecto());
            proyecto.setParticipantes(participanteProyectoFacade.buscarParticipantesProyecto(proyecto));
            proyecto.setArchivoProyectoList(archivoProyectoFacade.buscarArchivosPorProyecto(proyecto));
        }
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public void eliminarProyecto() {
        proyectoFacade.remove(proyecto);
    }

    public String verResumenAvance() {
        JsfUtils.addParametro("proyecto", proyecto);
        return "avanceProyecto?faces-redirect=true";
    }

    public String editarProyecto() {
        putValueOnFlashContext("proyecto", proyecto);
        return "editProyecto?faces-redirect=true";
    }

    public void cerrarProyecto() {
        proyectoEJB.cerrarProyecto(proyecto);
    }

    public void reabrirProyecto() {
        proyectoEJB.reabrirProyecto(proyecto);
    }
    
}
