/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Persistence;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbConsultarSolicitud extends MbBase{
    
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    
    private String codigoConsulta="";
   // private Funcionario funcionario;
    private SolicitudRequerimiento selectedSolicitud;
    private List<SolicitudRequerimiento> ultimasSolicitudesEnviadas;
    private List<SolicitudRequerimiento> ultimasSolicitudesCerradas;
    
    public MbConsultarSolicitud() {
    }

    @PostConstruct
    public void init(){
        if(!Persistence.getPersistenceUtil().isLoaded(getFuncionario(), "solicitudesRequerimientoEnviadas")){
            getFuncionario().setSolicitudesRequerimientoEnviadas(solicitudFacade.buscarPorSolicitante(getFuncionario().getRut()));
        }
        ultimasSolicitudesEnviadas = solicitudFacade.getUltimasSolicitudesEnviadas(getFuncionario(), 5);;
        ultimasSolicitudesCerradas = solicitudFacade.getUltimasSolicitudesCerradas(getFuncionario(), 5);;
    }
    
    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    public void setCodigoConsulta(String codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }

    public List<SolicitudRequerimiento> getSolicitudesEnviadas(){
        //return solicitudFacade.buscarPorSolicitante(funcionario.getRut());
        return getFuncionario().getSolicitudesRequerimientoEnviadas();
    }
    
    public SolicitudRequerimiento getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(SolicitudRequerimiento selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }
    
    public void onRowSelect(SelectEvent event) {  
        JsfUtils.performNavigation("solicitud?codigo="+selectedSolicitud.getCodigoConsulta(), true);
      //  JsfUtils.redirect("solicitud.xhtml?codigo="+selectedSolicitud.getCodigoConsulta());
    }  
    
    public String buscarCodigo(){
        if(codigoConsulta.isEmpty()){
            return "";
        }else{
            if(solicitudFacade.buscarPorCodigo(codigoConsulta)!=null){
                return "solicitud?faces-redirect=true&codigo="+codigoConsulta;
            }else{
                JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,null, "La solicitud con codigo \""+codigoConsulta+"\" no existe"));
                return "";
            }
            
        }
    }

    public List<SolicitudRequerimiento> getUltimasSolicitudesCerradas() {
        return ultimasSolicitudesCerradas;
    }

    public List<SolicitudRequerimiento> getUltimasSolicitudesEnviadas() {
        return ultimasSolicitudesEnviadas;
    }
    
    
}
