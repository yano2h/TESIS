/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jano
 */
@Stateless
public class CalculoDeIndicadoresEJB implements CalculoDeIndicadoresEJBLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;
    
    @Override
    public Long contarSolicitudes(FuncionarioDisico funcionario, Short idEstado) {
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE s.responsable = :responsable AND s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado";
        Query q = em.createQuery(query);
        q.setParameter("responsable", funcionario);
        q.setParameter("idEstado", idEstado);
        return (Long)q.getSingleResult();
    }
    
    @Override
    public Long contarSolicitudes(Area area, Short idEstado) {
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE s.areaResponsable = :area AND s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado";
        Query q = em.createQuery(query);
        q.setParameter("area", area);
        q.setParameter("idEstado", idEstado);
        return (Long)q.getSingleResult();
    }
    
    @Override
    public Long contarSolicitudes(Short idEstado) {
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado";
        Query q = em.createQuery(query);
        q.setParameter("idEstado", idEstado);
        return (Long)q.getSingleResult();
    }

    
//    public Integer porcentajeSolicitudesAsignadas(FuncionarioDisico f){
//    
//    }
//    
//    public Integer porcentajeSolicitudesAsignadas(Area a){
//    
//    }
//    
//    public Integer porcentajeSolicitudesAsignadas(){
//    
//    }
//    
//    public Integer porcentajeCumplimiento(FuncionarioDisico f){
//    
//    }
//    
//    public Integer porcentajeCumplimiento(Area a){
//    
//    }
//    
//    public Integer porcentajeCumplimiento(){
//    
//    }
//    
//    public Integer porcentajeRetrasos(FuncionarioDisico f){
//    
//    }
//    
//    public Integer porcentajeRetrasos(Area a){
//    
//    }
//    
//    public Integer porcentajeRetrasos(){
//    
//    }
    
}
