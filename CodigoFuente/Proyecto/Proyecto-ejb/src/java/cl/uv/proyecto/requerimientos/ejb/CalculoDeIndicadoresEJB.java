/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.model.base.utils.MathUtils;
import cl.uv.model.base.utils.Resources;
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

    @Override
    public Float porcentajeSolicitudesAsignadas(FuncionarioDisico f){
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
                     + "s.responsable = :responsable";
        Query q = em.createQuery(query);
        q.setParameter("responsable", f);
        Long cantidadSolAsignadas = (Long)q.getSingleResult();

        query  = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
               + "s.areaResponsable = :area";
        q = em.createQuery(query);
        q.setParameter("area", f.getArea());
        Long cantidadSolArea = (Long)q.getSingleResult();

        return MathUtils.calcularPorcentajeRedondeado(cantidadSolAsignadas, cantidadSolArea, 1) ;
    }
    
    @Override
    public Float porcentajeSolicitudesAsignadas(Area a){
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
                     + "s.areaResponsable = :area";
        Query q = em.createQuery(query);
        q.setParameter("area", a);
        Long cantidadSolAsignadas = (Long)q.getSingleResult();
        
        query  = "SELECT COUNT(s) FROM SolicitudRequerimiento s";
        q = em.createQuery(query);
        Long cantidadSolDpto = (Long)q.getSingleResult();
        
        return MathUtils.calcularPorcentajeRedondeado(cantidadSolAsignadas, cantidadSolDpto, 1) ;
    }
    
    @Override
    public Float porcentajeRetrasos(FuncionarioDisico f){
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
                     + "s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado AND "
                     + "s.fechaCierre > s.fechaVencimiento AND "
                     + "s.responsable = :responsable";
        Query q = em.createQuery(query);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        q.setParameter("responsable", f);
        
        Long cantidadSolCerradasVencidas = (Long)q.getSingleResult();
        
        query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
              + "s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado AND "
              + "s.responsable = :responsable";
        q = em.createQuery(query);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
        q.setParameter("responsable", f);
        Long cantidadSolVencidas = (Long)q.getSingleResult();
        
        query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
              + "s.responsable = :responsable ";
        q = em.createQuery(query);
        q.setParameter("responsable", f);
        Long totalSolicitudesResponsable = (Long)q.getSingleResult();
        Long totalSolicitudesVencidas = cantidadSolCerradasVencidas + cantidadSolVencidas;
        
        return MathUtils.calcularPorcentajeRedondeado(totalSolicitudesVencidas, totalSolicitudesResponsable, 1) ;
    }
    
    @Override
    public Float porcentajeRetrasos(Area a){
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
                     + "s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado AND "
                     + "s.fechaCierre > s.fechaVencimiento AND "
                     + "s.areaResponsable = :area";
        Query q = em.createQuery(query);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        q.setParameter("area", a);
        
        Long cantidadSolCerradasVencidas = (Long)q.getSingleResult();
        
        query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
              + "s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado AND "
              + "s.areaResponsable = :area";
        q = em.createQuery(query);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
        q.setParameter("area", a);
        Long cantidadSolVencidas = (Long)q.getSingleResult();
        
        query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
              + "s.areaResponsable = :area";
        q = em.createQuery(query);
        q.setParameter("area", a);
        Long totalSolicitudesArea = (Long)q.getSingleResult();
        Long totalSolicitudesVencidas = cantidadSolCerradasVencidas + cantidadSolVencidas;
        
        return MathUtils.calcularPorcentajeRedondeado(totalSolicitudesVencidas, totalSolicitudesArea, 1) ;
    }
    
    @Override
    public Float porcentajeRetrasos(){
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
                     + "s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado AND "
                     + "s.fechaCierre > s.fechaVencimiento";
        Query q = em.createQuery(query);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        
        Long cantidadSolCerradasVencidas = (Long)q.getSingleResult();
        
        query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE "
              + "s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado";
        q = em.createQuery(query);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));

        Long cantidadSolVencidas = (Long)q.getSingleResult();
        
        query = "SELECT COUNT(s) FROM SolicitudRequerimiento s";
        q = em.createQuery(query);
        Long totalSolicitudesDpto = (Long)q.getSingleResult();
        Long totalSolicitudesVencidas = cantidadSolCerradasVencidas + cantidadSolVencidas;
        
        return MathUtils.calcularPorcentajeRedondeado(totalSolicitudesVencidas, totalSolicitudesDpto, 1) ;
    }
    
}
