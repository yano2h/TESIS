/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.RolProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface RolProyectoFacadeLocal {

    void create(RolProyecto rolProyecto);

    void edit(RolProyecto rolProyecto);

    void remove(RolProyecto rolProyecto);

    RolProyecto find(Object id);

    List<RolProyecto> findAll();

    List<RolProyecto> findRange(int[] range);

    int count();
    
}
