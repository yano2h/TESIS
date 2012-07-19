/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface ProyectoFacadeLocal {

    void create(Proyecto proyecto);

    void edit(Proyecto proyecto);

    void remove(Proyecto proyecto);

    Proyecto find(Object id);

    List<Proyecto> findAll();

    List<Proyecto> findRange(int[] range);

    int count();

    public List<Proyecto> buscarProyectosPorArea(Area area);

    public List<Proyecto> buscarProyectoPorFiltros(Proyecto proyecto, Date minDate, Date maxDate);
    
    public List<Proyecto> buscarProyectoPorTareaFuncionario(FuncionarioDisico funcionarioDisico, Integer idTarea);
}
