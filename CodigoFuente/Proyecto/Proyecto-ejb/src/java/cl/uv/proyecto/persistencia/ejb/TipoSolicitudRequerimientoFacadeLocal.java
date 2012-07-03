/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.TipoSolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface TipoSolicitudRequerimientoFacadeLocal {

    void create(TipoSolicitudRequerimiento tipoSolicitudRequerimiento);

    void edit(TipoSolicitudRequerimiento tipoSolicitudRequerimiento);

    void remove(TipoSolicitudRequerimiento tipoSolicitudRequerimiento);

    TipoSolicitudRequerimiento find(Object id);

    List<TipoSolicitudRequerimiento> findAll();

    List<TipoSolicitudRequerimiento> findRange(int[] range);

    int count();
    
}
