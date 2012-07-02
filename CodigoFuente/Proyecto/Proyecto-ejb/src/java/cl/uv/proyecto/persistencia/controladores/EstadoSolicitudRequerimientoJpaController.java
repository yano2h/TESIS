/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.EstadoSolicitudRequerimiento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class EstadoSolicitudRequerimientoJpaController implements Serializable {

    public EstadoSolicitudRequerimientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoSolicitudRequerimiento estadoSolicitudRequerimiento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (estadoSolicitudRequerimiento.getSolicitudRequerimientoList() == null) {
            estadoSolicitudRequerimiento.setSolicitudRequerimientoList(new ArrayList<SolicitudRequerimiento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<SolicitudRequerimiento> attachedSolicitudRequerimientoList = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimientoToAttach : estadoSolicitudRequerimiento.getSolicitudRequerimientoList()) {
                solicitudRequerimientoListSolicitudRequerimientoToAttach = em.getReference(solicitudRequerimientoListSolicitudRequerimientoToAttach.getClass(), solicitudRequerimientoListSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudRequerimientoList.add(solicitudRequerimientoListSolicitudRequerimientoToAttach);
            }
            estadoSolicitudRequerimiento.setSolicitudRequerimientoList(attachedSolicitudRequerimientoList);
            em.persist(estadoSolicitudRequerimiento);
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimiento : estadoSolicitudRequerimiento.getSolicitudRequerimientoList()) {
                EstadoSolicitudRequerimiento oldEstadoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento = solicitudRequerimientoListSolicitudRequerimiento.getEstadoSolicitud();
                solicitudRequerimientoListSolicitudRequerimiento.setEstadoSolicitud(estadoSolicitudRequerimiento);
                solicitudRequerimientoListSolicitudRequerimiento = em.merge(solicitudRequerimientoListSolicitudRequerimiento);
                if (oldEstadoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento != null) {
                    oldEstadoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListSolicitudRequerimiento);
                    oldEstadoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento = em.merge(oldEstadoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEstadoSolicitudRequerimiento(estadoSolicitudRequerimiento.getIdEstadoSolicitudRequerimiento()) != null) {
                throw new PreexistingEntityException("EstadoSolicitudRequerimiento " + estadoSolicitudRequerimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoSolicitudRequerimiento estadoSolicitudRequerimiento) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoSolicitudRequerimiento persistentEstadoSolicitudRequerimiento = em.find(EstadoSolicitudRequerimiento.class, estadoSolicitudRequerimiento.getIdEstadoSolicitudRequerimiento());
            List<SolicitudRequerimiento> solicitudRequerimientoListOld = persistentEstadoSolicitudRequerimiento.getSolicitudRequerimientoList();
            List<SolicitudRequerimiento> solicitudRequerimientoListNew = estadoSolicitudRequerimiento.getSolicitudRequerimientoList();
            List<String> illegalOrphanMessages = null;
            for (SolicitudRequerimiento solicitudRequerimientoListOldSolicitudRequerimiento : solicitudRequerimientoListOld) {
                if (!solicitudRequerimientoListNew.contains(solicitudRequerimientoListOldSolicitudRequerimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudRequerimiento " + solicitudRequerimientoListOldSolicitudRequerimiento + " since its estadoSolicitud field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SolicitudRequerimiento> attachedSolicitudRequerimientoListNew = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudRequerimientoListNewSolicitudRequerimientoToAttach : solicitudRequerimientoListNew) {
                solicitudRequerimientoListNewSolicitudRequerimientoToAttach = em.getReference(solicitudRequerimientoListNewSolicitudRequerimientoToAttach.getClass(), solicitudRequerimientoListNewSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudRequerimientoListNew.add(solicitudRequerimientoListNewSolicitudRequerimientoToAttach);
            }
            solicitudRequerimientoListNew = attachedSolicitudRequerimientoListNew;
            estadoSolicitudRequerimiento.setSolicitudRequerimientoList(solicitudRequerimientoListNew);
            estadoSolicitudRequerimiento = em.merge(estadoSolicitudRequerimiento);
            for (SolicitudRequerimiento solicitudRequerimientoListNewSolicitudRequerimiento : solicitudRequerimientoListNew) {
                if (!solicitudRequerimientoListOld.contains(solicitudRequerimientoListNewSolicitudRequerimiento)) {
                    EstadoSolicitudRequerimiento oldEstadoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento = solicitudRequerimientoListNewSolicitudRequerimiento.getEstadoSolicitud();
                    solicitudRequerimientoListNewSolicitudRequerimiento.setEstadoSolicitud(estadoSolicitudRequerimiento);
                    solicitudRequerimientoListNewSolicitudRequerimiento = em.merge(solicitudRequerimientoListNewSolicitudRequerimiento);
                    if (oldEstadoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento != null && !oldEstadoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento.equals(estadoSolicitudRequerimiento)) {
                        oldEstadoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListNewSolicitudRequerimiento);
                        oldEstadoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento = em.merge(oldEstadoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento);
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
                Short id = estadoSolicitudRequerimiento.getIdEstadoSolicitudRequerimiento();
                if (findEstadoSolicitudRequerimiento(id) == null) {
                    throw new NonexistentEntityException("The estadoSolicitudRequerimiento with id " + id + " no longer exists.");
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
            EstadoSolicitudRequerimiento estadoSolicitudRequerimiento;
            try {
                estadoSolicitudRequerimiento = em.getReference(EstadoSolicitudRequerimiento.class, id);
                estadoSolicitudRequerimiento.getIdEstadoSolicitudRequerimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoSolicitudRequerimiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SolicitudRequerimiento> solicitudRequerimientoListOrphanCheck = estadoSolicitudRequerimiento.getSolicitudRequerimientoList();
            for (SolicitudRequerimiento solicitudRequerimientoListOrphanCheckSolicitudRequerimiento : solicitudRequerimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoSolicitudRequerimiento (" + estadoSolicitudRequerimiento + ") cannot be destroyed since the SolicitudRequerimiento " + solicitudRequerimientoListOrphanCheckSolicitudRequerimiento + " in its solicitudRequerimientoList field has a non-nullable estadoSolicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoSolicitudRequerimiento);
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

    public List<EstadoSolicitudRequerimiento> findEstadoSolicitudRequerimientoEntities() {
        return findEstadoSolicitudRequerimientoEntities(true, -1, -1);
    }

    public List<EstadoSolicitudRequerimiento> findEstadoSolicitudRequerimientoEntities(int maxResults, int firstResult) {
        return findEstadoSolicitudRequerimientoEntities(false, maxResults, firstResult);
    }

    private List<EstadoSolicitudRequerimiento> findEstadoSolicitudRequerimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoSolicitudRequerimiento.class));
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

    public EstadoSolicitudRequerimiento findEstadoSolicitudRequerimiento(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoSolicitudRequerimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoSolicitudRequerimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoSolicitudRequerimiento> rt = cq.from(EstadoSolicitudRequerimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
