/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jano
 */
@Stateless
public class SolicitudRequerimientoFacade extends AbstractFacade<SolicitudRequerimiento> implements SolicitudRequerimientoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    private short ESTADO_INICIAL = 0;
    private short ESTADO_FINAL = 8;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudRequerimientoFacade() {
        super(SolicitudRequerimiento.class);
    }
    
    @Override
    public void edit(SolicitudRequerimiento solicitud) {
        solicitud.setFechaUltimaActualizacion(new Date());
        getEntityManager().merge(solicitud);
    }
    
    @Override
    public List<SolicitudRequerimiento> buscarPorSolicitante(Integer rutSolicitante){
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.solicitante.rut = :rut");
        q.setParameter("rut", rutSolicitante);
        return q.getResultList();
    }
    
    @Override
    public SolicitudRequerimiento buscarPorCodigo(String codigo){
         Query q = em.createNamedQuery("SolicitudRequerimiento.findByCodigoConsulta");
         q.setParameter("codigoConsulta", codigo);
         SolicitudRequerimiento solicitud;
         try{
             solicitud = (SolicitudRequerimiento) q.getSingleResult();
         }catch(NoResultException e){
             solicitud = null;
         }           
         return solicitud;
    }
    
    @Override
    public List<SolicitudRequerimiento> getUltimasSolicitudesEnviadas(Funcionario funcionario, Integer maxResults){
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.solicitante = :solicitante AND s.estadoSolicitud.idEstadoSolicitudRequerimiento <> :idEstado");
        q.setParameter("solicitante", funcionario);
        q.setParameter("idEstado", new Short(ESTADO_FINAL));
        q.setMaxResults(maxResults);
        return q.getResultList();
    }
    
    @Override
    public List<SolicitudRequerimiento> getUltimasSolicitudesCerradas(Funcionario funcionario, Integer maxResults){
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.solicitante = :solicitante AND s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado");
        q.setParameter("solicitante", funcionario);
        q.setParameter("idEstado", new Short(ESTADO_FINAL));
        q.setMaxResults(maxResults);
        return q.getResultList();
    }
    
    @Override
    public List<SolicitudRequerimiento> buscarSolicitudesPorArea(Area area){
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.areaResponsable = :area");
        q.setParameter("area", area);
        return q.getResultList();
    }
    
    
    @Override
    public List<SolicitudRequerimiento> getSolicitudesAsignadas(FuncionarioDisico funcionarioDisico){
        return funcionarioDisico.getSolicitudesRequerimientosAsignadas();
    }
    
    @Override
    public List<SolicitudRequerimiento> getSolicitudesEnviadas(Funcionario funcionario){
        return funcionario.getSolicitudesRequerimientoEnviadas();
    }
}
