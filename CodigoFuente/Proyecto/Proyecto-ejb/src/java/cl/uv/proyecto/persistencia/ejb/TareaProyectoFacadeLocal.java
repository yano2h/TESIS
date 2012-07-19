/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface TareaProyectoFacadeLocal {

    void create(TareaProyecto tareaProyecto);

    void edit(TareaProyecto tareaProyecto);

    void remove(TareaProyecto tareaProyecto);

    TareaProyecto find(Object id);

    List<TareaProyecto> findAll();

    List<TareaProyecto> findRange(int[] range);

    int count();
    
    public List<TareaProyecto> buscarTareasPorProyecto(Proyecto proyecto);
}
