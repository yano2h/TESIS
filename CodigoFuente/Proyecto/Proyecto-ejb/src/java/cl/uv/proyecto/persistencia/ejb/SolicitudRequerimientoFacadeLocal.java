/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
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

    public List<SolicitudRequerimiento> buscarPorSolicitante(Integer rutSolicitante);

    public SolicitudRequerimiento buscarPorCodigo(String codigo);
    
    public List<SolicitudRequerimiento> getUltimasSolicitudesEnviadas(Funcionario funcionario, Integer maxResults);
    
    public List<SolicitudRequerimiento> getUltimasSolicitudesCerradas(Funcionario funcionario, Integer maxResults);
    
    public List<SolicitudRequerimiento> buscarSolicitudesPorArea(Area area);
    
    public List<SolicitudRequerimiento> getSolicitudesAsignadas(FuncionarioDisico funcionarioDisico);
    
    public List<SolicitudRequerimiento> getSolicitudesEnviadas(Funcionario funcionario);
    
    public void contarSolicitudes(FuncionarioDisico funcionario);
}
