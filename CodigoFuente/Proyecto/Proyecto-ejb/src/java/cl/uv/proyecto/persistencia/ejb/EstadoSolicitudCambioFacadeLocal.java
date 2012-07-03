/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EstadoSolicitudCambio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface EstadoSolicitudCambioFacadeLocal {

    void create(EstadoSolicitudCambio estadoSolicitudCambio);

    void edit(EstadoSolicitudCambio estadoSolicitudCambio);

    void remove(EstadoSolicitudCambio estadoSolicitudCambio);

    EstadoSolicitudCambio find(Object id);

    List<EstadoSolicitudCambio> findAll();

    List<EstadoSolicitudCambio> findRange(int[] range);

    int count();
    
}
