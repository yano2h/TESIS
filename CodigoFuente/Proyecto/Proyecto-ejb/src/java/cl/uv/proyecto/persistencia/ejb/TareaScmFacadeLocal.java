/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.TareaScm;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface TareaScmFacadeLocal {

    void create(TareaScm tareaScm);

    void edit(TareaScm tareaScm);

    void remove(TareaScm tareaScm);

    TareaScm find(Object id);

    List<TareaScm> findAll();

    List<TareaScm> findRange(int[] range);

    int count();
    
}
