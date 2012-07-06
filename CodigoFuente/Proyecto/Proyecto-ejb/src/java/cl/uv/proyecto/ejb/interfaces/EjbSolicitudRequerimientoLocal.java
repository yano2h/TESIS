/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.ejb.interfaces;

import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface EjbSolicitudRequerimientoLocal {

    String generarCodigoConsulta(SolicitudRequerimiento solicitud);

    boolean validarCodigoConsulta(String codigoConsulta);

    String enviarSolicitud(SolicitudRequerimiento solicitud, Funcionario solicitante);
    
}
