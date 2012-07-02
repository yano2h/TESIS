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
import java.util.ArrayList;
import java.util.List;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.proyecto.persistencia.entidades.TipoPrioridad;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class TipoPrioridadJpaController implements Serializable {

    public TipoPrioridadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPrioridad tipoPrioridad) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoPrioridad.getSolicitudRequerimientoList() == null) {
            tipoPrioridad.setSolicitudRequerimientoList(new ArrayList<SolicitudRequerimiento>());
        }
        if (tipoPrioridad.getSolicitudCambioList() == null) {
            tipoPrioridad.setSolicitudCambioList(new ArrayList<SolicitudCambio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<SolicitudRequerimiento> attachedSolicitudRequerimientoList = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimientoToAttach : tipoPrioridad.getSolicitudRequerimientoList()) {
                solicitudRequerimientoListSolicitudRequerimientoToAttach = em.getReference(solicitudRequerimientoListSolicitudRequerimientoToAttach.getClass(), solicitudRequerimientoListSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudRequerimientoList.add(solicitudRequerimientoListSolicitudRequerimientoToAttach);
            }
            tipoPrioridad.setSolicitudRequerimientoList(attachedSolicitudRequerimientoList);
            List<SolicitudCambio> attachedSolicitudCambioList = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudCambioListSolicitudCambioToAttach : tipoPrioridad.getSolicitudCambioList()) {
                solicitudCambioListSolicitudCambioToAttach = em.getReference(solicitudCambioListSolicitudCambioToAttach.getClass(), solicitudCambioListSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudCambioList.add(solicitudCambioListSolicitudCambioToAttach);
            }
            tipoPrioridad.setSolicitudCambioList(attachedSolicitudCambioList);
            em.persist(tipoPrioridad);
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimiento : tipoPrioridad.getSolicitudRequerimientoList()) {
                TipoPrioridad oldPrioridadSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento = solicitudRequerimientoListSolicitudRequerimiento.getPrioridadSolicitud();
                solicitudRequerimientoListSolicitudRequerimiento.setPrioridadSolicitud(tipoPrioridad);
                solicitudRequerimientoListSolicitudRequerimiento = em.merge(solicitudRequerimientoListSolicitudRequerimiento);
                if (oldPrioridadSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento != null) {
                    oldPrioridadSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListSolicitudRequerimiento);
                    oldPrioridadSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento = em.merge(oldPrioridadSolicitudOfSolicitudRequerimientoListSolicitudRequerimiento);
                }
            }
            for (SolicitudCambio solicitudCambioListSolicitudCambio : tipoPrioridad.getSolicitudCambioList()) {
                TipoPrioridad oldPrioridadSolicitudOfSolicitudCambioListSolicitudCambio = solicitudCambioListSolicitudCambio.getPrioridadSolicitud();
                solicitudCambioListSolicitudCambio.setPrioridadSolicitud(tipoPrioridad);
                solicitudCambioListSolicitudCambio = em.merge(solicitudCambioListSolicitudCambio);
                if (oldPrioridadSolicitudOfSolicitudCambioListSolicitudCambio != null) {
                    oldPrioridadSolicitudOfSolicitudCambioListSolicitudCambio.getSolicitudCambioList().remove(solicitudCambioListSolicitudCambio);
                    oldPrioridadSolicitudOfSolicitudCambioListSolicitudCambio = em.merge(oldPrioridadSolicitudOfSolicitudCambioListSolicitudCambio);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoPrioridad(tipoPrioridad.getIdTipoPrioridad()) != null) {
                throw new PreexistingEntityException("TipoPrioridad " + tipoPrioridad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPrioridad tipoPrioridad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoPrioridad persistentTipoPrioridad = em.find(TipoPrioridad.class, tipoPrioridad.getIdTipoPrioridad());
            List<SolicitudRequerimiento> solicitudRequerimientoListOld = persistentTipoPrioridad.getSolicitudRequerimientoList();
            List<SolicitudRequerimiento> solicitudRequerimientoListNew = tipoPrioridad.getSolicitudRequerimientoList();
            List<SolicitudCambio> solicitudCambioListOld = persistentTipoPrioridad.getSolicitudCambioList();
            List<SolicitudCambio> solicitudCambioListNew = tipoPrioridad.getSolicitudCambioList();
            List<String> illegalOrphanMessages = null;
            for (SolicitudRequerimiento solicitudRequerimientoListOldSolicitudRequerimiento : solicitudRequerimientoListOld) {
                if (!solicitudRequerimientoListNew.contains(solicitudRequerimientoListOldSolicitudRequerimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudRequerimiento " + solicitudRequerimientoListOldSolicitudRequerimiento + " since its prioridadSolicitud field is not nullable.");
                }
            }
            for (SolicitudCambio solicitudCambioListOldSolicitudCambio : solicitudCambioListOld) {
                if (!solicitudCambioListNew.contains(solicitudCambioListOldSolicitudCambio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudCambio " + solicitudCambioListOldSolicitudCambio + " since its prioridadSolicitud field is not nullable.");
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
            tipoPrioridad.setSolicitudRequerimientoList(solicitudRequerimientoListNew);
            List<SolicitudCambio> attachedSolicitudCambioListNew = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudCambioListNewSolicitudCambioToAttach : solicitudCambioListNew) {
                solicitudCambioListNewSolicitudCambioToAttach = em.getReference(solicitudCambioListNewSolicitudCambioToAttach.getClass(), solicitudCambioListNewSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudCambioListNew.add(solicitudCambioListNewSolicitudCambioToAttach);
            }
            solicitudCambioListNew = attachedSolicitudCambioListNew;
            tipoPrioridad.setSolicitudCambioList(solicitudCambioListNew);
            tipoPrioridad = em.merge(tipoPrioridad);
            for (SolicitudRequerimiento solicitudRequerimientoListNewSolicitudRequerimiento : solicitudRequerimientoListNew) {
                if (!solicitudRequerimientoListOld.contains(solicitudRequerimientoListNewSolicitudRequerimiento)) {
                    TipoPrioridad oldPrioridadSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento = solicitudRequerimientoListNewSolicitudRequerimiento.getPrioridadSolicitud();
                    solicitudRequerimientoListNewSolicitudRequerimiento.setPrioridadSolicitud(tipoPrioridad);
                    solicitudRequerimientoListNewSolicitudRequerimiento = em.merge(solicitudRequerimientoListNewSolicitudRequerimiento);
                    if (oldPrioridadSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento != null && !oldPrioridadSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento.equals(tipoPrioridad)) {
                        oldPrioridadSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListNewSolicitudRequerimiento);
                        oldPrioridadSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento = em.merge(oldPrioridadSolicitudOfSolicitudRequerimientoListNewSolicitudRequerimiento);
                    }
                }
            }
            for (SolicitudCambio solicitudCambioListNewSolicitudCambio : solicitudCambioListNew) {
                if (!solicitudCambioListOld.contains(solicitudCambioListNewSolicitudCambio)) {
                    TipoPrioridad oldPrioridadSolicitudOfSolicitudCambioListNewSolicitudCambio = solicitudCambioListNewSolicitudCambio.getPrioridadSolicitud();
                    solicitudCambioListNewSolicitudCambio.setPrioridadSolicitud(tipoPrioridad);
                    solicitudCambioListNewSolicitudCambio = em.merge(solicitudCambioListNewSolicitudCambio);
                    if (oldPrioridadSolicitudOfSolicitudCambioListNewSolicitudCambio != null && !oldPrioridadSolicitudOfSolicitudCambioListNewSolicitudCambio.equals(tipoPrioridad)) {
                        oldPrioridadSolicitudOfSolicitudCambioListNewSolicitudCambio.getSolicitudCambioList().remove(solicitudCambioListNewSolicitudCambio);
                        oldPrioridadSolicitudOfSolicitudCambioListNewSolicitudCambio = em.merge(oldPrioridadSolicitudOfSolicitudCambioListNewSolicitudCambio);
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
                Short id = tipoPrioridad.getIdTipoPrioridad();
                if (findTipoPrioridad(id) == null) {
                    throw new NonexistentEntityException("The tipoPrioridad with id " + id + " no longer exists.");
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
            TipoPrioridad tipoPrioridad;
            try {
                tipoPrioridad = em.getReference(TipoPrioridad.class, id);
                tipoPrioridad.getIdTipoPrioridad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPrioridad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SolicitudRequerimiento> solicitudRequerimientoListOrphanCheck = tipoPrioridad.getSolicitudRequerimientoList();
            for (SolicitudRequerimiento solicitudRequerimientoListOrphanCheckSolicitudRequerimiento : solicitudRequerimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPrioridad (" + tipoPrioridad + ") cannot be destroyed since the SolicitudRequerimiento " + solicitudRequerimientoListOrphanCheckSolicitudRequerimiento + " in its solicitudRequerimientoList field has a non-nullable prioridadSolicitud field.");
            }
            List<SolicitudCambio> solicitudCambioListOrphanCheck = tipoPrioridad.getSolicitudCambioList();
            for (SolicitudCambio solicitudCambioListOrphanCheckSolicitudCambio : solicitudCambioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPrioridad (" + tipoPrioridad + ") cannot be destroyed since the SolicitudCambio " + solicitudCambioListOrphanCheckSolicitudCambio + " in its solicitudCambioList field has a non-nullable prioridadSolicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPrioridad);
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

    public List<TipoPrioridad> findTipoPrioridadEntities() {
        return findTipoPrioridadEntities(true, -1, -1);
    }

    public List<TipoPrioridad> findTipoPrioridadEntities(int maxResults, int firstResult) {
        return findTipoPrioridadEntities(false, maxResults, firstResult);
    }

    private List<TipoPrioridad> findTipoPrioridadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPrioridad.class));
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

    public TipoPrioridad findTipoPrioridad(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPrioridad.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPrioridadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPrioridad> rt = cq.from(TipoPrioridad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
