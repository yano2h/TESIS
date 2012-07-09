/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface SolicitudRequerimientoFacadeLocal {

    void create(SolicitudRequerimiento solicitudRequerimiento);

    void edit(SolicitudRequerimiento solicitudRequerimiento);

    void remove(SolicitudRequerimiento solicitudRequerimiento);

    SolicitudRequerimiento find(Object id);

    List<SolicitudRequerimiento> findAll();

    List<SolicitudRequerimiento> findRange(int[] range);

    int count();

    public java.util.List<cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento> buscarPorSolicitante(java.lang.Integer rutSolicitante);
    
}
