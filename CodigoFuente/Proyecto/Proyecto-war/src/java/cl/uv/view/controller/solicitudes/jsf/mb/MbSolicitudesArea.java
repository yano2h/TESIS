/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.jsf.mb.MbUserInfo;
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
import javax.persistence.Persistence;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbSolicitudesArea implements Serializable{

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private SolicitudRequerimientoEJBLocal solicitudEJB;
    
    @ManagedProperty(value="#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    @ManagedProperty(value="#{mbDetalleSolicitud}")
    private MbDetalleSolicitud mbDetalleSolicitud;
    
    private String codigoConsulta="";
    private SolicitudRequerimiento selectedSolicitud;
    
    private int MEDIA_HORA = 1000 * 60 * 30;
    
    List<SolicitudRequerimiento> solicitudesArea;
    
    public MbSolicitudesArea() {
    }
    
    @PostConstruct
    public void init(){
       solicitudesArea = solicitudFacade.buscarSolicitudesPorArea(mbFuncionarioInfo.getFuncionario().getArea());
    }
    
    public List<SolicitudRequerimiento> getSolicitudesArea(){
        
        return solicitudesArea;
    }
    
    public List<SolicitudRequerimiento> getSolicitudesAsignadas(){
        return solicitudFacade.getSolicitudesAsignadas(mbFuncionarioInfo.getFuncionario());
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public SolicitudRequerimiento getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(SolicitudRequerimiento selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }
    
    public void onRowSelect(SelectEvent event) {  
        JsfUtils.redirect("solicitud.xhtml?codigo="+selectedSolicitud.getCodigoConsulta());
    }
    
    public void reload(){
        solicitudesArea = solicitudFacade.buscarSolicitudesPorArea(mbFuncionarioInfo.getFuncionario().getArea());
    }
    
    public Date getMinDate(){
        Date d = new Date();
        d = new Date (d.getTime() + MEDIA_HORA);
        return d;
    }
    
    
    public void setMbDetalleSolicitud(MbDetalleSolicitud mbDetalleSolicitud) {
        this.mbDetalleSolicitud = mbDetalleSolicitud;
    }
    
    public void rechazarSolcitiud(){
        solicitudEJB.rechazarSolicitud(mbDetalleSolicitud.getSolicitud());
    }

    
    public void guardarModificacion(){
        System.out.println(mbDetalleSolicitud.getSolicitud().getFechaVencimiento());
        solicitudFacade.edit(mbDetalleSolicitud.getSolicitud());
    }
}
