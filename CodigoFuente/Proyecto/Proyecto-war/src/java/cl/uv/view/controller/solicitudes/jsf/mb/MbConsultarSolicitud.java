/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.view.controller.base.jsf.mb.MbUserInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Persistence;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbConsultarSolicitud implements Serializable{
    
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    
    @ManagedProperty(value="#{mbUserInfo}")
    private MbUserInfo mbUserInfo;
    
    private String codigoConsulta="";
    private Funcionario funcionario;
    private SolicitudRequerimiento selectedSolicitud;
    private List<SolicitudRequerimiento> ultimasSolicitudesEnviadas;
    private List<SolicitudRequerimiento> ultimasSolicitudesCerradas;
    
    public MbConsultarSolicitud() {
    }

    @PostConstruct
    public void init(){
        funcionario = mbUserInfo.getFuncionario();
        if(!Persistence.getPersistenceUtil().isLoaded(funcionario, "solicitudesRequerimientoEnviadas")){
            funcionario.setSolicitudesRequerimientoEnviadas(solicitudFacade.buscarPorSolicitante(funcionario.getRut()));
        }
    }
    
    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    public void setCodigoConsulta(String codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }

    public void setMbUserInfo(MbUserInfo mbUserInfo) {
        this.mbUserInfo = mbUserInfo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public List<SolicitudRequerimiento> getSolicitudesEnviadas(){
        //return solicitudFacade.buscarPorSolicitante(funcionario.getRut());
        return funcionario.getSolicitudesRequerimientoEnviadas();
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
        return solicitudFacade.getUltimasSolicitudesCerradas(funcionario, 5);
    }

    public List<SolicitudRequerimiento> getUltimasSolicitudesEnviadas() {
        return solicitudFacade.getUltimasSolicitudesEnviadas(funcionario, 5);
    }
    
    
}
