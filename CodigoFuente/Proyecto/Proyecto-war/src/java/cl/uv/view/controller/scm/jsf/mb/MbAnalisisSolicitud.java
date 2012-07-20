/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.SolicitudCambioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyecto;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbAnalisisSolicitud {

    @EJB
    private SolicitudCambioFacadeLocal solicitudCambioFacade;
    @EJB
    private TareaScmProyectoFacadeLocal tareaScmProyectoFacade;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private SolicitudCambio solicitudCambioSelected;
    private List<SolicitudCambio> listaSolicitudesPendientes;
    private List<SolicitudCambio> listaSolicitudesAnalisadas;
            
    public MbAnalisisSolicitud() {
    }

    @PostConstruct
    private void init(){
        List<TareaScmProyecto> tp = tareaScmProyectoFacade.buscarTareasSCMPorResponsable(mbFuncionarioInfo.getFuncionario());
       
        
        listaSolicitudesPendientes = solicitudCambioFacade.buscarSolicitudAnalisisPendiente(mbFuncionarioInfo.getFuncionario());
        listaSolicitudesAnalisadas = solicitudCambioFacade.buscarSolicitudAnalisadas(mbFuncionarioInfo.getFuncionario());;
    }
    
    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public SolicitudCambio getSolicitudCambioSelected() {
        return solicitudCambioSelected;
    }

    public void setSolicitudCambioSelected(SolicitudCambio solicitudCambioSelected) {
        this.solicitudCambioSelected = solicitudCambioSelected;
    }

    public List<SolicitudCambio> getListaSolicitudesAnalisadas() {
        return listaSolicitudesAnalisadas;
    }

    public void setListaSolicitudesAnalisadas(List<SolicitudCambio> listaSolicitudesAnalisadas) {
        this.listaSolicitudesAnalisadas = listaSolicitudesAnalisadas;
    }

    public List<SolicitudCambio> getListaSolicitudesPendientes() {
        return listaSolicitudesPendientes;
    }

    public void setListaSolicitudesPendientes(List<SolicitudCambio> listaSolicitudesPendientes) {
        this.listaSolicitudesPendientes = listaSolicitudesPendientes;
    }
    
    public void redirctToDetalle(){
        JsfUtils.addParametro("solicitudCambio", solicitudCambioSelected);
        JsfUtils.redirect("solicitudCambio.xhtml");
    }
}
