/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbMisSolicitudes implements Serializable{
    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private SolicitudRequerimientoEJBLocal solicitudEJB;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    @ManagedProperty(value = "#{mbDetalleSolicitud}")
    private MbDetalleSolicitud mbDetalleSolicitud;
    
    List<SolicitudRequerimiento> misSolicitudes;
    SolicitudRequerimiento selectedSolicitud;
    
    public MbMisSolicitudes() {
    }
    
    @PostConstruct
    private void init(){
        misSolicitudes = solicitudFacade.getSolicitudesAsignadas(mbFuncionarioInfo.getFuncionario());
        mbFuncionarioInfo.getFuncionario().setSolicitudesRequerimientosAsignadas(misSolicitudes);
    }

    public void setMbDetalleSolicitud(MbDetalleSolicitud mbDetalleSolicitud) {
        this.mbDetalleSolicitud = mbDetalleSolicitud;
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public List<SolicitudRequerimiento> getMisSolicitudes() {
        return misSolicitudes;
    }

    public void setMisSolicitudes(List<SolicitudRequerimiento> misSolicitudes) {
        this.misSolicitudes = misSolicitudes;
    }

    public SolicitudRequerimiento getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(SolicitudRequerimiento selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }
    
    public void iniciarSolicitud(){
        solicitudEJB.iniciarSolicitud(mbDetalleSolicitud.getSolicitud());
    }
    
    public void respuestaAlJefeDeArea(){
        solicitudEJB.enviarRespuestaJefeArea(mbDetalleSolicitud.getSolicitud());
    }
    
    public void onRowSelect(){
        JsfUtils.redirect("solicitud.xhtml?codigo=" + selectedSolicitud.getCodigoConsulta());
    }
    
    public void reload(){

    }
}
