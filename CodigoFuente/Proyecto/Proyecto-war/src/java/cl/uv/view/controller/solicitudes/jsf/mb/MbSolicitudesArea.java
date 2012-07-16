/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.*;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.persistencia.jsf.mb.AreaController;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbSolicitudesArea implements Serializable {

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private SolicitudRequerimientoEJBLocal solicitudEJB;
    
    
    private String codigoConsulta = "";
    private SolicitudRequerimiento selectedSolicitud;
    private Boolean enviarMail = false;
    private String respuesta = "";
    private String emailsRespuestaManual = "";
    private String asuntoRespuestaManual = "";
    private String motivoTransferencia = "";
    private Area nuevaArea;
    private int MEDIA_HORA = 1000 * 60 * 30;
    private List<SolicitudRequerimiento> solicitudesArea;
    private SelectItem[] areasParaTransferencia;
    private List<FuncionarioDisico> funcionariosArea;
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    @ManagedProperty(value = "#{mbDetalleSolicitud}")
    private MbDetalleSolicitud mbDetalleSolicitud;
    @ManagedProperty(value = "#{areaController}")
    private AreaController areaController;

    public MbSolicitudesArea() {
    }

    @PostConstruct
    public void init() {
        solicitudesArea = solicitudFacade.buscarSolicitudesPorArea(mbFuncionarioInfo.getFuncionario().getArea());
        SelectItem[] temp = areaController.getItemsAvailableSelectMany();
        areasParaTransferencia = new SelectItem[temp.length - 1];
        int cont = 0;
        for (SelectItem s : temp) {
            if (!s.getValue().equals(mbFuncionarioInfo.getFuncionario().getArea())) {
                areasParaTransferencia[cont++] = s;
            }
        }
        
        funcionariosArea = funcionarioDisicoFacade.buscarFuncrionariosPorArea(mbFuncionarioInfo.getFuncionario().getArea());
        for (FuncionarioDisico f: funcionariosArea) {
            solicitudFacade.contarSolicitudes(f);
        }

    }

    public void setMbDetalleSolicitud(MbDetalleSolicitud mbDetalleSolicitud) {
        this.mbDetalleSolicitud = mbDetalleSolicitud;
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public void setAreaController(AreaController areaController) {
        this.areaController = areaController;
    }

    public List<SolicitudRequerimiento> getSolicitudesArea() {
        return solicitudesArea;
    }

    public List<SolicitudRequerimiento> getSolicitudesAsignadas() {
        return solicitudFacade.getSolicitudesAsignadas(mbFuncionarioInfo.getFuncionario());
    }

    public SolicitudRequerimiento getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(SolicitudRequerimiento selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }

    public Boolean getEnviarMail() {
        return enviarMail;
    }

    public void setEnviarMail(Boolean enviarMail) {
        this.enviarMail = enviarMail;
    }

    public String getEmailsRespuestaManual() {
        return emailsRespuestaManual;
    }

    public void setEmailsRespuestaManual(String emailsRespuestaManual) {
        this.emailsRespuestaManual = emailsRespuestaManual;
    }

    public String getAsuntoRespuestaManual() {
        return asuntoRespuestaManual;
    }

    public void setAsuntoRespuestaManual(String asuntoRespuestaManual) {
        this.asuntoRespuestaManual = asuntoRespuestaManual;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getMotivoTransferencia() {
        return motivoTransferencia;
    }

    public void setMotivoTransferencia(String motivoTransferencia) {
        this.motivoTransferencia = motivoTransferencia;
    }

    public Area getNuevaArea() {
        return nuevaArea;
    }

    public void setNuevaArea(Area nuevaArea) {
        this.nuevaArea = nuevaArea;
    }

    public List<FuncionarioDisico> getFuncionariosArea() {
        return funcionariosArea;
    }

    public void setFuncionariosArea(List<FuncionarioDisico> funcionariosArea) {
        this.funcionariosArea = funcionariosArea;
    }
    
    public SelectItem[] getAreasParaTransferencia() {
        return areasParaTransferencia;
    }

    public void onRowSelect(SelectEvent event) {
        JsfUtils.redirect("solicitud.xhtml?codigo=" + selectedSolicitud.getCodigoConsulta());
    }

    public void reload() {
        solicitudesArea = solicitudFacade.buscarSolicitudesPorArea(mbFuncionarioInfo.getFuncionario().getArea());
    }

    public Date getMinDate() {
        Date d = new Date();
        d = new Date(d.getTime() + MEDIA_HORA);
        return d;
    }

    public void rechazarSolcitiud() {
        if (!respuesta.isEmpty()) {
            mbDetalleSolicitud.getSolicitud().setRespuesta(respuesta);
            solicitudEJB.rechazarSolicitud(mbDetalleSolicitud.getSolicitud());
        }
    }

    public void guardarModificacion() {
        solicitudFacade.edit(mbDetalleSolicitud.getSolicitud());
    }

    public void enviarRespuestaDirecta(ActionEvent event) {
        System.out.println("0 - Respuesta:"+respuesta);
        if (!respuesta.isEmpty()) {
            System.out.println("Respuesta:"+respuesta);
            mbDetalleSolicitud.getSolicitud().setRespuesta(respuesta);
            solicitudEJB.enviarRespuestaDirecta(mbDetalleSolicitud.getSolicitud(), enviarMail);
        }
        respuesta="";
        enviarMail = false;
    }
}
