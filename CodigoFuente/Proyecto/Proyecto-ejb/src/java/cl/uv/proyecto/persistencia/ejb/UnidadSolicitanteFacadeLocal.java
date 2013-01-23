/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.UnidadSolicitante;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface UnidadSolicitanteFacadeLocal {
    void create(UnidadSolicitante unidadSolicitante);

    void edit(UnidadSolicitante unidadSolicitante);

    void remove(UnidadSolicitante unidadSolicitante);

    UnidadSolicitante find(Object id);

    List<UnidadSolicitante> findAll();

    List<UnidadSolicitante> findRange(int[] range);

    int count();
}
