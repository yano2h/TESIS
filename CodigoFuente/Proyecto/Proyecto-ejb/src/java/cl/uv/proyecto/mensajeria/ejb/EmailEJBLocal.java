/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
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
public interface EmailEJBLocal {

    void enviarEmail(String direccion, String asunto, String mensaje);
    void enviarEmail(String direccion, String asunto, String mensaje, List<ArchivoAdjunto> adjuntos);
    void enviarEmail(String[] direcciones,String asunto, String mensaje);
    void enviarEmailConfirmacionEnvioSolicitud(SolicitudRequerimiento s);
}
