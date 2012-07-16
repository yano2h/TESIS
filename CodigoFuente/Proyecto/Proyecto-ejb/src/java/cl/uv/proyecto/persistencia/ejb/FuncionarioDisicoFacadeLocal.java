/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface FuncionarioDisicoFacadeLocal {

    void create(FuncionarioDisico funcionarioDisico);

    void edit(FuncionarioDisico funcionarioDisico);

    void remove(FuncionarioDisico funcionarioDisico);

    FuncionarioDisico find(Object id);

    List<FuncionarioDisico> findAll();

    List<FuncionarioDisico> findRange(int[] range);

    int count();
    
    List<FuncionarioDisico> buscarFuncrionariosPorArea(Area area);
    
}
