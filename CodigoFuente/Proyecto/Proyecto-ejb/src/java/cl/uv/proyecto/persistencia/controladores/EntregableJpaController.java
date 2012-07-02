/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.Entregable;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.TareaScm;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class EntregableJpaController implements Serializable {

    public EntregableJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entregable entregable) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TareaScm tareaScm = entregable.getTareaScm();
            if (tareaScm != null) {
                tareaScm = em.getReference(tareaScm.getClass(), tareaScm.getIdTareaScm());
                entregable.setTareaScm(tareaScm);
            }
            em.persist(entregable);
            if (tareaScm != null) {
                tareaScm.getEntregableList().add(entregable);
                tareaScm = em.merge(tareaScm);
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

    public void edit(Entregable entregable) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Entregable persistentEntregable = em.find(Entregable.class, entregable.getIdEntregable());
            TareaScm tareaScmOld = persistentEntregable.getTareaScm();
            TareaScm tareaScmNew = entregable.getTareaScm();
            if (tareaScmNew != null) {
                tareaScmNew = em.getReference(tareaScmNew.getClass(), tareaScmNew.getIdTareaScm());
                entregable.setTareaScm(tareaScmNew);
            }
            entregable = em.merge(entregable);
            if (tareaScmOld != null && !tareaScmOld.equals(tareaScmNew)) {
                tareaScmOld.getEntregableList().remove(entregable);
                tareaScmOld = em.merge(tareaScmOld);
            }
            if (tareaScmNew != null && !tareaScmNew.equals(tareaScmOld)) {
                tareaScmNew.getEntregableList().add(entregable);
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
                Integer id = entregable.getIdEntregable();
                if (findEntregable(id) == null) {
                    throw new NonexistentEntityException("The entregable with id " + id + " no longer exists.");
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
            Entregable entregable;
            try {
                entregable = em.getReference(Entregable.class, id);
                entregable.getIdEntregable();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entregable with id " + id + " no longer exists.", enfe);
            }
            TareaScm tareaScm = entregable.getTareaScm();
            if (tareaScm != null) {
                tareaScm.getEntregableList().remove(entregable);
                tareaScm = em.merge(tareaScm);
            }
            em.remove(entregable);
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

    public List<Entregable> findEntregableEntities() {
        return findEntregableEntities(true, -1, -1);
    }

    public List<Entregable> findEntregableEntities(int maxResults, int firstResult) {
        return findEntregableEntities(false, maxResults, firstResult);
    }

    private List<Entregable> findEntregableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entregable.class));
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

    public Entregable findEntregable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entregable.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntregableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entregable> rt = cq.from(Entregable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
