/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EstadoSolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface EstadoSolicitudRequerimientoFacadeLocal {

    void create(EstadoSolicitudRequerimiento estadoSolicitudRequerimiento);

    void edit(EstadoSolicitudRequerimiento estadoSolicitudRequerimiento);

    void remove(EstadoSolicitudRequerimiento estadoSolicitudRequerimiento);

    EstadoSolicitudRequerimiento find(Object id);

    List<EstadoSolicitudRequerimiento> findAll();

    List<EstadoSolicitudRequerimiento> findRange(int[] range);

    int count();
    
}
