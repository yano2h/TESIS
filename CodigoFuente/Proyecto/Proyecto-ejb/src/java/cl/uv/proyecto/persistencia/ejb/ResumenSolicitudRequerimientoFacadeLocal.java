/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.ResumenSolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface ResumenSolicitudRequerimientoFacadeLocal {
    List<ResumenSolicitudRequerimiento> findAll();

    List<ResumenSolicitudRequerimiento> findRange(int[] range);

    int count();

    List<ResumenSolicitudRequerimiento> buscarPorArea(Area a);
    
    List<ResumenSolicitudRequerimiento> buscarPorResponsable(Integer rut);
}
