/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.TipoProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface TipoProyectoFacadeLocal {

    void create(TipoProyecto tipoProyecto);

    void edit(TipoProyecto tipoProyecto);

    void remove(TipoProyecto tipoProyecto);

    TipoProyecto find(Object id);

    List<TipoProyecto> findAll();

    List<TipoProyecto> findRange(int[] range);

    int count();
    
}
