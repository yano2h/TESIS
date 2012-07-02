/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class SolicitudCambioJpaController implements Serializable {

    public SolicitudCambioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudCambio solicitudCambio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FormularioImplementacion formularioImplementacion = solicitudCambio.getFormularioImplementacion();
            if (formularioImplementacion != null) {
                formularioImplementacion = em.getReference(formularioImplementacion.getClass(), formularioImplementacion.getIdFormularioImplementacion());
                solicitudCambio.setFormularioImplementacion(formularioImplementacion);
            }
            ItemConfiguracion itemConfiguracion = solicitudCambio.getItemConfiguracion();
            if (itemConfiguracion != null) {
                itemConfiguracion = em.getReference(itemConfiguracion.getClass(), itemConfiguracion.getIdItemConfiguracion());
                solicitudCambio.setItemConfiguracion(itemConfiguracion);
            }
            FuncionarioDisico evaluadorFinal = solicitudCambio.getEvaluadorFinal();
            if (evaluadorFinal != null) {
                evaluadorFinal = em.getReference(evaluadorFinal.getClass(), evaluadorFinal.getRut());
                solicitudCambio.setEvaluadorFinal(evaluadorFinal);
            }
            FuncionarioDisico evaluadorImpacto = solicitudCambio.getEvaluadorImpacto();
            if (evaluadorImpacto != null) {
                evaluadorImpacto = em.getReference(evaluadorImpacto.getClass(), evaluadorImpacto.getRut());
                solicitudCambio.setEvaluadorImpacto(evaluadorImpacto);
            }
            EstadoSolicitudCambio estadoSolicitud = solicitudCambio.getEstadoSolicitud();
            if (estadoSolicitud != null) {
                estadoSolicitud = em.getReference(estadoSolicitud.getClass(), estadoSolicitud.getIdEstadoSolicitudCambio());
                solicitudCambio.setEstadoSolicitud(estadoSolicitud);
            }
            TipoPrioridad prioridadSolicitud = solicitudCambio.getPrioridadSolicitud();
            if (prioridadSolicitud != null) {
                prioridadSolicitud = em.getReference(prioridadSolicitud.getClass(), prioridadSolicitud.getIdTipoPrioridad());
                solicitudCambio.setPrioridadSolicitud(prioridadSolicitud);
            }
            FuncionarioDisico solicitante = solicitudCambio.getSolicitante();
            if (solicitante != null) {
                solicitante = em.getReference(solicitante.getClass(), solicitante.getRut());
                solicitudCambio.setSolicitante(solicitante);
            }
            Proyecto proyecto = solicitudCambio.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getIdProyecto());
                solicitudCambio.setProyecto(proyecto);
            }
            em.persist(solicitudCambio);
            if (formularioImplementacion != null) {
                SolicitudCambio oldSolicitudCambioOfFormularioImplementacion = formularioImplementacion.getSolicitudCambio();
                if (oldSolicitudCambioOfFormularioImplementacion != null) {
                    oldSolicitudCambioOfFormularioImplementacion.setFormularioImplementacion(null);
                    oldSolicitudCambioOfFormularioImplementacion = em.merge(oldSolicitudCambioOfFormularioImplementacion);
                }
                formularioImplementacion.setSolicitudCambio(solicitudCambio);
                formularioImplementacion = em.merge(formularioImplementacion);
            }
            if (itemConfiguracion != null) {
                itemConfiguracion.getSolicitudCambioList().add(solicitudCambio);
                itemConfiguracion = em.merge(itemConfiguracion);
            }
            if (evaluadorFinal != null) {
                evaluadorFinal.getSolicitudesCambioEvaluadorFinal().add(solicitudCambio);
                evaluadorFinal = em.merge(evaluadorFinal);
            }
            if (evaluadorImpacto != null) {
                evaluadorImpacto.getSolicitudesCambioEvaluadorFinal().add(solicitudCambio);
                evaluadorImpacto = em.merge(evaluadorImpacto);
            }
            if (estadoSolicitud != null) {
                estadoSolicitud.getSolicitudCambioList().add(solicitudCambio);
                estadoSolicitud = em.merge(estadoSolicitud);
            }
            if (prioridadSolicitud != null) {
                prioridadSolicitud.getSolicitudCambioList().add(solicitudCambio);
                prioridadSolicitud = em.merge(prioridadSolicitud);
            }
            if (solicitante != null) {
                solicitante.getSolicitudesCambioEvaluadorFinal().add(solicitudCambio);
                solicitante = em.merge(solicitante);
            }
            if (proyecto != null) {
                proyecto.getSolicitudesDeCambio().add(solicitudCambio);
                proyecto = em.merge(proyecto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SolicitudCambio solicitudCambio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudCambio persistentSolicitudCambio = em.find(SolicitudCambio.class, solicitudCambio.getIdSolicitudCambio());
            FormularioImplementacion formularioImplementacionOld = persistentSolicitudCambio.getFormularioImplementacion();
            FormularioImplementacion formularioImplementacionNew = solicitudCambio.getFormularioImplementacion();
            ItemConfiguracion itemConfiguracionOld = persistentSolicitudCambio.getItemConfiguracion();
            ItemConfiguracion itemConfiguracionNew = solicitudCambio.getItemConfiguracion();
            FuncionarioDisico evaluadorFinalOld = persistentSolicitudCambio.getEvaluadorFinal();
            FuncionarioDisico evaluadorFinalNew = solicitudCambio.getEvaluadorFinal();
            FuncionarioDisico evaluadorImpactoOld = persistentSolicitudCambio.getEvaluadorImpacto();
            FuncionarioDisico evaluadorImpactoNew = solicitudCambio.getEvaluadorImpacto();
            EstadoSolicitudCambio estadoSolicitudOld = persistentSolicitudCambio.getEstadoSolicitud();
            EstadoSolicitudCambio estadoSolicitudNew = solicitudCambio.getEstadoSolicitud();
            TipoPrioridad prioridadSolicitudOld = persistentSolicitudCambio.getPrioridadSolicitud();
            TipoPrioridad prioridadSolicitudNew = solicitudCambio.getPrioridadSolicitud();
            FuncionarioDisico solicitanteOld = persistentSolicitudCambio.getSolicitante();
            FuncionarioDisico solicitanteNew = solicitudCambio.getSolicitante();
            Proyecto proyectoOld = persistentSolicitudCambio.getProyecto();
            Proyecto proyectoNew = solicitudCambio.getProyecto();
            List<String> illegalOrphanMessages = null;
            if (formularioImplementacionOld != null && !formularioImplementacionOld.equals(formularioImplementacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain FormularioImplementacion " + formularioImplementacionOld + " since its solicitudCambio field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (formularioImplementacionNew != null) {
                formularioImplementacionNew = em.getReference(formularioImplementacionNew.getClass(), formularioImplementacionNew.getIdFormularioImplementacion());
                solicitudCambio.setFormularioImplementacion(formularioImplementacionNew);
            }
            if (itemConfiguracionNew != null) {
                itemConfiguracionNew = em.getReference(itemConfiguracionNew.getClass(), itemConfiguracionNew.getIdItemConfiguracion());
                solicitudCambio.setItemConfiguracion(itemConfiguracionNew);
            }
            if (evaluadorFinalNew != null) {
                evaluadorFinalNew = em.getReference(evaluadorFinalNew.getClass(), evaluadorFinalNew.getRut());
                solicitudCambio.setEvaluadorFinal(evaluadorFinalNew);
            }
            if (evaluadorImpactoNew != null) {
                evaluadorImpactoNew = em.getReference(evaluadorImpactoNew.getClass(), evaluadorImpactoNew.getRut());
                solicitudCambio.setEvaluadorImpacto(evaluadorImpactoNew);
            }
            if (estadoSolicitudNew != null) {
                estadoSolicitudNew = em.getReference(estadoSolicitudNew.getClass(), estadoSolicitudNew.getIdEstadoSolicitudCambio());
                solicitudCambio.setEstadoSolicitud(estadoSolicitudNew);
            }
            if (prioridadSolicitudNew != null) {
                prioridadSolicitudNew = em.getReference(prioridadSolicitudNew.getClass(), prioridadSolicitudNew.getIdTipoPrioridad());
                solicitudCambio.setPrioridadSolicitud(prioridadSolicitudNew);
            }
            if (solicitanteNew != null) {
                solicitanteNew = em.getReference(solicitanteNew.getClass(), solicitanteNew.getRut());
                solicitudCambio.setSolicitante(solicitanteNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getIdProyecto());
                solicitudCambio.setProyecto(proyectoNew);
            }
            solicitudCambio = em.merge(solicitudCambio);
            if (formularioImplementacionNew != null && !formularioImplementacionNew.equals(formularioImplementacionOld)) {
                SolicitudCambio oldSolicitudCambioOfFormularioImplementacion = formularioImplementacionNew.getSolicitudCambio();
                if (oldSolicitudCambioOfFormularioImplementacion != null) {
                    oldSolicitudCambioOfFormularioImplementacion.setFormularioImplementacion(null);
                    oldSolicitudCambioOfFormularioImplementacion = em.merge(oldSolicitudCambioOfFormularioImplementacion);
                }
                formularioImplementacionNew.setSolicitudCambio(solicitudCambio);
                formularioImplementacionNew = em.merge(formularioImplementacionNew);
            }
            if (itemConfiguracionOld != null && !itemConfiguracionOld.equals(itemConfiguracionNew)) {
                itemConfiguracionOld.getSolicitudCambioList().remove(solicitudCambio);
                itemConfiguracionOld = em.merge(itemConfiguracionOld);
            }
            if (itemConfiguracionNew != null && !itemConfiguracionNew.equals(itemConfiguracionOld)) {
                itemConfiguracionNew.getSolicitudCambioList().add(solicitudCambio);
                itemConfiguracionNew = em.merge(itemConfiguracionNew);
            }
            if (evaluadorFinalOld != null && !evaluadorFinalOld.equals(evaluadorFinalNew)) {
                evaluadorFinalOld.getSolicitudesCambioEvaluadorFinal().remove(solicitudCambio);
                evaluadorFinalOld = em.merge(evaluadorFinalOld);
            }
            if (evaluadorFinalNew != null && !evaluadorFinalNew.equals(evaluadorFinalOld)) {
                evaluadorFinalNew.getSolicitudesCambioEvaluadorFinal().add(solicitudCambio);
                evaluadorFinalNew = em.merge(evaluadorFinalNew);
            }
            if (evaluadorImpactoOld != null && !evaluadorImpactoOld.equals(evaluadorImpactoNew)) {
                evaluadorImpactoOld.getSolicitudesCambioEvaluadorFinal().remove(solicitudCambio);
                evaluadorImpactoOld = em.merge(evaluadorImpactoOld);
            }
            if (evaluadorImpactoNew != null && !evaluadorImpactoNew.equals(evaluadorImpactoOld)) {
                evaluadorImpactoNew.getSolicitudesCambioEvaluadorFinal().add(solicitudCambio);
                evaluadorImpactoNew = em.merge(evaluadorImpactoNew);
            }
            if (estadoSolicitudOld != null && !estadoSolicitudOld.equals(estadoSolicitudNew)) {
                estadoSolicitudOld.getSolicitudCambioList().remove(solicitudCambio);
                estadoSolicitudOld = em.merge(estadoSolicitudOld);
            }
            if (estadoSolicitudNew != null && !estadoSolicitudNew.equals(estadoSolicitudOld)) {
                estadoSolicitudNew.getSolicitudCambioList().add(solicitudCambio);
                estadoSolicitudNew = em.merge(estadoSolicitudNew);
            }
            if (prioridadSolicitudOld != null && !prioridadSolicitudOld.equals(prioridadSolicitudNew)) {
                prioridadSolicitudOld.getSolicitudCambioList().remove(solicitudCambio);
                prioridadSolicitudOld = em.merge(prioridadSolicitudOld);
            }
            if (prioridadSolicitudNew != null && !prioridadSolicitudNew.equals(prioridadSolicitudOld)) {
                prioridadSolicitudNew.getSolicitudCambioList().add(solicitudCambio);
                prioridadSolicitudNew = em.merge(prioridadSolicitudNew);
            }
            if (solicitanteOld != null && !solicitanteOld.equals(solicitanteNew)) {
                solicitanteOld.getSolicitudesCambioEvaluadorFinal().remove(solicitudCambio);
                solicitanteOld = em.merge(solicitanteOld);
            }
            if (solicitanteNew != null && !solicitanteNew.equals(solicitanteOld)) {
                solicitanteNew.getSolicitudesCambioEvaluadorFinal().add(solicitudCambio);
                solicitanteNew = em.merge(solicitanteNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getSolicitudesDeCambio().remove(solicitudCambio);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getSolicitudesDeCambio().add(solicitudCambio);
                proyectoNew = em.merge(proyectoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitudCambio.getIdSolicitudCambio();
                if (findSolicitudCambio(id) == null) {
                    throw new NonexistentEntityException("The solicitudCambio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudCambio solicitudCambio;
            try {
                solicitudCambio = em.getReference(SolicitudCambio.class, id);
                solicitudCambio.getIdSolicitudCambio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudCambio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            FormularioImplementacion formularioImplementacionOrphanCheck = solicitudCambio.getFormularioImplementacion();
            if (formularioImplementacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SolicitudCambio (" + solicitudCambio + ") cannot be destroyed since the FormularioImplementacion " + formularioImplementacionOrphanCheck + " in its formularioImplementacion field has a non-nullable solicitudCambio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ItemConfiguracion itemConfiguracion = solicitudCambio.getItemConfiguracion();
            if (itemConfiguracion != null) {
                itemConfiguracion.getSolicitudCambioList().remove(solicitudCambio);
                itemConfiguracion = em.merge(itemConfiguracion);
            }
            FuncionarioDisico evaluadorFinal = solicitudCambio.getEvaluadorFinal();
            if (evaluadorFinal != null) {
                evaluadorFinal.getSolicitudesCambioEvaluadorFinal().remove(solicitudCambio);
                evaluadorFinal = em.merge(evaluadorFinal);
            }
            FuncionarioDisico evaluadorImpacto = solicitudCambio.getEvaluadorImpacto();
            if (evaluadorImpacto != null) {
                evaluadorImpacto.getSolicitudesCambioEvaluadorFinal().remove(solicitudCambio);
                evaluadorImpacto = em.merge(evaluadorImpacto);
            }
            EstadoSolicitudCambio estadoSolicitud = solicitudCambio.getEstadoSolicitud();
            if (estadoSolicitud != null) {
                estadoSolicitud.getSolicitudCambioList().remove(solicitudCambio);
                estadoSolicitud = em.merge(estadoSolicitud);
            }
            TipoPrioridad prioridadSolicitud = solicitudCambio.getPrioridadSolicitud();
            if (prioridadSolicitud != null) {
                prioridadSolicitud.getSolicitudCambioList().remove(solicitudCambio);
                prioridadSolicitud = em.merge(prioridadSolicitud);
            }
            FuncionarioDisico solicitante = solicitudCambio.getSolicitante();
            if (solicitante != null) {
                solicitante.getSolicitudesCambioEvaluadorFinal().remove(solicitudCambio);
                solicitante = em.merge(solicitante);
            }
            Proyecto proyecto = solicitudCambio.getProyecto();
            if (proyecto != null) {
                proyecto.getSolicitudesDeCambio().remove(solicitudCambio);
                proyecto = em.merge(proyecto);
            }
            em.remove(solicitudCambio);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SolicitudCambio> findSolicitudCambioEntities() {
        return findSolicitudCambioEntities(true, -1, -1);
    }

    public List<SolicitudCambio> findSolicitudCambioEntities(int maxResults, int firstResult) {
        return findSolicitudCambioEntities(false, maxResults, firstResult);
    }

    private List<SolicitudCambio> findSolicitudCambioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudCambio.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SolicitudCambio findSolicitudCambio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudCambio.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudCambioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudCambio> rt = cq.from(SolicitudCambio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
