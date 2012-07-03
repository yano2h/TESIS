/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EstadisticaPersonal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface EstadisticaPersonalFacadeLocal {

    void create(EstadisticaPersonal estadisticaPersonal);

    void edit(EstadisticaPersonal estadisticaPersonal);

    void remove(EstadisticaPersonal estadisticaPersonal);

    EstadisticaPersonal find(Object id);

    List<EstadisticaPersonal> findAll();

    List<EstadisticaPersonal> findRange(int[] range);

    int count();
    
}
