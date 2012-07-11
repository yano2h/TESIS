/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ComentarioSolicitudFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.view.controller.base.jsf.mb.MbUserInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

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
    
    @ManagedProperty(value="#{mbUserInfo}")
    private MbUserInfo mbUserInfo;
    
    private String codigo;
    private String comentario;
    private SolicitudRequerimiento solicitud;
    
    
    public MbDetalleSolicitud() {
        comentario="";
        codigo="";
    }
    
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public SolicitudRequerimiento getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudRequerimiento solicitud) {
        this.solicitud = solicitud;
    }
    
    public void comentar(ActionEvent event){
        if(!comentario.isEmpty()){
            ComentarioSolicitud c = new ComentarioSolicitud();
            c.setAutor(mbUserInfo.getFuncionario());
            c.setFecha(new Date());
            c.setVisible(true);
            c.setComentario(comentario);
            c.setSolicitudRequerimiento(solicitud);
            comentarioFacade.create(c);
        }
        comentario="";
    }

    public void setMbUserInfo(MbUserInfo mbUserInfo) {
        this.mbUserInfo = mbUserInfo;
    }
    
    
    
}
