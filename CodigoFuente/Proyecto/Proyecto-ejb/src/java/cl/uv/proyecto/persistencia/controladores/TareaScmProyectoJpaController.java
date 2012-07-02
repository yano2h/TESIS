/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class TareaScmProyectoJpaController implements Serializable {

    public TareaScmProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TareaScmProyecto tareaScmProyecto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tareaScmProyecto.getTareaScmProyectoPK() == null) {
            tareaScmProyecto.setTareaScmProyectoPK(new TareaScmProyectoPK());
        }
        tareaScmProyecto.getTareaScmProyectoPK().setIdTareaScm(tareaScmProyecto.getTareaScm().getIdTareaScm());
        tareaScmProyecto.getTareaScmProyectoPK().setIdProyecto(tareaScmProyecto.getProyecto().getIdProyecto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FuncionarioDisico responsable = tareaScmProyecto.getResponsable();
            if (responsable != null) {
                responsable = em.getReference(responsable.getClass(), responsable.getRut());
                tareaScmProyecto.setResponsable(responsable);
            }
            Proyecto proyecto = tareaScmProyecto.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getIdProyecto());
                tareaScmProyecto.setProyecto(proyecto);
            }
            TareaScm tareaScm = tareaScmProyecto.getTareaScm();
            if (tareaScm != null) {
                tareaScm = em.getReference(tareaScm.getClass(), tareaScm.getIdTareaScm());
                tareaScmProyecto.setTareaScm(tareaScm);
            }
            em.persist(tareaScmProyecto);
            if (responsable != null) {
                responsable.getTareasScmAsignadas().add(tareaScmProyecto);
                responsable = em.merge(responsable);
            }
            if (proyecto != null) {
                proyecto.getTareasScmProyecto().add(tareaScmProyecto);
                proyecto = em.merge(proyecto);
            }
            if (tareaScm != null) {
                tareaScm.getTareaScmProyectoList().add(tareaScmProyecto);
                tareaScm = em.merge(tareaScm);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTareaScmProyecto(tareaScmProyecto.getTareaScmProyectoPK()) != null) {
                throw new PreexistingEntityException("TareaScmProyecto " + tareaScmProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TareaScmProyecto tareaScmProyecto) throws NonexistentEntityException, RollbackFailureException, Exception {
        tareaScmProyecto.getTareaScmProyectoPK().setIdTareaScm(tareaScmProyecto.getTareaScm().getIdTareaScm());
        tareaScmProyecto.getTareaScmProyectoPK().setIdProyecto(tareaScmProyecto.getProyecto().getIdProyecto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TareaScmProyecto persistentTareaScmProyecto = em.find(TareaScmProyecto.class, tareaScmProyecto.getTareaScmProyectoPK());
            FuncionarioDisico responsableOld = persistentTareaScmProyecto.getResponsable();
            FuncionarioDisico responsableNew = tareaScmProyecto.getResponsable();
            Proyecto proyectoOld = persistentTareaScmProyecto.getProyecto();
            Proyecto proyectoNew = tareaScmProyecto.getProyecto();
            TareaScm tareaScmOld = persistentTareaScmProyecto.getTareaScm();
            TareaScm tareaScmNew = tareaScmProyecto.getTareaScm();
            if (responsableNew != null) {
                responsableNew = em.getReference(responsableNew.getClass(), responsableNew.getRut());
                tareaScmProyecto.setResponsable(responsableNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getIdProyecto());
                tareaScmProyecto.setProyecto(proyectoNew);
            }
            if (tareaScmNew != null) {
                tareaScmNew = em.getReference(tareaScmNew.getClass(), tareaScmNew.getIdTareaScm());
                tareaScmProyecto.setTareaScm(tareaScmNew);
            }
            tareaScmProyecto = em.merge(tareaScmProyecto);
            if (responsableOld != null && !responsableOld.equals(responsableNew)) {
                responsableOld.getTareasScmAsignadas().remove(tareaScmProyecto);
                responsableOld = em.merge(responsableOld);
            }
            if (responsableNew != null && !responsableNew.equals(responsableOld)) {
                responsableNew.getTareasScmAsignadas().add(tareaScmProyecto);
                responsableNew = em.merge(responsableNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getTareasScmProyecto().remove(tareaScmProyecto);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getTareasScmProyecto().add(tareaScmProyecto);
                proyectoNew = em.merge(proyectoNew);
            }
            if (tareaScmOld != null && !tareaScmOld.equals(tareaScmNew)) {
                tareaScmOld.getTareaScmProyectoList().remove(tareaScmProyecto);
                tareaScmOld = em.merge(tareaScmOld);
            }
            if (tareaScmNew != null && !tareaScmNew.equals(tareaScmOld)) {
                tareaScmNew.getTareaScmProyectoList().add(tareaScmProyecto);
                tareaScmNew = em.merge(tareaScmNew);
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
                TareaScmProyectoPK id = tareaScmProyecto.getTareaScmProyectoPK();
                if (findTareaScmProyecto(id) == null) {
                    throw new NonexistentEntityException("The tareaScmProyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TareaScmProyectoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TareaScmProyecto tareaScmProyecto;
            try {
                tareaScmProyecto = em.getReference(TareaScmProyecto.class, id);
                tareaScmProyecto.getTareaScmProyectoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareaScmProyecto with id " + id + " no longer exists.", enfe);
            }
            FuncionarioDisico responsable = tareaScmProyecto.getResponsable();
            if (responsable != null) {
                responsable.getTareasScmAsignadas().remove(tareaScmProyecto);
                responsable = em.merge(responsable);
            }
            Proyecto proyecto = tareaScmProyecto.getProyecto();
            if (proyecto != null) {
                proyecto.getTareasScmProyecto().remove(tareaScmProyecto);
                proyecto = em.merge(proyecto);
            }
            TareaScm tareaScm = tareaScmProyecto.getTareaScm();
            if (tareaScm != null) {
                tareaScm.getTareaScmProyectoList().remove(tareaScmProyecto);
                tareaScm = em.merge(tareaScm);
            }
            em.remove(tareaScmProyecto);
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

    public List<TareaScmProyecto> findTareaScmProyectoEntities() {
        return findTareaScmProyectoEntities(true, -1, -1);
    }

    public List<TareaScmProyecto> findTareaScmProyectoEntities(int maxResults, int firstResult) {
        return findTareaScmProyectoEntities(false, maxResults, firstResult);
    }

    private List<TareaScmProyecto> findTareaScmProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TareaScmProyecto.class));
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

    public TareaScmProyecto findTareaScmProyecto(TareaScmProyectoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TareaScmProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareaScmProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TareaScmProyecto> rt = cq.from(TareaScmProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
