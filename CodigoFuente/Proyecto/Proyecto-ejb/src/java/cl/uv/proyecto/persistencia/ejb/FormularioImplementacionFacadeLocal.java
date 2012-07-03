/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.FormularioImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface FormularioImplementacionFacadeLocal {

    void create(FormularioImplementacion formularioImplementacion);

    void edit(FormularioImplementacion formularioImplementacion);

    void remove(FormularioImplementacion formularioImplementacion);

    FormularioImplementacion find(Object id);

    List<FormularioImplementacion> findAll();

    List<FormularioImplementacion> findRange(int[] range);

    int count();
    
}
