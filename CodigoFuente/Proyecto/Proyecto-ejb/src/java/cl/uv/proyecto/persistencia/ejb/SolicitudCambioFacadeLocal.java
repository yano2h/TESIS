/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface SolicitudCambioFacadeLocal {

    void create(SolicitudCambio solicitudCambio);

    void edit(SolicitudCambio solicitudCambio);

    void remove(SolicitudCambio solicitudCambio);

    SolicitudCambio find(Object id);

    List<SolicitudCambio> findAll();

    List<SolicitudCambio> findRange(int[] range);

    int count();

    public void enviarSolicitudCambio(SolicitudCambio sc, FuncionarioDisico solicitante);

    public List<SolicitudCambio> buscarSolicitudAnalisadas(FuncionarioDisico funcionario);
            
    public List<SolicitudCambio> buscarSolicitudAnalisisPendiente(FuncionarioDisico funcionario);

    public List<SolicitudCambio> buscarSolicitudPorProyecto(Proyecto proyecto) ;

    public void guardarAnalisisImpacto(SolicitudCambio sc, FuncionarioDisico funcionario);
    
    public void guardarEvaluacionSolicitud(SolicitudCambio sc, FuncionarioDisico funcionario);

    public List<SolicitudCambio> buscarSolicitudEvaluacionPendiente(FuncionarioDisico funcionario);
    
    public List<SolicitudCambio> buscarSolicitudEvaluadas(FuncionarioDisico funcionario);

    public List<SolicitudCambio> buscarSolicitudImplementacionPendiente(FuncionarioDisico funcionario);
    
    public List<SolicitudCambio> buscarSolicitudImplementadas(FuncionarioDisico funcionario);

    public Long contarSolicitudAnalisisPendiente(FuncionarioDisico funcionario);

    public Long contarSolicitudEvaluacionPendiente(FuncionarioDisico funcionario);

    public Long contarSolicitudImplementacionPendiente(FuncionarioDisico funcionario);
    
    public List<SolicitudCambio> buscarSolicitudesPorIC(ItemConfiguracion ic);
    
}
