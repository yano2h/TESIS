/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ResumenSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.ResumenSolicitudRequerimiento;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbSolicitudesArea extends MbBase implements Serializable{

    @EJB
    private ResumenSolicitudRequerimientoFacadeLocal resumenSolicitudRequerimientoFacade;
    
    private Area area;
    private ResumenSolicitudRequerimiento selectedSolicitud;
    private List<ResumenSolicitudRequerimiento> solicitudesArea;
    
    public MbSolicitudesArea() {
    }
    
    @PostConstruct
    public void init() {
        area = getFuncionarioDisico().getArea();
        solicitudesArea = resumenSolicitudRequerimientoFacade.buscarPorArea(area);
    }
    
    public List<ResumenSolicitudRequerimiento> getSolicitudesArea() {
        return solicitudesArea;
    }

    public ResumenSolicitudRequerimiento getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(ResumenSolicitudRequerimiento selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }

    public void onRowSelect(SelectEvent event) {
        JsfUtils.redirect("solicitud.xhtml?codigo=" + selectedSolicitud.getCodigoConsulta());
    }

    public void reload() {
        solicitudesArea = resumenSolicitudRequerimientoFacade.buscarPorArea(area);
    }

    
}
