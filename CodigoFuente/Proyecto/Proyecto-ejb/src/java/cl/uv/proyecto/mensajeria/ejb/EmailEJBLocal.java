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
public interface EmailEJBLocal {

    void enviarEmail(String direccion, String asunto, String mensaje);
    void enviarEmail(String[] direcciones,String asunto, String mensaje);
    String generarContenidoEmailSolicitud(TypeEmail t, SolicitudRequerimiento s, Funcionario invoker);
    String generarAsuntoEmailSolicitud(TypeEmail t, SolicitudRequerimiento s, Funcionario invoker);
    void enviarEmailConfirmacionEnvioSolicitud(SolicitudRequerimiento s);
}
