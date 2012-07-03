/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface AreaFacadeLocal {

    void create(Area area);

    void edit(Area area);

    void remove(Area area);

    Area find(Object id);

    List<Area> findAll();

    List<Area> findRange(int[] range);

    int count();
    
}
