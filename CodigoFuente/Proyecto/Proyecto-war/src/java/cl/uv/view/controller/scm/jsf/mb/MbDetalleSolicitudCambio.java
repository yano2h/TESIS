/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyecto;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyectoPK;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbDetalleSolicitudCambio implements Serializable {

    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    @EJB
    private TareaScmProyectoFacadeLocal tareaScmProyectoFacade;
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    private SolicitudCambio solicitudCambio;

    public MbDetalleSolicitudCambio() {
    }

    @PostConstruct
    private void init() {
        solicitudCambio = (SolicitudCambio) JsfUtils.getValue("solicitudCambio");
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public SolicitudCambio getSolicitudCambio() {
        return solicitudCambio;
    }

    public void setSolicitudCambio(SolicitudCambio solicitudCambio) {
        this.solicitudCambio = solicitudCambio;
    }

    public void guardarAnalisis() {
        solicitudCambioFacade.guardarAnalisisImpacto(solicitudCambio, mbFuncionarioInfo.getFuncionario());
        JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Analisis del Impacto", "Fue guardado exitosamente"));
    }

    public void guardarEvaluacion() {
        solicitudCambioFacade.guardarEvaluacionSolicitud(solicitudCambio, mbFuncionarioInfo.getFuncionario());
        JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evaluación Solicitud", "Fue guardado exitosamente"));
    }

    public Boolean getEnabledAnalisis() {
        TareaScmProyecto t = tareaScmProyectoFacade.find(new TareaScmProyectoPK(Resources.getValueInteger("const", "Tarea_Analisis"),
                solicitudCambio.getProyecto().getIdProyecto()));
        return (solicitudCambio.getEstadoSolicitud().getIdEstadoSolicitudCambio().equals(Resources.getValueShort("const", "EstadoSC_ENVIADA"))
                && t != null
                && t.getResponsable().getRut().equals(mbFuncionarioInfo.getFuncionario().getRut()));
    }

    public Boolean getEnabledEvaluacion() {
        TareaScmProyecto t = tareaScmProyectoFacade.find(new TareaScmProyectoPK(Resources.getValueInteger("const", "Tarea_Evaluacion"),
                solicitudCambio.getProyecto().getIdProyecto()));
        return (solicitudCambio.getEstadoSolicitud().getIdEstadoSolicitudCambio().equals(Resources.getValueShort("const", "EstadoSC_ANALISADA"))
                && t != null
                && t.getResponsable().getRut().equals(mbFuncionarioInfo.getFuncionario().getRut()));
    }

    public Boolean getEnabledFormularioImpl() {
        TareaScmProyecto t = tareaScmProyectoFacade.find(new TareaScmProyectoPK(Resources.getValueInteger("const", "Tarea_Implementar"),
                solicitudCambio.getProyecto().getIdProyecto()));
        return (solicitudCambio.getEstadoSolicitud().getIdEstadoSolicitudCambio().equals(Resources.getValueShort("const", "EstadoSC_APROBADA"))
                && t != null
                && t.getResponsable().getRut().equals(mbFuncionarioInfo.getFuncionario().getRut()));
    }
}
