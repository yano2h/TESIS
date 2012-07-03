/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface SolicitudCambioFacadeLocal {

    void create(SolicitudCambio solicitudCambio);

    void edit(SolicitudCambio solicitudCambio);

    void remove(SolicitudCambio solicitudCambio);

    SolicitudCambio find(Object id);

    List<SolicitudCambio> findAll();

    List<SolicitudCambio> findRange(int[] range);

    int count();
    
}
