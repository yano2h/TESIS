/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface NotificacionEJBLocal {

    void crearNotificacionSolicitud(TypeNotification type, SolicitudRequerimiento s, Funcionario invoker);
    
}
