/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jano
 */
@Stateless
public class SolicitudCambioFacade extends AbstractFacade<SolicitudCambio> implements SolicitudCambioFacadeLocal {

    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;
    @EJB
    private EstadoSolicitudCambioFacadeLocal estadoSolicitudCambioFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudCambioFacade() {
        super(SolicitudCambio.class);
    }

    @Override
    public void enviarSolicitudCambio(SolicitudCambio sc, FuncionarioDisico solicitante) {
        sc.setSolicitante(solicitante);
        sc.setFechaEnvio(new Date());
        sc.setEstadoSolicitud( estadoSolicitudCambioFacade.find( Resources.getValueShort("Estados", "EstadoSC_ENVIADA") ));
        create(sc);
    }
    
    @Override
    public void guardarAnalisisImpacto(SolicitudCambio sc, FuncionarioDisico funcionario){
        sc.setEvaluadorImpacto(funcionario);
        sc.setFechaAnalisis(new Date());
        sc.setEstadoSolicitud(estadoSolicitudCambioFacade.find( Resources.getValueShort("Estados", "EstadoSC_ANALISADA") ));
        edit(sc);
    }

    @Override
    public void guardarEvaluacionSolicitud(SolicitudCambio sc, FuncionarioDisico funcionario){
        sc.setEvaluadorFinal(funcionario);
        sc.setFechaCierre(new Date());
        if(sc.getAprobada()){
            sc.setEstadoSolicitud(estadoSolicitudCambioFacade.find( Resources.getValueShort("Estados", "EstadoSC_APROBADA") ));
        }else{
            sc.setEstadoSolicitud(estadoSolicitudCambioFacade.find( Resources.getValueShort("Estados", "EstadoSC_RECHAZADA") ));
        }
        edit(sc);
    }
    
    @Override
    public List<SolicitudCambio> buscarSolicitudPorProyecto(Proyecto proyecto) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s WHERE s.proyecto = :proyecto ORDER BY s.fechaEnvio DESC");
        q.setParameter("proyecto", proyecto);
        return q.getResultList();
    }

    @Override
    public List<SolicitudCambio> buscarSolicitudAnalisisPendiente(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s, TareaScmProyecto t WHERE t.responsable = :responsable AND t.tareaScmProyectoPK.idTareaScm = :idTarea  AND s.proyecto = t.proyecto AND s.estadoSolicitud = :estado ORDER BY s.fechaEnvio DESC");
        q.setParameter("responsable", funcionario);
        q.setParameter("idTarea", Resources.getValueInteger("TareasSCM", "TSCM_ANALIZAR_SC") );
        q.setParameter("estado", estadoSolicitudCambioFacade.find( Resources.getValueShort("Estados", "EstadoSC_ENVIADA") ));
        return q.getResultList();
    }

    @Override
    public Long contarSolicitudAnalisisPendiente(FuncionarioDisico funcionario) {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM tarea_scm_proyecto t, solicitud_cambio s WHERE t.responsable = ? AND t.id_tarea_scm = ? AND s.proyecto = t.id_proyecto AND s.estado_solicitud = ?");
        q.setParameter(1, funcionario.getRut());
        q.setParameter(2, Resources.getValueInteger("TareasSCM", "TSCM_ANALIZAR_SC") );
        q.setParameter(3, Resources.getValueShort("Estados", "EstadoSC_ENVIADA"));
        return ((BigInteger) q.getSingleResult()).longValue();
    }
    
    @Override
    public List<SolicitudCambio> buscarSolicitudAnalisadas(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s WHERE s.evaluadorImpacto = :evaluador ORDER BY s.fechaEnvio DESC");
        q.setParameter("evaluador", funcionario);
        return q.getResultList();
    }
    
    @Override
    public List<SolicitudCambio> buscarSolicitudEvaluacionPendiente(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s, TareaScmProyecto t WHERE t.responsable = :responsable AND t.tareaScmProyectoPK.idTareaScm = :idTarea  AND s.proyecto = t.proyecto AND s.estadoSolicitud = :estado ORDER BY s.fechaEnvio DESC");
        q.setParameter("responsable", funcionario);
        q.setParameter("idTarea", Resources.getValueInteger("TareasSCM", "TSCM_APROBAR_SC"));
        q.setParameter("estado", estadoSolicitudCambioFacade.find( Resources.getValueShort("Estados", "EstadoSC_ANALISADA") ));
        return q.getResultList();
    }

    @Override
     public Long contarSolicitudEvaluacionPendiente(FuncionarioDisico funcionario) {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM tarea_scm_proyecto t, solicitud_cambio s WHERE t.responsable = ? AND t.id_tarea_scm = ? AND s.proyecto = t.id_proyecto AND s.estado_solicitud = ?");
        q.setParameter(1, funcionario.getRut());
        q.setParameter(2, Resources.getValueInteger("TareasSCM", "TSCM_APROBAR_SC"));
        q.setParameter(3, Resources.getValueShort("Estados", "EstadoSC_ANALISADA"));
        return ((BigInteger) q.getSingleResult()).longValue();
    }
     
    @Override
    public List<SolicitudCambio> buscarSolicitudEvaluadas(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s WHERE s.evaluadorFinal = :evaluador ORDER BY s.fechaEnvio DESC");
        q.setParameter("evaluador", funcionario);
        return q.getResultList();
    }
    
    @Override
    public List<SolicitudCambio> buscarSolicitudImplementacionPendiente(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s, TareaScmProyecto t WHERE t.responsable = :responsable AND t.tareaScmProyectoPK.idTareaScm = :idTarea  AND s.proyecto = t.proyecto AND s.estadoSolicitud = :estado ORDER BY s.fechaEnvio DESC");
        q.setParameter("responsable", funcionario);
        q.setParameter("idTarea", Resources.getValueInteger("TareasSCM", "TSCM_IMPLEMENTAR_SC"));
        q.setParameter("estado", estadoSolicitudCambioFacade.find( Resources.getValueShort("Estados", "EstadoSC_APROBADA") ));
        return q.getResultList();
    }
    
    @Override
    public Long contarSolicitudImplementacionPendiente(FuncionarioDisico funcionario) {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM tarea_scm_proyecto t, solicitud_cambio s WHERE t.responsable = ? AND t.id_tarea_scm = ? AND s.proyecto = t.id_proyecto AND s.estado_solicitud = ?");
        q.setParameter(1, funcionario.getRut());
        q.setParameter(2, Resources.getValueInteger("TareasSCM", "TSCM_IMPLEMENTAR_SC"));
        q.setParameter(3, Resources.getValueShort("Estados", "EstadoSC_APROBADA"));
        return ((BigInteger) q.getSingleResult()).longValue();
    }

    @Override
    public List<SolicitudCambio> buscarSolicitudImplementadas(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s WHERE s.formularioImplementacion.implementador = :implementador ORDER BY s.fechaEnvio DESC");
        q.setParameter("implementador", funcionario);
        return q.getResultList();
    }
}
