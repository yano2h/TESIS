/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class TareaProyectoJpaController implements Serializable {

    public TareaProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TareaProyecto tareaProyecto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FuncionarioDisico responsableTarea = tareaProyecto.getResponsableTarea();
            if (responsableTarea != null) {
                responsableTarea = em.getReference(responsableTarea.getClass(), responsableTarea.getRut());
                tareaProyecto.setResponsableTarea(responsableTarea);
            }
            Proyecto proyecto = tareaProyecto.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getIdProyecto());
                tareaProyecto.setProyecto(proyecto);
            }
            em.persist(tareaProyecto);
            if (responsableTarea != null) {
                responsableTarea.getTareasProyectoAgendadas().add(tareaProyecto);
                responsableTarea = em.merge(responsableTarea);
            }
            if (proyecto != null) {
                proyecto.getTareasAgendadas().add(tareaProyecto);
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

    public void edit(TareaProyecto tareaProyecto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TareaProyecto persistentTareaProyecto = em.find(TareaProyecto.class, tareaProyecto.getIdTareaProyecto());
            FuncionarioDisico responsableTareaOld = persistentTareaProyecto.getResponsableTarea();
            FuncionarioDisico responsableTareaNew = tareaProyecto.getResponsableTarea();
            Proyecto proyectoOld = persistentTareaProyecto.getProyecto();
            Proyecto proyectoNew = tareaProyecto.getProyecto();
            if (responsableTareaNew != null) {
                responsableTareaNew = em.getReference(responsableTareaNew.getClass(), responsableTareaNew.getRut());
                tareaProyecto.setResponsableTarea(responsableTareaNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getIdProyecto());
                tareaProyecto.setProyecto(proyectoNew);
            }
            tareaProyecto = em.merge(tareaProyecto);
            if (responsableTareaOld != null && !responsableTareaOld.equals(responsableTareaNew)) {
                responsableTareaOld.getTareasProyectoAgendadas().remove(tareaProyecto);
                responsableTareaOld = em.merge(responsableTareaOld);
            }
            if (responsableTareaNew != null && !responsableTareaNew.equals(responsableTareaOld)) {
                responsableTareaNew.getTareasProyectoAgendadas().add(tareaProyecto);
                responsableTareaNew = em.merge(responsableTareaNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getTareasAgendadas().remove(tareaProyecto);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getTareasAgendadas().add(tareaProyecto);
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
                Integer id = tareaProyecto.getIdTareaProyecto();
                if (findTareaProyecto(id) == null) {
                    throw new NonexistentEntityException("The tareaProyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TareaProyecto tareaProyecto;
            try {
                tareaProyecto = em.getReference(TareaProyecto.class, id);
                tareaProyecto.getIdTareaProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareaProyecto with id " + id + " no longer exists.", enfe);
            }
            FuncionarioDisico responsableTarea = tareaProyecto.getResponsableTarea();
            if (responsableTarea != null) {
                responsableTarea.getTareasProyectoAgendadas().remove(tareaProyecto);
                responsableTarea = em.merge(responsableTarea);
            }
            Proyecto proyecto = tareaProyecto.getProyecto();
            if (proyecto != null) {
                proyecto.getTareasAgendadas().remove(tareaProyecto);
                proyecto = em.merge(proyecto);
            }
            em.remove(tareaProyecto);
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

    public List<TareaProyecto> findTareaProyectoEntities() {
        return findTareaProyectoEntities(true, -1, -1);
    }

    public List<TareaProyecto> findTareaProyectoEntities(int maxResults, int firstResult) {
        return findTareaProyectoEntities(false, maxResults, firstResult);
    }

    private List<TareaProyecto> findTareaProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TareaProyecto.class));
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

    public TareaProyecto findTareaProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TareaProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareaProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TareaProyecto> rt = cq.from(TareaProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
