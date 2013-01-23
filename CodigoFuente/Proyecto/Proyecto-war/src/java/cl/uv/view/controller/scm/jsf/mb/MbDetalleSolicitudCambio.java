/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FormularioImplementacionFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FormularioImplementacion;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyecto;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyectoPK;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbDetalleSolicitudCambio extends MbBase {

    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    @EJB
    private TareaScmProyectoFacadeLocal tareaScmProyectoFacade;
    @EJB
    private FormularioImplementacionFacadeLocal formularioImplementacionFacade;
    
    private SolicitudCambio solicitudCambio;
    private FormularioImplementacion formularioImplementacion;

    public MbDetalleSolicitudCambio() {
    }

    @PostConstruct
    private void init() {
        solicitudCambio = (SolicitudCambio) JsfUtils.getParametro("solicitudCambio");
        formularioImplementacion = solicitudCambio.getFormularioImplementacion();
        if(formularioImplementacion == null ){
            formularioImplementacion = new FormularioImplementacion();
        }
    }

    public SolicitudCambio getSolicitudCambio() {
        return solicitudCambio;
    }

    public void setSolicitudCambio(SolicitudCambio solicitudCambio) {
        this.solicitudCambio = solicitudCambio;
    }

    public FormularioImplementacion getFormularioImplementacion() {
        return formularioImplementacion;
    }

    public void setFormularioImplementacion(FormularioImplementacion formularioImplementacion) {
        this.formularioImplementacion = formularioImplementacion;
    }

    public void guardarAnalisis() {
        solicitudCambioFacade.guardarAnalisisImpacto(solicitudCambio, getFuncionarioDisico());
       // JsfUtils.addMessage(FacesMessage.SEVERITY_INFO, "Guardado Exitoso", "Los cambios realizados en la solicitud fueron guardados exitosamente");
    }

    public void guardarEvaluacion() {
        solicitudCambioFacade.guardarEvaluacionSolicitud(solicitudCambio, getFuncionarioDisico());
       // JsfUtils.addMessage(FacesMessage.SEVERITY_INFO, "Guardado Exitoso", "Los cambios realizados en la solicitud fueron guardados exitosamente");
    }
    
    public void guardarFormularioImpl(){
        formularioImplementacion.setIdFormularioImplementacion(solicitudCambio.getIdSolicitudCambio());
        formularioImplementacion.setSolicitudCambio(solicitudCambio);
        formularioImplementacionFacade.create(formularioImplementacion);
        solicitudCambio.setFormularioImplementacion(formularioImplementacion);
        
      //  JsfUtils.addMessage(FacesMessage.SEVERITY_INFO, "Guardado Exitoso", "El formulario de implmentacion fue guardado exitosamente");
    }

    public Boolean getEnabledAnalisis() {
        TareaScmProyecto t = tareaScmProyectoFacade.find(new TareaScmProyectoPK(Resources.getValueInteger("const", "Tarea_Analisis"),
                                                                                solicitudCambio.getProyecto().getIdProyecto()));
        return (solicitudCambio.getEstadoSolicitud().getIdEstadoSolicitudCambio().equals(Resources.getValueShort("const", "EstadoSC_ENVIADA"))
                && t != null
                && t.getResponsable().getRut().equals(getFuncionarioDisico().getRut()));
    }

    public Boolean getEnabledEvaluacion() {
        TareaScmProyecto t = tareaScmProyectoFacade.find(new TareaScmProyectoPK(Resources.getValueInteger("const", "Tarea_Evaluacion"),
                                                                                solicitudCambio.getProyecto().getIdProyecto()));
        return (solicitudCambio.getEstadoSolicitud().getIdEstadoSolicitudCambio().equals(Resources.getValueShort("const", "EstadoSC_ANALISADA"))
                && t != null
                && t.getResponsable().getRut().equals(getFuncionarioDisico().getRut()));
    }

    public Boolean getEnabledFormularioImpl() {
        TareaScmProyecto t = tareaScmProyectoFacade.find(new TareaScmProyectoPK(Resources.getValueInteger("const", "Tarea_Implementar"),
                                                                                solicitudCambio.getProyecto().getIdProyecto()));
        return (solicitudCambio.getEstadoSolicitud().getIdEstadoSolicitudCambio().equals(Resources.getValueShort("const", "EstadoSC_APROBADA"))
                && t != null
                && t.getResponsable().getRut().equals(getFuncionarioDisico().getRut()));
    }
}
