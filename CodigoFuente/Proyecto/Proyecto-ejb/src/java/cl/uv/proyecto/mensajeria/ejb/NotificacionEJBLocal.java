/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface NotificacionEJBLocal {

    public void crearNotificacionEnvioSolicitud(SolicitudRequerimiento s);
    
}
