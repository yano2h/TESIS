/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ResumenSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ResumenSolicitudRequerimiento;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbMisSolicitudes extends MbBase implements Serializable{

    @EJB
    private SolicitudRequerimientoEJBLocal solicitudEJB;
    @EJB
    private ResumenSolicitudRequerimientoFacadeLocal resumenSolicitudFacade;
    
    List<ResumenSolicitudRequerimiento> misSolicitudes;
    ResumenSolicitudRequerimiento selectedSolicitud;
    
    public MbMisSolicitudes() {
    }
    
    @PostConstruct
    private void init(){
        misSolicitudes = resumenSolicitudFacade.buscarPorResponsable(getFuncionarioDisico().getRut());
    }

    public List<ResumenSolicitudRequerimiento> getMisSolicitudes() {
        return misSolicitudes;
    }

    public void setMisSolicitudes(List<ResumenSolicitudRequerimiento> misSolicitudes) {
        this.misSolicitudes = misSolicitudes;
    }

    public ResumenSolicitudRequerimiento getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(ResumenSolicitudRequerimiento selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }
    
    public void onRowSelect(){
        solicitudEJB.dejarPendienteSolicitud(selectedSolicitud.getIdSolicitudReq());
        JsfUtils.redirect("solicitud.xhtml?codigo=" + selectedSolicitud.getCodigoConsulta());
    }
    
    public void reload(){
        misSolicitudes = resumenSolicitudFacade.buscarPorResponsable(getFuncionarioDisico().getRut());
    }
}
