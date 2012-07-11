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
    private FuncionarioFacadeLocal funcionarioFacade;
    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    
    @ManagedProperty(value="#{mbUserInfo}")
    private MbUserInfo mbUserInfo;
    
    private String codigoConsulta="";
    private Funcionario funcionario;
    private SolicitudRequerimiento selectedSolicitud;
            
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
    
    public String goDetalle(){
        return "solicitud?faces-redirect=true&codigo="+selectedSolicitud.getCodigoConsulta();
    }
}
