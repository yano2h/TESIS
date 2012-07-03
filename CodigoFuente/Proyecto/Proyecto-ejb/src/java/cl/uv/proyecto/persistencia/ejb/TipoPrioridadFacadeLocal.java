/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.TipoPrioridad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface TipoPrioridadFacadeLocal {

    void create(TipoPrioridad tipoPrioridad);

    void edit(TipoPrioridad tipoPrioridad);

    void remove(TipoPrioridad tipoPrioridad);

    TipoPrioridad find(Object id);

    List<TipoPrioridad> findAll();

    List<TipoPrioridad> findRange(int[] range);

    int count();
    
}
