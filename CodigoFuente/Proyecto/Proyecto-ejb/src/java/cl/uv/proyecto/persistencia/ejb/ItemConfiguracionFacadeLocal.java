/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface ItemConfiguracionFacadeLocal {

    void create(ItemConfiguracion itemConfiguracion);

    void edit(ItemConfiguracion itemConfiguracion);

    void remove(ItemConfiguracion itemConfiguracion);

    ItemConfiguracion find(Object id);

    List<ItemConfiguracion> findAll();

    List<ItemConfiguracion> findRange(int[] range);

    int count();
    
}
