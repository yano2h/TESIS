/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EstadoProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface EstadoProyectoFacadeLocal {

    void create(EstadoProyecto estadoProyecto);

    void edit(EstadoProyecto estadoProyecto);

    void remove(EstadoProyecto estadoProyecto);

    EstadoProyecto find(Object id);

    List<EstadoProyecto> findAll();

    List<EstadoProyecto> findRange(int[] range);

    int count();
    
}
