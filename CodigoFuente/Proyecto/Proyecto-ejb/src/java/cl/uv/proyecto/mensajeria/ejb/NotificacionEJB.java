/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.NotificacionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Jano
 */
@Stateless
public class NotificacionEJB implements NotificacionEJBLocal {
    
    @EJB
    private NotificacionFacadeLocal notificacionFacade;
    
    public void crearNotificacion(TypeNotification type, SolicitudRequerimiento s, Funcionario invoker){
        switch (type) {
            case ENVIO_SOLICITUD:
                
                break;
            case ASIGNACION_SOLICITUD:
                
                break;
            case COMENTARIO_SOLICITUD:    
                
            default:
                throw new AssertionError();
        }
        
    }
    
    @Override
    public void crearNotificacionEnvioSolicitud(SolicitudRequerimiento s){
        String msg = Resources.getValue("Notificaciones", "Notf_EnvioSolicitudReq");
        msg = String.format(msg, s.getSolicitante().getNombreCompleto(), s.getAsunto());
        Notificacion n = new Notificacion(msg,s.getSolicitante());
        notificacionFacade.create(n);
    }
    
  

}
