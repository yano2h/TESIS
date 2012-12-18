/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EtapaProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface EtapaProyectoFacadeLocal {

    void create(EtapaProyecto estadoProyecto);

    void edit(EtapaProyecto estadoProyecto);

    void remove(EtapaProyecto estadoProyecto);

    EtapaProyecto find(Object id);

    List<EtapaProyecto> findAll();

    List<EtapaProyecto> findRange(int[] range);

    int count();
}
