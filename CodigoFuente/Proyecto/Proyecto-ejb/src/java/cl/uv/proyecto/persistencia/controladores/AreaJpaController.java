/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.Area;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.ArrayList;
import java.util.List;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class AreaJpaController implements Serializable {

    public AreaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Area area) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (area.getSolicitudRequerimientoList() == null) {
            area.setSolicitudRequerimientoList(new ArrayList<SolicitudRequerimiento>());
        }
        if (area.getFuncionarioDisicoList() == null) {
            area.setFuncionarioDisicoList(new ArrayList<FuncionarioDisico>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<SolicitudRequerimiento> attachedSolicitudRequerimientoList = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimientoToAttach : area.getSolicitudRequerimientoList()) {
                solicitudRequerimientoListSolicitudRequerimientoToAttach = em.getReference(solicitudRequerimientoListSolicitudRequerimientoToAttach.getClass(), solicitudRequerimientoListSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudRequerimientoList.add(solicitudRequerimientoListSolicitudRequerimientoToAttach);
            }
            area.setSolicitudRequerimientoList(attachedSolicitudRequerimientoList);
            List<FuncionarioDisico> attachedFuncionarioDisicoList = new ArrayList<FuncionarioDisico>();
            for (FuncionarioDisico funcionarioDisicoListFuncionarioDisicoToAttach : area.getFuncionarioDisicoList()) {
                funcionarioDisicoListFuncionarioDisicoToAttach = em.getReference(funcionarioDisicoListFuncionarioDisicoToAttach.getClass(), funcionarioDisicoListFuncionarioDisicoToAttach.getRut());
                attachedFuncionarioDisicoList.add(funcionarioDisicoListFuncionarioDisicoToAttach);
            }
            area.setFuncionarioDisicoList(attachedFuncionarioDisicoList);
            em.persist(area);
            for (SolicitudRequerimiento solicitudRequerimientoListSolicitudRequerimiento : area.getSolicitudRequerimientoList()) {
                Area oldAreaResponsableOfSolicitudRequerimientoListSolicitudRequerimiento = solicitudRequerimientoListSolicitudRequerimiento.getAreaResponsable();
                solicitudRequerimientoListSolicitudRequerimiento.setAreaResponsable(area);
                solicitudRequerimientoListSolicitudRequerimiento = em.merge(solicitudRequerimientoListSolicitudRequerimiento);
                if (oldAreaResponsableOfSolicitudRequerimientoListSolicitudRequerimiento != null) {
                    oldAreaResponsableOfSolicitudRequerimientoListSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListSolicitudRequerimiento);
                    oldAreaResponsableOfSolicitudRequerimientoListSolicitudRequerimiento = em.merge(oldAreaResponsableOfSolicitudRequerimientoListSolicitudRequerimiento);
                }
            }
            for (FuncionarioDisico funcionarioDisicoListFuncionarioDisico : area.getFuncionarioDisicoList()) {
                Area oldAreaOfFuncionarioDisicoListFuncionarioDisico = funcionarioDisicoListFuncionarioDisico.getArea();
                funcionarioDisicoListFuncionarioDisico.setArea(area);
                funcionarioDisicoListFuncionarioDisico = em.merge(funcionarioDisicoListFuncionarioDisico);
                if (oldAreaOfFuncionarioDisicoListFuncionarioDisico != null) {
                    oldAreaOfFuncionarioDisicoListFuncionarioDisico.getFuncionarioDisicoList().remove(funcionarioDisicoListFuncionarioDisico);
                    oldAreaOfFuncionarioDisicoListFuncionarioDisico = em.merge(oldAreaOfFuncionarioDisicoListFuncionarioDisico);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findArea(area.getIdArea()) != null) {
                throw new PreexistingEntityException("Area " + area + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Area area) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Area persistentArea = em.find(Area.class, area.getIdArea());
            List<SolicitudRequerimiento> solicitudRequerimientoListOld = persistentArea.getSolicitudRequerimientoList();
            List<SolicitudRequerimiento> solicitudRequerimientoListNew = area.getSolicitudRequerimientoList();
            List<FuncionarioDisico> funcionarioDisicoListOld = persistentArea.getFuncionarioDisicoList();
            List<FuncionarioDisico> funcionarioDisicoListNew = area.getFuncionarioDisicoList();
            List<String> illegalOrphanMessages = null;
            for (SolicitudRequerimiento solicitudRequerimientoListOldSolicitudRequerimiento : solicitudRequerimientoListOld) {
                if (!solicitudRequerimientoListNew.contains(solicitudRequerimientoListOldSolicitudRequerimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudRequerimiento " + solicitudRequerimientoListOldSolicitudRequerimiento + " since its areaResponsable field is not nullable.");
                }
            }
            for (FuncionarioDisico funcionarioDisicoListOldFuncionarioDisico : funcionarioDisicoListOld) {
                if (!funcionarioDisicoListNew.contains(funcionarioDisicoListOldFuncionarioDisico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FuncionarioDisico " + funcionarioDisicoListOldFuncionarioDisico + " since its area field is not nullable.");
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
            area.setSolicitudRequerimientoList(solicitudRequerimientoListNew);
            List<FuncionarioDisico> attachedFuncionarioDisicoListNew = new ArrayList<FuncionarioDisico>();
            for (FuncionarioDisico funcionarioDisicoListNewFuncionarioDisicoToAttach : funcionarioDisicoListNew) {
                funcionarioDisicoListNewFuncionarioDisicoToAttach = em.getReference(funcionarioDisicoListNewFuncionarioDisicoToAttach.getClass(), funcionarioDisicoListNewFuncionarioDisicoToAttach.getRut());
                attachedFuncionarioDisicoListNew.add(funcionarioDisicoListNewFuncionarioDisicoToAttach);
            }
            funcionarioDisicoListNew = attachedFuncionarioDisicoListNew;
            area.setFuncionarioDisicoList(funcionarioDisicoListNew);
            area = em.merge(area);
            for (SolicitudRequerimiento solicitudRequerimientoListNewSolicitudRequerimiento : solicitudRequerimientoListNew) {
                if (!solicitudRequerimientoListOld.contains(solicitudRequerimientoListNewSolicitudRequerimiento)) {
                    Area oldAreaResponsableOfSolicitudRequerimientoListNewSolicitudRequerimiento = solicitudRequerimientoListNewSolicitudRequerimiento.getAreaResponsable();
                    solicitudRequerimientoListNewSolicitudRequerimiento.setAreaResponsable(area);
                    solicitudRequerimientoListNewSolicitudRequerimiento = em.merge(solicitudRequerimientoListNewSolicitudRequerimiento);
                    if (oldAreaResponsableOfSolicitudRequerimientoListNewSolicitudRequerimiento != null && !oldAreaResponsableOfSolicitudRequerimientoListNewSolicitudRequerimiento.equals(area)) {
                        oldAreaResponsableOfSolicitudRequerimientoListNewSolicitudRequerimiento.getSolicitudRequerimientoList().remove(solicitudRequerimientoListNewSolicitudRequerimiento);
                        oldAreaResponsableOfSolicitudRequerimientoListNewSolicitudRequerimiento = em.merge(oldAreaResponsableOfSolicitudRequerimientoListNewSolicitudRequerimiento);
                    }
                }
            }
            for (FuncionarioDisico funcionarioDisicoListNewFuncionarioDisico : funcionarioDisicoListNew) {
                if (!funcionarioDisicoListOld.contains(funcionarioDisicoListNewFuncionarioDisico)) {
                    Area oldAreaOfFuncionarioDisicoListNewFuncionarioDisico = funcionarioDisicoListNewFuncionarioDisico.getArea();
                    funcionarioDisicoListNewFuncionarioDisico.setArea(area);
                    funcionarioDisicoListNewFuncionarioDisico = em.merge(funcionarioDisicoListNewFuncionarioDisico);
                    if (oldAreaOfFuncionarioDisicoListNewFuncionarioDisico != null && !oldAreaOfFuncionarioDisicoListNewFuncionarioDisico.equals(area)) {
                        oldAreaOfFuncionarioDisicoListNewFuncionarioDisico.getFuncionarioDisicoList().remove(funcionarioDisicoListNewFuncionarioDisico);
                        oldAreaOfFuncionarioDisicoListNewFuncionarioDisico = em.merge(oldAreaOfFuncionarioDisicoListNewFuncionarioDisico);
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
                Short id = area.getIdArea();
                if (findArea(id) == null) {
                    throw new NonexistentEntityException("The area with id " + id + " no longer exists.");
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
            Area area;
            try {
                area = em.getReference(Area.class, id);
                area.getIdArea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The area with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SolicitudRequerimiento> solicitudRequerimientoListOrphanCheck = area.getSolicitudRequerimientoList();
            for (SolicitudRequerimiento solicitudRequerimientoListOrphanCheckSolicitudRequerimiento : solicitudRequerimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the SolicitudRequerimiento " + solicitudRequerimientoListOrphanCheckSolicitudRequerimiento + " in its solicitudRequerimientoList field has a non-nullable areaResponsable field.");
            }
            List<FuncionarioDisico> funcionarioDisicoListOrphanCheck = area.getFuncionarioDisicoList();
            for (FuncionarioDisico funcionarioDisicoListOrphanCheckFuncionarioDisico : funcionarioDisicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the FuncionarioDisico " + funcionarioDisicoListOrphanCheckFuncionarioDisico + " in its funcionarioDisicoList field has a non-nullable area field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(area);
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

    public List<Area> findAreaEntities() {
        return findAreaEntities(true, -1, -1);
    }

    public List<Area> findAreaEntities(int maxResults, int firstResult) {
        return findAreaEntities(false, maxResults, firstResult);
    }

    private List<Area> findAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Area.class));
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

    public Area findArea(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Area.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Area> rt = cq.from(Area.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
