/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.model.base.utils.MathUtils;
import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import java.math.BigInteger;
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
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE responsable = ? AND estado_solicitud = ?";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, funcionario.getRut());
        q.setParameter(2, idEstado);
        return ((BigInteger)q.getSingleResult()).longValue();
    }
    
    @Override
    public Long contarSolicitudes(Area area, Short idEstado) {
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE area_responsable = ? AND estado_solicitud = ?";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, area.getIdArea());
        q.setParameter(2, idEstado);
        return ((BigInteger)q.getSingleResult()).longValue();
    }
    
    @Override
    public Long contarSolicitudes(Short idEstado) {
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE estado_solicitud = ?";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, idEstado);
        return ((BigInteger)q.getSingleResult()).longValue();
    }

    @Override
    public Float porcentajeSolicitudesAsignadas(FuncionarioDisico f){
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE responsable = ?";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, f.getRut());
        Long cantidadSolAsignadas = ((BigInteger)q.getSingleResult()).longValue();

        query  = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE area_responsable = ?";
        q = em.createNativeQuery(query);
        q.setParameter(1, f.getArea().getIdArea());
        Long cantidadSolArea = ((BigInteger)q.getSingleResult()).longValue();

        return MathUtils.calcularPorcentajeRedondeado(cantidadSolAsignadas, cantidadSolArea, 1) ;
    }
    
    @Override
    public Float porcentajeSolicitudesAsignadas(Area a){
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE area_responsable = ?";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, a.getIdArea());
        Long cantidadSolAsignadas = ((BigInteger)q.getSingleResult()).longValue();
        
        query  = "SELECT COUNT(*) FROM solicitud_requerimiento";
        q = em.createNativeQuery(query);
        Long cantidadSolDpto = ((BigInteger)q.getSingleResult()).longValue();
        
        return MathUtils.calcularPorcentajeRedondeado(cantidadSolAsignadas, cantidadSolDpto, 1) ;
    }
    
    @Override
    public Float porcentajeRetrasos(FuncionarioDisico f){
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE responsable = ? AND estado_solicitud = ? AND fecha_cierre > fecha_vencimiento";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, f.getRut());
        q.setParameter(2, Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        
        Long cantidadSolCerradasVencidas = ((BigInteger)q.getSingleResult()).longValue();
        
        query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE responsable = ? AND estado_solicitud = ?";
        q = em.createNativeQuery(query);
        q.setParameter(1, f.getRut());
        q.setParameter(2, Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
        Long cantidadSolVencidas = ((BigInteger)q.getSingleResult()).longValue();
        
        query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE responsable = ?";
        q = em.createNativeQuery(query);
        q.setParameter(1, f.getRut());
        Long totalSolicitudesResponsable = ((BigInteger)q.getSingleResult()).longValue();
        Long totalSolicitudesVencidas = cantidadSolCerradasVencidas + cantidadSolVencidas;
        
        return MathUtils.calcularPorcentajeRedondeado(totalSolicitudesVencidas, totalSolicitudesResponsable, 1) ;
    }
    
    @Override
    public Float porcentajeRetrasos(Area a){
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE area_responsable = ? AND estado_solicitud = ? AND fecha_cierre > fecha_vencimiento ";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, a.getIdArea());
        q.setParameter(2, Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        
        Long cantidadSolCerradasVencidas = ((BigInteger)q.getSingleResult()).longValue();
        
        query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE area_responsable = ? AND estado_solicitud = ? ";
        q = em.createNativeQuery(query);
        q.setParameter(1, a.getIdArea());
        q.setParameter(2, Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
        Long cantidadSolVencidas = ((BigInteger)q.getSingleResult()).longValue();
        
        query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE area_responsable = ?";
        q = em.createNativeQuery(query);
        q.setParameter(1, a.getIdArea());
        Long totalSolicitudesArea = ((BigInteger)q.getSingleResult()).longValue();
        Long totalSolicitudesVencidas = cantidadSolCerradasVencidas + cantidadSolVencidas;
        
        return MathUtils.calcularPorcentajeRedondeado(totalSolicitudesVencidas, totalSolicitudesArea, 1) ;
    }
    
    @Override
    public Float porcentajeRetrasos(){
        String query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE estado_solicitud = ? AND fecha_cierre > fecha_vencimiento ";
        Query q = em.createNativeQuery(query);
        q.setParameter(1, Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        
        Long cantidadSolCerradasVencidas = ((BigInteger)q.getSingleResult()).longValue();
        
        query = "SELECT COUNT(*) FROM solicitud_requerimiento WHERE estado_solicitud = ?";
        q = em.createNativeQuery(query);
        q.setParameter(1, Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));

        Long cantidadSolVencidas = ((BigInteger)q.getSingleResult()).longValue();
        
        query = "SELECT COUNT(*) FROM solicitud_requerimiento";
        q = em.createNativeQuery(query);
        Long totalSolicitudesDpto = ((BigInteger)q.getSingleResult()).longValue();
        Long totalSolicitudesVencidas = cantidadSolCerradasVencidas + cantidadSolVencidas;
        
        return MathUtils.calcularPorcentajeRedondeado(totalSolicitudesVencidas, totalSolicitudesDpto, 1) ;
    }
    
}
