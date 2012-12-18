/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoProyecto;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface ArchivoProyectoFacadeLocal {

    void create(ArchivoProyecto archivoProyecto);

    void edit(ArchivoProyecto archivoProyecto);

    void remove(ArchivoProyecto archivoProyecto);

    ArchivoProyecto find(Object id);

    List<ArchivoProyecto> findAll();

    List<ArchivoProyecto> findRange(int[] range);

    int count();
    
    List<ArchivoProyecto> buscarArchivosPorProyecto(Proyecto p);
}
