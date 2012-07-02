/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.Entregable;
import cl.uv.proyecto.persistencia.entidades.TareaScm;
import java.util.ArrayList;
import java.util.List;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyecto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class TareaScmJpaController implements Serializable {

    public TareaScmJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TareaScm tareaScm) throws RollbackFailureException, Exception {
        if (tareaScm.getEntregableList() == null) {
            tareaScm.setEntregableList(new ArrayList<Entregable>());
        }
        if (tareaScm.getTareaScmProyectoList() == null) {
            tareaScm.setTareaScmProyectoList(new ArrayList<TareaScmProyecto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Entregable> attachedEntregableList = new ArrayList<Entregable>();
            for (Entregable entregableListEntregableToAttach : tareaScm.getEntregableList()) {
                entregableListEntregableToAttach = em.getReference(entregableListEntregableToAttach.getClass(), entregableListEntregableToAttach.getIdEntregable());
                attachedEntregableList.add(entregableListEntregableToAttach);
            }
            tareaScm.setEntregableList(attachedEntregableList);
            List<TareaScmProyecto> attachedTareaScmProyectoList = new ArrayList<TareaScmProyecto>();
            for (TareaScmProyecto tareaScmProyectoListTareaScmProyectoToAttach : tareaScm.getTareaScmProyectoList()) {
                tareaScmProyectoListTareaScmProyectoToAttach = em.getReference(tareaScmProyectoListTareaScmProyectoToAttach.getClass(), tareaScmProyectoListTareaScmProyectoToAttach.getTareaScmProyectoPK());
                attachedTareaScmProyectoList.add(tareaScmProyectoListTareaScmProyectoToAttach);
            }
            tareaScm.setTareaScmProyectoList(attachedTareaScmProyectoList);
            em.persist(tareaScm);
            for (Entregable entregableListEntregable : tareaScm.getEntregableList()) {
                TareaScm oldTareaScmOfEntregableListEntregable = entregableListEntregable.getTareaScm();
                entregableListEntregable.setTareaScm(tareaScm);
                entregableListEntregable = em.merge(entregableListEntregable);
                if (oldTareaScmOfEntregableListEntregable != null) {
                    oldTareaScmOfEntregableListEntregable.getEntregableList().remove(entregableListEntregable);
                    oldTareaScmOfEntregableListEntregable = em.merge(oldTareaScmOfEntregableListEntregable);
                }
            }
            for (TareaScmProyecto tareaScmProyectoListTareaScmProyecto : tareaScm.getTareaScmProyectoList()) {
                TareaScm oldTareaScmOfTareaScmProyectoListTareaScmProyecto = tareaScmProyectoListTareaScmProyecto.getTareaScm();
                tareaScmProyectoListTareaScmProyecto.setTareaScm(tareaScm);
                tareaScmProyectoListTareaScmProyecto = em.merge(tareaScmProyectoListTareaScmProyecto);
                if (oldTareaScmOfTareaScmProyectoListTareaScmProyecto != null) {
                    oldTareaScmOfTareaScmProyectoListTareaScmProyecto.getTareaScmProyectoList().remove(tareaScmProyectoListTareaScmProyecto);
                    oldTareaScmOfTareaScmProyectoListTareaScmProyecto = em.merge(oldTareaScmOfTareaScmProyectoListTareaScmProyecto);
                }
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

    public void edit(TareaScm tareaScm) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TareaScm persistentTareaScm = em.find(TareaScm.class, tareaScm.getIdTareaScm());
            List<Entregable> entregableListOld = persistentTareaScm.getEntregableList();
            List<Entregable> entregableListNew = tareaScm.getEntregableList();
            List<TareaScmProyecto> tareaScmProyectoListOld = persistentTareaScm.getTareaScmProyectoList();
            List<TareaScmProyecto> tareaScmProyectoListNew = tareaScm.getTareaScmProyectoList();
            List<String> illegalOrphanMessages = null;
            for (Entregable entregableListOldEntregable : entregableListOld) {
                if (!entregableListNew.contains(entregableListOldEntregable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entregable " + entregableListOldEntregable + " since its tareaScm field is not nullable.");
                }
            }
            for (TareaScmProyecto tareaScmProyectoListOldTareaScmProyecto : tareaScmProyectoListOld) {
                if (!tareaScmProyectoListNew.contains(tareaScmProyectoListOldTareaScmProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TareaScmProyecto " + tareaScmProyectoListOldTareaScmProyecto + " since its tareaScm field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Entregable> attachedEntregableListNew = new ArrayList<Entregable>();
            for (Entregable entregableListNewEntregableToAttach : entregableListNew) {
                entregableListNewEntregableToAttach = em.getReference(entregableListNewEntregableToAttach.getClass(), entregableListNewEntregableToAttach.getIdEntregable());
                attachedEntregableListNew.add(entregableListNewEntregableToAttach);
            }
            entregableListNew = attachedEntregableListNew;
            tareaScm.setEntregableList(entregableListNew);
            List<TareaScmProyecto> attachedTareaScmProyectoListNew = new ArrayList<TareaScmProyecto>();
            for (TareaScmProyecto tareaScmProyectoListNewTareaScmProyectoToAttach : tareaScmProyectoListNew) {
                tareaScmProyectoListNewTareaScmProyectoToAttach = em.getReference(tareaScmProyectoListNewTareaScmProyectoToAttach.getClass(), tareaScmProyectoListNewTareaScmProyectoToAttach.getTareaScmProyectoPK());
                attachedTareaScmProyectoListNew.add(tareaScmProyectoListNewTareaScmProyectoToAttach);
            }
            tareaScmProyectoListNew = attachedTareaScmProyectoListNew;
            tareaScm.setTareaScmProyectoList(tareaScmProyectoListNew);
            tareaScm = em.merge(tareaScm);
            for (Entregable entregableListNewEntregable : entregableListNew) {
                if (!entregableListOld.contains(entregableListNewEntregable)) {
                    TareaScm oldTareaScmOfEntregableListNewEntregable = entregableListNewEntregable.getTareaScm();
                    entregableListNewEntregable.setTareaScm(tareaScm);
                    entregableListNewEntregable = em.merge(entregableListNewEntregable);
                    if (oldTareaScmOfEntregableListNewEntregable != null && !oldTareaScmOfEntregableListNewEntregable.equals(tareaScm)) {
                        oldTareaScmOfEntregableListNewEntregable.getEntregableList().remove(entregableListNewEntregable);
                        oldTareaScmOfEntregableListNewEntregable = em.merge(oldTareaScmOfEntregableListNewEntregable);
                    }
                }
            }
            for (TareaScmProyecto tareaScmProyectoListNewTareaScmProyecto : tareaScmProyectoListNew) {
                if (!tareaScmProyectoListOld.contains(tareaScmProyectoListNewTareaScmProyecto)) {
                    TareaScm oldTareaScmOfTareaScmProyectoListNewTareaScmProyecto = tareaScmProyectoListNewTareaScmProyecto.getTareaScm();
                    tareaScmProyectoListNewTareaScmProyecto.setTareaScm(tareaScm);
                    tareaScmProyectoListNewTareaScmProyecto = em.merge(tareaScmProyectoListNewTareaScmProyecto);
                    if (oldTareaScmOfTareaScmProyectoListNewTareaScmProyecto != null && !oldTareaScmOfTareaScmProyectoListNewTareaScmProyecto.equals(tareaScm)) {
                        oldTareaScmOfTareaScmProyectoListNewTareaScmProyecto.getTareaScmProyectoList().remove(tareaScmProyectoListNewTareaScmProyecto);
                        oldTareaScmOfTareaScmProyectoListNewTareaScmProyecto = em.merge(oldTareaScmOfTareaScmProyectoListNewTareaScmProyecto);
                    }
                }
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
                Integer id = tareaScm.getIdTareaScm();
                if (findTareaScm(id) == null) {
                    throw new NonexistentEntityException("The tareaScm with id " + id + " no longer exists.");
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
            TareaScm tareaScm;
            try {
                tareaScm = em.getReference(TareaScm.class, id);
                tareaScm.getIdTareaScm();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareaScm with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Entregable> entregableListOrphanCheck = tareaScm.getEntregableList();
            for (Entregable entregableListOrphanCheckEntregable : entregableListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TareaScm (" + tareaScm + ") cannot be destroyed since the Entregable " + entregableListOrphanCheckEntregable + " in its entregableList field has a non-nullable tareaScm field.");
            }
            List<TareaScmProyecto> tareaScmProyectoListOrphanCheck = tareaScm.getTareaScmProyectoList();
            for (TareaScmProyecto tareaScmProyectoListOrphanCheckTareaScmProyecto : tareaScmProyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TareaScm (" + tareaScm + ") cannot be destroyed since the TareaScmProyecto " + tareaScmProyectoListOrphanCheckTareaScmProyecto + " in its tareaScmProyectoList field has a non-nullable tareaScm field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tareaScm);
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

    public List<TareaScm> findTareaScmEntities() {
        return findTareaScmEntities(true, -1, -1);
    }

    public List<TareaScm> findTareaScmEntities(int maxResults, int firstResult) {
        return findTareaScmEntities(false, maxResults, firstResult);
    }

    private List<TareaScm> findTareaScmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TareaScm.class));
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

    public TareaScm findTareaScm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TareaScm.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareaScmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TareaScm> rt = cq.from(TareaScm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
