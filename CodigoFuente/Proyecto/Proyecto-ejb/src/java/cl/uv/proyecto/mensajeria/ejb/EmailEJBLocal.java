/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 *
 * @author Jano
 */
@Local
public interface EmailEJBLocal {

    void enviarEmail(String direccion, String asunto, String mensaje);
    void enviarEmail(String direccion, String asunto, String mensaje, List<ArchivoAdjunto> adjuntos)throws MessagingException;
    void enviarEmail(String[] direcciones,String asunto, String mensaje);
    void enviarEmail(String[] direcciones,String asunto, String mensaje, List<ArchivoAdjunto> adjuntos)throws MessagingException;
    void enviarEmailConfirmacionEnvioSolicitud(SolicitudRequerimiento s);
    void enviarEmailInicioSolicitud(SolicitudRequerimiento s);
    void enviarEmailRechazoSolicitud(SolicitudRequerimiento s, Funcionario invoker);
    void enviarEmailTransferenciaSolicitud(SolicitudRequerimiento s, Funcionario invoker);
    void enviarEmailCierreSolicitud(SolicitudRequerimiento s, Funcionario invoker, List<ArchivoAdjunto> adjuntos);
    void enviarEmailRespuestaManual(SolicitudRequerimiento s, String[] direcciones, String asunto, String msg, List<ArchivoAdjunto> adjuntos);
    void enviarEmailNotificacionComentario(ComentarioSolicitud c);
    void enviarEmailAsignacionSolicitud(SolicitudRequerimiento s);
}
