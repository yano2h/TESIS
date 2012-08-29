/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 *
 * @author Jano
 */
@Stateless
public class SolicitudRequerimientoFacade extends AbstractFacade<SolicitudRequerimiento> implements SolicitudRequerimientoFacadeLocal {

    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

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
    public List<SolicitudRequerimiento> buscarPorSolicitante(Integer rutSolicitante) {
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.solicitante.rut = :rut");
        q.setParameter("rut", rutSolicitante);
        return q.getResultList();
    }

    @Override
    public SolicitudRequerimiento buscarPorCodigo(String codigo) {
        Query q = em.createNamedQuery("SolicitudRequerimiento.findByCodigoConsulta");
        q.setParameter("codigoConsulta", codigo);
        SolicitudRequerimiento solicitud;
        try {
            solicitud = (SolicitudRequerimiento) q.getSingleResult();
        } catch (NoResultException e) {
            solicitud = null;
        }
        return solicitud;
    }

    @Override
    public List<SolicitudRequerimiento> getUltimasSolicitudesEnviadas(Funcionario funcionario, Integer maxResults) {
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.solicitante = :solicitante AND s.estadoSolicitud.idEstadoSolicitudRequerimiento <> :idEstado");
        q.setParameter("solicitante", funcionario);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        q.setMaxResults(maxResults);
        return q.getResultList();
    }

    @Override
    public List<SolicitudRequerimiento> getUltimasSolicitudesCerradas(Funcionario funcionario, Integer maxResults) {
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.solicitante = :solicitante AND s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado");
        q.setParameter("solicitante", funcionario);
        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        q.setMaxResults(maxResults);
        return q.getResultList();
    }

    @Override
    public List<SolicitudRequerimiento> buscarSolicitudesPorArea(Area area) {
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.areaResponsable = :area ORDER BY s.fechaEnvio DESC");
        q.setParameter("area", area);
        return q.getResultList();
    }

    @Override
    public List<SolicitudRequerimiento> getSolicitudesAsignadas(FuncionarioDisico funcionarioDisico) {
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.responsable = :responsable ORDER BY s.fechaEnvio DESC");
        q.setParameter("responsable", funcionarioDisico);
        return q.getResultList();
    }

    @Override
    public List<SolicitudRequerimiento> getSolicitudesEnviadas(Funcionario funcionario) {
        return funcionario.getSolicitudesRequerimientoEnviadas();
    }

    @Override
    public void contarSolicitudes(FuncionarioDisico funcionario) {
        String query = "SELECT COUNT(s) FROM SolicitudRequerimiento s WHERE s.responsable = :responsable AND s.estadoSolicitud.idEstadoSolicitudRequerimiento = :idEstado";
        Query q = em.createQuery(query);
        q.setParameter("responsable", funcionario);

        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_ASIGNADA"));
        funcionario.setCantidadDeSolicitudesAsignadas(((Long) q.getSingleResult()).intValue());

        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_PENDIENTE"));
        funcionario.setCantidadDeSolicitudesPendientes(((Long) q.getSingleResult()).intValue());

        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_INICIADA"));
        funcionario.setCantidadDeSolicitudesIniciadas(((Long) q.getSingleResult()).intValue());

        q.setParameter("idEstado", Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
        funcionario.setCantidadDeSolicitudesVencidas(((Long) q.getSingleResult()).intValue());
    }

    @Override
    public List<SolicitudRequerimiento> buscarSolicitudPorFiltros(SolicitudRequerimiento solicitud, Date minDate, Date maxDate) {
        
        //Create query
        String select = "SELECT s FROM SolicitudRequerimiento s ";
        String where = "WHERE ";
        String condicion = "";
        String order = " ORDER BY s.fechaEnvio DESC";
        
        if (!solicitud.getCodigoConsulta().isEmpty()) {
            condicion += " s.codigoConsulta LIKE :codigoConsulta ";
        }

        if (!solicitud.getAsunto().isEmpty()) {
            condicion += (condicion.isEmpty()) ? " s.asunto LIKE :asunto" : " AND s.asunto LIKE :asunto";
        }

        if (!solicitud.getMensaje().isEmpty()) {
            condicion += (condicion.isEmpty()) ? " s.mensaje LIKE :mensaje" : " AND s.mensaje LIKE :mensaje";
        }

        if (solicitud.getEstadoSolicitud() != null) {
            condicion += (condicion.isEmpty()) ? " s.estadoSolicitud = :estadoSolicitud" : " AND s.estadoSolicitud = :estadoSolicitud";
        }

        if (solicitud.getPrioridadSolicitud() != null) {
            condicion += (condicion.isEmpty()) ? " s.prioridadSolicitud = :prioridadSolicitud" : " AND s.prioridadSolicitud = :prioridadSolicitud";
        }

        if (solicitud.getAreaResponsable() != null) {
            condicion += (condicion.isEmpty()) ? " s.areaResponsable = :areaResponsable" : " AND s.areaResponsable = :areaResponsable";
        }

        if (solicitud.getTipoSolicitud() != null) {
            condicion += (condicion.isEmpty()) ? " s.tipoSolicitud = :tipoSolicitud" : " AND s.tipoSolicitud = :tipoSolicitud";
        }
        
        if (minDate != null) {
            condicion += (condicion.isEmpty()) ? " s.fechaEnvio >= :minFechaEnvio" : " AND s.fechaEnvio >= :minFechaEnvio";
        }
        
        if (maxDate != null) {
            condicion += (condicion.isEmpty()) ? " s.fechaEnvio <= :maxFechaEnvio" : " AND s.fechaEnvio <= :maxFechaEnvio";
        }

        //Set Paramenters
        String consulta = (condicion.isEmpty())? select+order : select+where+condicion+order;        
        Query q = em.createQuery(consulta);
        
        if (!solicitud.getCodigoConsulta().isEmpty()) {
            q.setParameter("codigoConsulta", "%"+solicitud.getCodigoConsulta()+"%");
        }

        if (!solicitud.getAsunto().isEmpty()) {
            String asunto = "%"+solicitud.getAsunto()+"%";
            asunto = asunto.replaceAll(" ", "%");
            q.setParameter("asunto", asunto);
        }

        if (!solicitud.getMensaje().isEmpty()) {
            String mensaje = "%"+solicitud.getMensaje()+"%";
            mensaje = mensaje.replaceAll(" ", "%");
            q.setParameter("mensaje", mensaje);
        }

        if (solicitud.getEstadoSolicitud() != null) {
            q.setParameter("estadoSolicitud", solicitud.getEstadoSolicitud());
        }

        if (solicitud.getPrioridadSolicitud() != null) {
            q.setParameter("prioridadSolicitud", solicitud.getPrioridadSolicitud());
        }

        if (solicitud.getAreaResponsable() != null) {
            q.setParameter("areaResponsable", solicitud.getAreaResponsable());
        }

        if (solicitud.getTipoSolicitud() != null) {
            q.setParameter("tipoSolicitud", solicitud.getTipoSolicitud());
        }
        
        if (minDate != null) {
            q.setParameter("minFechaEnvio", minDate, TemporalType.DATE);
        }
        
        if (maxDate != null) {
            q.setParameter("maxFechaEnvio", maxDate, TemporalType.DATE);
        }
        
        return q.getResultList();
    }
}
