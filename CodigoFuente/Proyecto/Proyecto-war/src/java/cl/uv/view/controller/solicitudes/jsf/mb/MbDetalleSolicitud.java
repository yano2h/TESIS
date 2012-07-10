/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ComentarioSolicitudFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbDetalleSolicitud implements Serializable{

    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private ComentarioSolicitudFacadeLocal comentarioFacade;
    
    private String codigo;
    private SolicitudRequerimiento solicitud;
    
    public MbDetalleSolicitud() {
        codigo="";
    }
    
  //  @PostConstruct
    public void init(){
        solicitud = solicitudFacade.buscarPorCodigo(codigo);
        FuncionarioDisico f = new FuncionarioDisico();
        if(solicitud == null){
            JsfUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "La solicitud con codigo "+codigo+" no pudo ser encontrada"));
        }else{
            solicitud.setComentarios(comentarioFacade.buscarComentariosPorSolicitud(solicitud.getIdSolicitudRequerimiento()));
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public SolicitudRequerimiento getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudRequerimiento solicitud) {
        this.solicitud = solicitud;
    }
    
    
    
}
