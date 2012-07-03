/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Entregable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface EntregableFacadeLocal {

    void create(Entregable entregable);

    void edit(Entregable entregable);

    void remove(Entregable entregable);

    Entregable find(Object id);

    List<Entregable> findAll();

    List<Entregable> findRange(int[] range);

    int count();
    
}
