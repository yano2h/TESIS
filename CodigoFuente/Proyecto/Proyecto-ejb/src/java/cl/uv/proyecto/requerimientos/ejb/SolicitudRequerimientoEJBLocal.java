/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 *
 * @author Jano
 */
@Local
public interface SolicitudRequerimientoEJBLocal {
    
    String generarCodigoConsulta(SolicitudRequerimiento solicitud);

    boolean validarCodigoConsulta(String codigoConsulta);

    String enviarSolicitud(SolicitudRequerimiento solicitud, Funcionario solicitante,  List<ArchivoAdjunto> archivosAdjuntos) ;

    String generarCodigo(long num);
    
    public void rechazarSolicitud(SolicitudRequerimiento solicitud);
    public void enviarRespuestaDirecta(SolicitudRequerimiento solicitud, Boolean enviarCopiaCorreo, List<ArchivoAdjunto> archivosAdjuntos) throws AddressException, MessagingException ;
    public void enviarRespuestaManual(SolicitudRequerimiento solicitud, String[] direcciones, String asunto, List<ArchivoAdjunto> archivosAdjuntos) ;
    public void transferirSolicitud(SolicitudRequerimiento solicitud, Area nuevaAreaResponsable, String motivoTransferencia);
    public void asignarSolicitud(SolicitudRequerimiento solicitud);
    public void iniciarSolicitud(SolicitudRequerimiento solicitud);
    public void enviarRespuestaJefeArea(SolicitudRequerimiento solicitud);

    public void comentarSolicitud(String comentario, SolicitudRequerimiento solicitud, Funcionario autor);

    public void dejarPendienteSolicitud(SolicitudRequerimiento solicitud);

    public void convertirSolicitudEnProyecto(SolicitudRequerimiento solicitud);
}
