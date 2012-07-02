/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TipoProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class TipoProyectoJpaController implements Serializable {

    public TipoProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProyecto tipoProyecto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoProyecto.getProyectoList() == null) {
            tipoProyecto.setProyectoList(new ArrayList<Proyecto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Proyecto> attachedProyectoList = new ArrayList<Proyecto>();
            for (Proyecto proyectoListProyectoToAttach : tipoProyecto.getProyectoList()) {
                proyectoListProyectoToAttach = em.getReference(proyectoListProyectoToAttach.getClass(), proyectoListProyectoToAttach.getIdProyecto());
                attachedProyectoList.add(proyectoListProyectoToAttach);
            }
            tipoProyecto.setProyectoList(attachedProyectoList);
            em.persist(tipoProyecto);
            for (Proyecto proyectoListProyecto : tipoProyecto.getProyectoList()) {
                TipoProyecto oldTipoProyectoOfProyectoListProyecto = proyectoListProyecto.getTipoProyecto();
                proyectoListProyecto.setTipoProyecto(tipoProyecto);
                proyectoListProyecto = em.merge(proyectoListProyecto);
                if (oldTipoProyectoOfProyectoListProyecto != null) {
                    oldTipoProyectoOfProyectoListProyecto.getProyectoList().remove(proyectoListProyecto);
                    oldTipoProyectoOfProyectoListProyecto = em.merge(oldTipoProyectoOfProyectoListProyecto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoProyecto(tipoProyecto.getIdTipoProyecto()) != null) {
                throw new PreexistingEntityException("TipoProyecto " + tipoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoProyecto tipoProyecto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoProyecto persistentTipoProyecto = em.find(TipoProyecto.class, tipoProyecto.getIdTipoProyecto());
            List<Proyecto> proyectoListOld = persistentTipoProyecto.getProyectoList();
            List<Proyecto> proyectoListNew = tipoProyecto.getProyectoList();
            List<String> illegalOrphanMessages = null;
            for (Proyecto proyectoListOldProyecto : proyectoListOld) {
                if (!proyectoListNew.contains(proyectoListOldProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyecto " + proyectoListOldProyecto + " since its tipoProyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Proyecto> attachedProyectoListNew = new ArrayList<Proyecto>();
            for (Proyecto proyectoListNewProyectoToAttach : proyectoListNew) {
                proyectoListNewProyectoToAttach = em.getReference(proyectoListNewProyectoToAttach.getClass(), proyectoListNewProyectoToAttach.getIdProyecto());
                attachedProyectoListNew.add(proyectoListNewProyectoToAttach);
            }
            proyectoListNew = attachedProyectoListNew;
            tipoProyecto.setProyectoList(proyectoListNew);
            tipoProyecto = em.merge(tipoProyecto);
            for (Proyecto proyectoListNewProyecto : proyectoListNew) {
                if (!proyectoListOld.contains(proyectoListNewProyecto)) {
                    TipoProyecto oldTipoProyectoOfProyectoListNewProyecto = proyectoListNewProyecto.getTipoProyecto();
                    proyectoListNewProyecto.setTipoProyecto(tipoProyecto);
                    proyectoListNewProyecto = em.merge(proyectoListNewProyecto);
                    if (oldTipoProyectoOfProyectoListNewProyecto != null && !oldTipoProyectoOfProyectoListNewProyecto.equals(tipoProyecto)) {
                        oldTipoProyectoOfProyectoListNewProyecto.getProyectoList().remove(proyectoListNewProyecto);
                        oldTipoProyectoOfProyectoListNewProyecto = em.merge(oldTipoProyectoOfProyectoListNewProyecto);
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
                Short id = tipoProyecto.getIdTipoProyecto();
                if (findTipoProyecto(id) == null) {
                    throw new NonexistentEntityException("The tipoProyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoProyecto tipoProyecto;
            try {
                tipoProyecto = em.getReference(TipoProyecto.class, id);
                tipoProyecto.getIdTipoProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Proyecto> proyectoListOrphanCheck = tipoProyecto.getProyectoList();
            for (Proyecto proyectoListOrphanCheckProyecto : proyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoProyecto (" + tipoProyecto + ") cannot be destroyed since the Proyecto " + proyectoListOrphanCheckProyecto + " in its proyectoList field has a non-nullable tipoProyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoProyecto);
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

    public List<TipoProyecto> findTipoProyectoEntities() {
        return findTipoProyectoEntities(true, -1, -1);
    }

    public List<TipoProyecto> findTipoProyectoEntities(int maxResults, int firstResult) {
        return findTipoProyectoEntities(false, maxResults, firstResult);
    }

    private List<TipoProyecto> findTipoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProyecto.class));
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

    public TipoProyecto findTipoProyecto(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProyecto> rt = cq.from(TipoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
