/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.EstadoSolicitudCambio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class EstadoSolicitudCambioJpaController implements Serializable {

    public EstadoSolicitudCambioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoSolicitudCambio estadoSolicitudCambio) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (estadoSolicitudCambio.getSolicitudCambioList() == null) {
            estadoSolicitudCambio.setSolicitudCambioList(new ArrayList<SolicitudCambio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<SolicitudCambio> attachedSolicitudCambioList = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudCambioListSolicitudCambioToAttach : estadoSolicitudCambio.getSolicitudCambioList()) {
                solicitudCambioListSolicitudCambioToAttach = em.getReference(solicitudCambioListSolicitudCambioToAttach.getClass(), solicitudCambioListSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudCambioList.add(solicitudCambioListSolicitudCambioToAttach);
            }
            estadoSolicitudCambio.setSolicitudCambioList(attachedSolicitudCambioList);
            em.persist(estadoSolicitudCambio);
            for (SolicitudCambio solicitudCambioListSolicitudCambio : estadoSolicitudCambio.getSolicitudCambioList()) {
                EstadoSolicitudCambio oldEstadoSolicitudOfSolicitudCambioListSolicitudCambio = solicitudCambioListSolicitudCambio.getEstadoSolicitud();
                solicitudCambioListSolicitudCambio.setEstadoSolicitud(estadoSolicitudCambio);
                solicitudCambioListSolicitudCambio = em.merge(solicitudCambioListSolicitudCambio);
                if (oldEstadoSolicitudOfSolicitudCambioListSolicitudCambio != null) {
                    oldEstadoSolicitudOfSolicitudCambioListSolicitudCambio.getSolicitudCambioList().remove(solicitudCambioListSolicitudCambio);
                    oldEstadoSolicitudOfSolicitudCambioListSolicitudCambio = em.merge(oldEstadoSolicitudOfSolicitudCambioListSolicitudCambio);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEstadoSolicitudCambio(estadoSolicitudCambio.getIdEstadoSolicitudCambio()) != null) {
                throw new PreexistingEntityException("EstadoSolicitudCambio " + estadoSolicitudCambio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoSolicitudCambio estadoSolicitudCambio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoSolicitudCambio persistentEstadoSolicitudCambio = em.find(EstadoSolicitudCambio.class, estadoSolicitudCambio.getIdEstadoSolicitudCambio());
            List<SolicitudCambio> solicitudCambioListOld = persistentEstadoSolicitudCambio.getSolicitudCambioList();
            List<SolicitudCambio> solicitudCambioListNew = estadoSolicitudCambio.getSolicitudCambioList();
            List<String> illegalOrphanMessages = null;
            for (SolicitudCambio solicitudCambioListOldSolicitudCambio : solicitudCambioListOld) {
                if (!solicitudCambioListNew.contains(solicitudCambioListOldSolicitudCambio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudCambio " + solicitudCambioListOldSolicitudCambio + " since its estadoSolicitud field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SolicitudCambio> attachedSolicitudCambioListNew = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudCambioListNewSolicitudCambioToAttach : solicitudCambioListNew) {
                solicitudCambioListNewSolicitudCambioToAttach = em.getReference(solicitudCambioListNewSolicitudCambioToAttach.getClass(), solicitudCambioListNewSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudCambioListNew.add(solicitudCambioListNewSolicitudCambioToAttach);
            }
            solicitudCambioListNew = attachedSolicitudCambioListNew;
            estadoSolicitudCambio.setSolicitudCambioList(solicitudCambioListNew);
            estadoSolicitudCambio = em.merge(estadoSolicitudCambio);
            for (SolicitudCambio solicitudCambioListNewSolicitudCambio : solicitudCambioListNew) {
                if (!solicitudCambioListOld.contains(solicitudCambioListNewSolicitudCambio)) {
                    EstadoSolicitudCambio oldEstadoSolicitudOfSolicitudCambioListNewSolicitudCambio = solicitudCambioListNewSolicitudCambio.getEstadoSolicitud();
                    solicitudCambioListNewSolicitudCambio.setEstadoSolicitud(estadoSolicitudCambio);
                    solicitudCambioListNewSolicitudCambio = em.merge(solicitudCambioListNewSolicitudCambio);
                    if (oldEstadoSolicitudOfSolicitudCambioListNewSolicitudCambio != null && !oldEstadoSolicitudOfSolicitudCambioListNewSolicitudCambio.equals(estadoSolicitudCambio)) {
                        oldEstadoSolicitudOfSolicitudCambioListNewSolicitudCambio.getSolicitudCambioList().remove(solicitudCambioListNewSolicitudCambio);
                        oldEstadoSolicitudOfSolicitudCambioListNewSolicitudCambio = em.merge(oldEstadoSolicitudOfSolicitudCambioListNewSolicitudCambio);
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
                Short id = estadoSolicitudCambio.getIdEstadoSolicitudCambio();
                if (findEstadoSolicitudCambio(id) == null) {
                    throw new NonexistentEntityException("The estadoSolicitudCambio with id " + id + " no longer exists.");
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
            EstadoSolicitudCambio estadoSolicitudCambio;
            try {
                estadoSolicitudCambio = em.getReference(EstadoSolicitudCambio.class, id);
                estadoSolicitudCambio.getIdEstadoSolicitudCambio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoSolicitudCambio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SolicitudCambio> solicitudCambioListOrphanCheck = estadoSolicitudCambio.getSolicitudCambioList();
            for (SolicitudCambio solicitudCambioListOrphanCheckSolicitudCambio : solicitudCambioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoSolicitudCambio (" + estadoSolicitudCambio + ") cannot be destroyed since the SolicitudCambio " + solicitudCambioListOrphanCheckSolicitudCambio + " in its solicitudCambioList field has a non-nullable estadoSolicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoSolicitudCambio);
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

    public List<EstadoSolicitudCambio> findEstadoSolicitudCambioEntities() {
        return findEstadoSolicitudCambioEntities(true, -1, -1);
    }

    public List<EstadoSolicitudCambio> findEstadoSolicitudCambioEntities(int maxResults, int firstResult) {
        return findEstadoSolicitudCambioEntities(false, maxResults, firstResult);
    }

    private List<EstadoSolicitudCambio> findEstadoSolicitudCambioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoSolicitudCambio.class));
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

    public EstadoSolicitudCambio findEstadoSolicitudCambio(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoSolicitudCambio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoSolicitudCambioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoSolicitudCambio> rt = cq.from(EstadoSolicitudCambio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
