/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.mensajeria.ejb.EmailEJBLocal;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbCrearSolicitud implements Serializable{

    @EJB
    private SolicitudRequerimientoEJBLocal ejbSolicitud;
    @EJB
    private EmailEJBLocal ejbEmail;
    
    private SolicitudRequerimiento solicitud;
    private String codigoConsulta;
    
    public MbCrearSolicitud() {
    }
    
    public SolicitudRequerimiento getSolicitud() {
        if (solicitud == null) {
            solicitud = new SolicitudRequerimiento();
        }
        return solicitud;
    }

    public void setSolicitud(SolicitudRequerimiento solicitud) {
        this.solicitud = solicitud;
    }

    public String getCodigoConsulta() {
        return codigoConsulta;
    }
    
    public void enviar(ActionEvent event){
        codigoConsulta = ejbSolicitud.enviarSolicitud(solicitud, null);
        ejbEmail.enviarEmail("yano2h@gmail.com", "mail de prueba", "Este es una prueba");
    }
    
    public String cerrarDialog(){
        codigoConsulta = "";        
        return "crearSolicitud?faces-redirect=true";
    }
    
}