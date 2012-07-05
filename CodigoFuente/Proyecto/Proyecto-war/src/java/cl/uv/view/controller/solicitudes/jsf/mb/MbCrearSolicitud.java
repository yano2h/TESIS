/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbCrearSolicitud {

    private SolicitudRequerimiento solicitud;
    
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
}
