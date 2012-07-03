/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ParticipanteProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface ParticipanteProyectoFacadeLocal {

    void create(ParticipanteProyecto participanteProyecto);

    void edit(ParticipanteProyecto participanteProyecto);

    void remove(ParticipanteProyecto participanteProyecto);

    ParticipanteProyecto find(Object id);

    List<ParticipanteProyecto> findAll();

    List<ParticipanteProyecto> findRange(int[] range);

    int count();
    
}
