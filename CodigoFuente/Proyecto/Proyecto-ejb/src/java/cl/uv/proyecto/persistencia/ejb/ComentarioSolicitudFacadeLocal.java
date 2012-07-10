/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface ComentarioSolicitudFacadeLocal {

    void create(ComentarioSolicitud comentarioSolicitud);

    void edit(ComentarioSolicitud comentarioSolicitud);

    void remove(ComentarioSolicitud comentarioSolicitud);

    ComentarioSolicitud find(Object id);

    List<ComentarioSolicitud> findAll();

    List<ComentarioSolicitud> findRange(int[] range);

    int count();

    public List<ComentarioSolicitud> buscarComentariosPorSolicitud(Long idSolicitudRequerimiento);
    
}
