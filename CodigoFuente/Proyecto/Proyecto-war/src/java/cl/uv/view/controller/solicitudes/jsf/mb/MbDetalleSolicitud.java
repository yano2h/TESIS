/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbDetalleSolicitud implements Serializable{

    private String codigo;
    private SolicitudRequerimiento selectedSolicitud;
    public MbDetalleSolicitud() {
        codigo="";
    }
    
    @PostConstruct
    public void init(){
//        Map<String,String> l = JsfUtils.getExternalContext().getRequestParameterMap();
//        System.out.println("SIZE MAP:"+l.size());
//        codigo = JsfUtils.getParam("codigo");
//        System.out.println("CODIGO-->"+codigo);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public SolicitudRequerimiento getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(SolicitudRequerimiento selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }
    
    
    
}
