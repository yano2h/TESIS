/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface TareaScmProyectoFacadeLocal {

    void create(TareaScmProyecto tareaScmProyecto);

    void edit(TareaScmProyecto tareaScmProyecto);

    void remove(TareaScmProyecto tareaScmProyecto);

    TareaScmProyecto find(Object id);

    List<TareaScmProyecto> findAll();

    List<TareaScmProyecto> findRange(int[] range);

    int count();

    public List<TareaScmProyecto> buscarTareasSCMPorIdProyecto(Integer idProyecto);

    public void guardarListaDeTareas(List<TareaScmProyecto> tareasScmProyecto);
    
    public List<TareaScmProyecto> buscarTareasSCMPorResponsable(FuncionarioDisico responsable);
}
