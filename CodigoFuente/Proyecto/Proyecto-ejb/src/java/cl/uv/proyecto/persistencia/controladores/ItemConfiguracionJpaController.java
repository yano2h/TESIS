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
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
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
public class ItemConfiguracionJpaController implements Serializable {

    public ItemConfiguracionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemConfiguracion itemConfiguracion) throws RollbackFailureException, Exception {
        if (itemConfiguracion.getSolicitudCambioList() == null) {
            itemConfiguracion.setSolicitudCambioList(new ArrayList<SolicitudCambio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FuncionarioDisico responsableItem = itemConfiguracion.getResponsableItem();
            if (responsableItem != null) {
                responsableItem = em.getReference(responsableItem.getClass(), responsableItem.getRut());
                itemConfiguracion.setResponsableItem(responsableItem);
            }
            Proyecto proyecto = itemConfiguracion.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getIdProyecto());
                itemConfiguracion.setProyecto(proyecto);
            }
            List<SolicitudCambio> attachedSolicitudCambioList = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudCambioListSolicitudCambioToAttach : itemConfiguracion.getSolicitudCambioList()) {
                solicitudCambioListSolicitudCambioToAttach = em.getReference(solicitudCambioListSolicitudCambioToAttach.getClass(), solicitudCambioListSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudCambioList.add(solicitudCambioListSolicitudCambioToAttach);
            }
            itemConfiguracion.setSolicitudCambioList(attachedSolicitudCambioList);
            em.persist(itemConfiguracion);
            if (responsableItem != null) {
                responsableItem.getItemsConfiguracionAcargo().add(itemConfiguracion);
                responsableItem = em.merge(responsableItem);
            }
            if (proyecto != null) {
                proyecto.getItemsDeConfiguracion().add(itemConfiguracion);
                proyecto = em.merge(proyecto);
            }
            for (SolicitudCambio solicitudCambioListSolicitudCambio : itemConfiguracion.getSolicitudCambioList()) {
                ItemConfiguracion oldItemConfiguracionOfSolicitudCambioListSolicitudCambio = solicitudCambioListSolicitudCambio.getItemConfiguracion();
                solicitudCambioListSolicitudCambio.setItemConfiguracion(itemConfiguracion);
                solicitudCambioListSolicitudCambio = em.merge(solicitudCambioListSolicitudCambio);
                if (oldItemConfiguracionOfSolicitudCambioListSolicitudCambio != null) {
                    oldItemConfiguracionOfSolicitudCambioListSolicitudCambio.getSolicitudCambioList().remove(solicitudCambioListSolicitudCambio);
                    oldItemConfiguracionOfSolicitudCambioListSolicitudCambio = em.merge(oldItemConfiguracionOfSolicitudCambioListSolicitudCambio);
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

    public void edit(ItemConfiguracion itemConfiguracion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemConfiguracion persistentItemConfiguracion = em.find(ItemConfiguracion.class, itemConfiguracion.getIdItemConfiguracion());
            FuncionarioDisico responsableItemOld = persistentItemConfiguracion.getResponsableItem();
            FuncionarioDisico responsableItemNew = itemConfiguracion.getResponsableItem();
            Proyecto proyectoOld = persistentItemConfiguracion.getProyecto();
            Proyecto proyectoNew = itemConfiguracion.getProyecto();
            List<SolicitudCambio> solicitudCambioListOld = persistentItemConfiguracion.getSolicitudCambioList();
            List<SolicitudCambio> solicitudCambioListNew = itemConfiguracion.getSolicitudCambioList();
            List<String> illegalOrphanMessages = null;
            for (SolicitudCambio solicitudCambioListOldSolicitudCambio : solicitudCambioListOld) {
                if (!solicitudCambioListNew.contains(solicitudCambioListOldSolicitudCambio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudCambio " + solicitudCambioListOldSolicitudCambio + " since its itemConfiguracion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (responsableItemNew != null) {
                responsableItemNew = em.getReference(responsableItemNew.getClass(), responsableItemNew.getRut());
                itemConfiguracion.setResponsableItem(responsableItemNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getIdProyecto());
                itemConfiguracion.setProyecto(proyectoNew);
            }
            List<SolicitudCambio> attachedSolicitudCambioListNew = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudCambioListNewSolicitudCambioToAttach : solicitudCambioListNew) {
                solicitudCambioListNewSolicitudCambioToAttach = em.getReference(solicitudCambioListNewSolicitudCambioToAttach.getClass(), solicitudCambioListNewSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudCambioListNew.add(solicitudCambioListNewSolicitudCambioToAttach);
            }
            solicitudCambioListNew = attachedSolicitudCambioListNew;
            itemConfiguracion.setSolicitudCambioList(solicitudCambioListNew);
            itemConfiguracion = em.merge(itemConfiguracion);
            if (responsableItemOld != null && !responsableItemOld.equals(responsableItemNew)) {
                responsableItemOld.getItemsConfiguracionAcargo().remove(itemConfiguracion);
                responsableItemOld = em.merge(responsableItemOld);
            }
            if (responsableItemNew != null && !responsableItemNew.equals(responsableItemOld)) {
                responsableItemNew.getItemsConfiguracionAcargo().add(itemConfiguracion);
                responsableItemNew = em.merge(responsableItemNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getItemsDeConfiguracion().remove(itemConfiguracion);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getItemsDeConfiguracion().add(itemConfiguracion);
                proyectoNew = em.merge(proyectoNew);
            }
            for (SolicitudCambio solicitudCambioListNewSolicitudCambio : solicitudCambioListNew) {
                if (!solicitudCambioListOld.contains(solicitudCambioListNewSolicitudCambio)) {
                    ItemConfiguracion oldItemConfiguracionOfSolicitudCambioListNewSolicitudCambio = solicitudCambioListNewSolicitudCambio.getItemConfiguracion();
                    solicitudCambioListNewSolicitudCambio.setItemConfiguracion(itemConfiguracion);
                    solicitudCambioListNewSolicitudCambio = em.merge(solicitudCambioListNewSolicitudCambio);
                    if (oldItemConfiguracionOfSolicitudCambioListNewSolicitudCambio != null && !oldItemConfiguracionOfSolicitudCambioListNewSolicitudCambio.equals(itemConfiguracion)) {
                        oldItemConfiguracionOfSolicitudCambioListNewSolicitudCambio.getSolicitudCambioList().remove(solicitudCambioListNewSolicitudCambio);
                        oldItemConfiguracionOfSolicitudCambioListNewSolicitudCambio = em.merge(oldItemConfiguracionOfSolicitudCambioListNewSolicitudCambio);
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
                Integer id = itemConfiguracion.getIdItemConfiguracion();
                if (findItemConfiguracion(id) == null) {
                    throw new NonexistentEntityException("The itemConfiguracion with id " + id + " no longer exists.");
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
            ItemConfiguracion itemConfiguracion;
            try {
                itemConfiguracion = em.getReference(ItemConfiguracion.class, id);
                itemConfiguracion.getIdItemConfiguracion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemConfiguracion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SolicitudCambio> solicitudCambioListOrphanCheck = itemConfiguracion.getSolicitudCambioList();
            for (SolicitudCambio solicitudCambioListOrphanCheckSolicitudCambio : solicitudCambioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItemConfiguracion (" + itemConfiguracion + ") cannot be destroyed since the SolicitudCambio " + solicitudCambioListOrphanCheckSolicitudCambio + " in its solicitudCambioList field has a non-nullable itemConfiguracion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            FuncionarioDisico responsableItem = itemConfiguracion.getResponsableItem();
            if (responsableItem != null) {
                responsableItem.getItemsConfiguracionAcargo().remove(itemConfiguracion);
                responsableItem = em.merge(responsableItem);
            }
            Proyecto proyecto = itemConfiguracion.getProyecto();
            if (proyecto != null) {
                proyecto.getItemsDeConfiguracion().remove(itemConfiguracion);
                proyecto = em.merge(proyecto);
            }
            em.remove(itemConfiguracion);
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

    public List<ItemConfiguracion> findItemConfiguracionEntities() {
        return findItemConfiguracionEntities(true, -1, -1);
    }

    public List<ItemConfiguracion> findItemConfiguracionEntities(int maxResults, int firstResult) {
        return findItemConfiguracionEntities(false, maxResults, firstResult);
    }

    private List<ItemConfiguracion> findItemConfiguracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemConfiguracion.class));
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

    public ItemConfiguracion findItemConfiguracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemConfiguracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemConfiguracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemConfiguracion> rt = cq.from(ItemConfiguracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
