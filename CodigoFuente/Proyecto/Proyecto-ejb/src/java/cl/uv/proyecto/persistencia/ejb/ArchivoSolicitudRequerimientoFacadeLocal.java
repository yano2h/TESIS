/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface ArchivoSolicitudRequerimientoFacadeLocal {

    void create(ArchivoSolicitudRequerimiento archivoSolicitudRequerimiento);

    void edit(ArchivoSolicitudRequerimiento archivoSolicitudRequerimiento);

    void remove(ArchivoSolicitudRequerimiento archivoSolicitudRequerimiento);

    ArchivoSolicitudRequerimiento find(Object id);

    List<ArchivoSolicitudRequerimiento> findAll();

    List<ArchivoSolicitudRequerimiento> findRange(int[] range);

    int count();
    
}
