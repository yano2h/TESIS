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
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.TipoSolicitudRequerimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class TipoSolicitudRequerimientoJpaController implements Serializable {

    public TipoSolicitudRequerimientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoSolicitudRequerimiento tipoSolicitudRequerimiento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoSolicitudRequerimiento.getSolicitudRequerimientoList() == null) {
            tipoSolicitudRequerimiento.setSolicitudRequerimientoList(new ArrayList<SolicitudRequerimiento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<SolicitudRequerimiento> attachedSolicitudRequerimientoList = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimientoToAttach : tipoSolicitudRequerimiento.getSolicitudRequerimientoList()) {
                solicitudRequerimientoListSolicitudRequerimientoToAttach = em.getReference(solicitudRequerimientoListSolicitudRequerimientoToAttach.getClass(), solicitudRequerimientoListSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudRequerimientoList.add(solicitudRequerimientoListSolicitudRequerimientoToAttach);
            }
            tipoSolicitudRequerimiento.setSolicitudRequerimientoList(attachedSolicitudRequerimientoList);
            em.persist(tipoSolicitudRequerimiento);
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimiento : tipoSolicitudRequerimiento.getSolicitudRequerimientoList()) {
                TipoSolicitudRequerimiento oldTipoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento = solicitudRequerimientoListSolicitudRequerimiento.getTipoSolicitud();
                solicitudRequerimientoListSolicitudRequerimiento.setTipoSolicitud(tipoSolicitudRequerimiento);
                solicitudRequerimientoListSolicitudRequerimiento = em.merge(solicitudRequerimientoListSolicitudRequerimiento);
                if (oldTipoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento != null) {
                    oldTipoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListSolicitudRequerimiento);
                    oldTipoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento = em.merge(oldTipoSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoSolicitudRequerimiento(tipoSolicitudRequerimiento.getIdTipoSolicitudRequerimiento()) != null) {
                throw new PreexistingEntityException("TipoSolicitudRequerimiento " + tipoSolicitudRequerimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoSolicitudRequerimiento tipoSolicitudRequerimiento) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoSolicitudRequerimiento persistentTipoSolicitudRequerimiento = em.find(TipoSolicitudRequerimiento.class, tipoSolicitudRequerimiento.getIdTipoSolicitudRequerimiento());
            List<SolicitudRequerimiento> solicitudRequerimientoListOld = persistentTipoSolicitudRequerimiento.getSolicitudRequerimientoList();
            List<SolicitudRequerimiento> solicitudRequerimientoListNew = tipoSolicitudRequerimiento.getSolicitudRequerimientoList();
            List<String> illegalOrphanMessages = null;
            for (SolicitudRequerimiento solicitudRequerimientoListOldSolicitudRequerimiento : solicitudRequerimientoListOld) {
                if (!solicitudRequerimientoListNew.contains(solicitudRequerimientoListOldSolicitudRequerimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudRequerimiento " + solicitudRequerimientoListOldSolicitudRequerimiento + " since its tipoSolicitud field is not nullable.");
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
            tipoSolicitudRequerimiento.setSolicitudRequerimientoList(solicitudRequerimientoListNew);
            tipoSolicitudRequerimiento = em.merge(tipoSolicitudRequerimiento);
            for (SolicitudRequerimiento solicitudRequerimientoListNewSolicitudRequerimiento : solicitudRequerimientoListNew) {
                if (!solicitudRequerimientoListOld.contains(solicitudRequerimientoListNewSolicitudRequerimiento)) {
                    TipoSolicitudRequerimiento oldTipoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento = solicitudRequerimientoListNewSolicitudRequerimiento.getTipoSolicitud();
                    solicitudRequerimientoListNewSolicitudRequerimiento.setTipoSolicitud(tipoSolicitudRequerimiento);
                    solicitudRequerimientoListNewSolicitudRequerimiento = em.merge(solicitudRequerimientoListNewSolicitudRequerimiento);
                    if (oldTipoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento != null && !oldTipoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento.equals(tipoSolicitudRequerimiento)) {
                        oldTipoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListNewSolicitudRequerimiento);
                        oldTipoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento = em.merge(oldTipoSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento);
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
                Short id = tipoSolicitudRequerimiento.getIdTipoSolicitudRequerimiento();
                if (findTipoSolicitudRequerimiento(id) == null) {
                    throw new NonexistentEntityException("The tipoSolicitudRequerimiento with id " + id + " no longer exists.");
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
            TipoSolicitudRequerimiento tipoSolicitudRequerimiento;
            try {
                tipoSolicitudRequerimiento = em.getReference(TipoSolicitudRequerimiento.class, id);
                tipoSolicitudRequerimiento.getIdTipoSolicitudRequerimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoSolicitudRequerimiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SolicitudRequerimiento> solicitudRequerimientoListOrphanCheck = tipoSolicitudRequerimiento.getSolicitudRequerimientoList();
            for (SolicitudRequerimiento solicitudRequerimientoListOrphanCheckSolicitudRequerimiento : solicitudRequerimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoSolicitudRequerimiento (" + tipoSolicitudRequerimiento + ") cannot be destroyed since the SolicitudRequerimiento " + solicitudRequerimientoListOrphanCheckSolicitudRequerimiento + " in its solicitudRequerimientoList field has a non-nullable tipoSolicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoSolicitudRequerimiento);
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

    public List<TipoSolicitudRequerimiento> findTipoSolicitudRequerimientoEntities() {
        return findTipoSolicitudRequerimientoEntities(true, -1, -1);
    }

    public List<TipoSolicitudRequerimiento> findTipoSolicitudRequerimientoEntities(int maxResults, int firstResult) {
        return findTipoSolicitudRequerimientoEntities(false, maxResults, firstResult);
    }

    private List<TipoSolicitudRequerimiento> findTipoSolicitudRequerimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoSolicitudRequerimiento.class));
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

    public TipoSolicitudRequerimiento findTipoSolicitudRequerimiento(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoSolicitudRequerimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoSolicitudRequerimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoSolicitudRequerimiento> rt = cq.from(TipoSolicitudRequerimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
