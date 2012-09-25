/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface ArchivoAdjuntoFacadeLocal {

    void create(ArchivoAdjunto archivoAdjunto);

    void edit(ArchivoAdjunto archivoAdjunto);

    void remove(ArchivoAdjunto archivoAdjunto);

    ArchivoAdjunto find(Object id);

    List<ArchivoAdjunto> findAll();

    List<ArchivoAdjunto> findRange(int[] range);

    int count();
    
}
