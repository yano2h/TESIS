/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.EstadoProyecto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class EstadoProyectoJpaController implements Serializable {

    public EstadoProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoProyecto estadoProyecto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (estadoProyecto.getProyectoList() == null) {
            estadoProyecto.setProyectoList(new ArrayList<Proyecto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Proyecto> attachedProyectoList = new ArrayList<Proyecto>();
            for (Proyecto proyectoListProyectoToAttach : estadoProyecto.getProyectoList()) {
                proyectoListProyectoToAttach = em.getReference(proyectoListProyectoToAttach.getClass(), proyectoListProyectoToAttach.getIdProyecto());
                attachedProyectoList.add(proyectoListProyectoToAttach);
            }
            estadoProyecto.setProyectoList(attachedProyectoList);
            em.persist(estadoProyecto);
            for (Proyecto proyectoListProyecto : estadoProyecto.getProyectoList()) {
                EstadoProyecto oldEstadoProyectoOfProyectoListProyecto = proyectoListProyecto.getEstadoProyecto();
                proyectoListProyecto.setEstadoProyecto(estadoProyecto);
                proyectoListProyecto = em.merge(proyectoListProyecto);
                if (oldEstadoProyectoOfProyectoListProyecto != null) {
                    oldEstadoProyectoOfProyectoListProyecto.getProyectoList().remove(proyectoListProyecto);
                    oldEstadoProyectoOfProyectoListProyecto = em.merge(oldEstadoProyectoOfProyectoListProyecto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEstadoProyecto(estadoProyecto.getIdEstadoProyecto()) != null) {
                throw new PreexistingEntityException("EstadoProyecto " + estadoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoProyecto estadoProyecto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoProyecto persistentEstadoProyecto = em.find(EstadoProyecto.class, estadoProyecto.getIdEstadoProyecto());
            List<Proyecto> proyectoListOld = persistentEstadoProyecto.getProyectoList();
            List<Proyecto> proyectoListNew = estadoProyecto.getProyectoList();
            List<String> illegalOrphanMessages = null;
            for (Proyecto proyectoListOldProyecto : proyectoListOld) {
                if (!proyectoListNew.contains(proyectoListOldProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyecto " + proyectoListOldProyecto + " since its estadoProyecto field is not nullable.");
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
            estadoProyecto.setProyectoList(proyectoListNew);
            estadoProyecto = em.merge(estadoProyecto);
            for (Proyecto proyectoListNewProyecto : proyectoListNew) {
                if (!proyectoListOld.contains(proyectoListNewProyecto)) {
                    EstadoProyecto oldEstadoProyectoOfProyectoListNewProyecto = proyectoListNewProyecto.getEstadoProyecto();
                    proyectoListNewProyecto.setEstadoProyecto(estadoProyecto);
                    proyectoListNewProyecto = em.merge(proyectoListNewProyecto);
                    if (oldEstadoProyectoOfProyectoListNewProyecto != null && !oldEstadoProyectoOfProyectoListNewProyecto.equals(estadoProyecto)) {
                        oldEstadoProyectoOfProyectoListNewProyecto.getProyectoList().remove(proyectoListNewProyecto);
                        oldEstadoProyectoOfProyectoListNewProyecto = em.merge(oldEstadoProyectoOfProyectoListNewProyecto);
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
                Short id = estadoProyecto.getIdEstadoProyecto();
                if (findEstadoProyecto(id) == null) {
                    throw new NonexistentEntityException("The estadoProyecto with id " + id + " no longer exists.");
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
            EstadoProyecto estadoProyecto;
            try {
                estadoProyecto = em.getReference(EstadoProyecto.class, id);
                estadoProyecto.getIdEstadoProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoProyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Proyecto> proyectoListOrphanCheck = estadoProyecto.getProyectoList();
            for (Proyecto proyectoListOrphanCheckProyecto : proyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoProyecto (" + estadoProyecto + ") cannot be destroyed since the Proyecto " + proyectoListOrphanCheckProyecto + " in its proyectoList field has a non-nullable estadoProyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoProyecto);
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

    public List<EstadoProyecto> findEstadoProyectoEntities() {
        return findEstadoProyectoEntities(true, -1, -1);
    }

    public List<EstadoProyecto> findEstadoProyectoEntities(int maxResults, int firstResult) {
        return findEstadoProyectoEntities(false, maxResults, firstResult);
    }

    private List<EstadoProyecto> findEstadoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoProyecto.class));
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

    public EstadoProyecto findEstadoProyecto(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoProyecto> rt = cq.from(EstadoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
