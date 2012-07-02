/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class SolicitudRequerimientoJpaController implements Serializable {

    public SolicitudRequerimientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudRequerimiento solicitudRequerimiento) throws RollbackFailureException, Exception {
        if (solicitudRequerimiento.getComentarios() == null) {
            solicitudRequerimiento.setComentarios(new ArrayList<ComentarioSolicitud>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoPrioridad prioridadSolicitud = solicitudRequerimiento.getPrioridadSolicitud();
            if (prioridadSolicitud != null) {
                prioridadSolicitud = em.getReference(prioridadSolicitud.getClass(), prioridadSolicitud.getIdTipoPrioridad());
                solicitudRequerimiento.setPrioridadSolicitud(prioridadSolicitud);
            }
            Funcionario solicitante = solicitudRequerimiento.getSolicitante();
            if (solicitante != null) {
                solicitante = em.getReference(solicitante.getClass(), solicitante.getRut());
                solicitudRequerimiento.setSolicitante(solicitante);
            }
            FuncionarioDisico responsable = solicitudRequerimiento.getResponsable();
            if (responsable != null) {
                responsable = em.getReference(responsable.getClass(), responsable.getRut());
                solicitudRequerimiento.setResponsable(responsable);
            }
            Area areaResponsable = solicitudRequerimiento.getAreaResponsable();
            if (areaResponsable != null) {
                areaResponsable = em.getReference(areaResponsable.getClass(), areaResponsable.getIdArea());
                solicitudRequerimiento.setAreaResponsable(areaResponsable);
            }
            EstadoSolicitudRequerimiento estadoSolicitud = solicitudRequerimiento.getEstadoSolicitud();
            if (estadoSolicitud != null) {
                estadoSolicitud = em.getReference(estadoSolicitud.getClass(), estadoSolicitud.getIdEstadoSolicitudRequerimiento());
                solicitudRequerimiento.setEstadoSolicitud(estadoSolicitud);
            }
            TipoSolicitudRequerimiento tipoSolicitud = solicitudRequerimiento.getTipoSolicitud();
            if (tipoSolicitud != null) {
                tipoSolicitud = em.getReference(tipoSolicitud.getClass(), tipoSolicitud.getIdTipoSolicitudRequerimiento());
                solicitudRequerimiento.setTipoSolicitud(tipoSolicitud);
            }
            List<ComentarioSolicitud> attachedComentarios = new ArrayList<ComentarioSolicitud>();
            for (ComentarioSolicitud comentariosComentarioSolicitudToAttach : solicitudRequerimiento.getComentarios()) {
                comentariosComentarioSolicitudToAttach = em.getReference(comentariosComentarioSolicitudToAttach.getClass(), comentariosComentarioSolicitudToAttach.getIdComentario());
                attachedComentarios.add(comentariosComentarioSolicitudToAttach);
            }
            solicitudRequerimiento.setComentarios(attachedComentarios);
            em.persist(solicitudRequerimiento);
            if (prioridadSolicitud != null) {
                prioridadSolicitud.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                prioridadSolicitud = em.merge(prioridadSolicitud);
            }
            if (solicitante != null) {
                solicitante.getSolicitudesRequerimientoEnviadas().add(solicitudRequerimiento);
                solicitante = em.merge(solicitante);
            }
            if (responsable != null) {
                responsable.getSolicitudesRequerimientosAsignadas().add(solicitudRequerimiento);
                responsable = em.merge(responsable);
            }
            if (areaResponsable != null) {
                areaResponsable.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                areaResponsable = em.merge(areaResponsable);
            }
            if (estadoSolicitud != null) {
                estadoSolicitud.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                estadoSolicitud = em.merge(estadoSolicitud);
            }
            if (tipoSolicitud != null) {
                tipoSolicitud.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                tipoSolicitud = em.merge(tipoSolicitud);
            }
            for (ComentarioSolicitud comentariosComentarioSolicitud : solicitudRequerimiento.getComentarios()) {
                SolicitudRequerimiento oldSolicitudRequerimientoOfComentariosComentarioSolicitud = comentariosComentarioSolicitud.getSolicitudRequerimiento();
                comentariosComentarioSolicitud.setSolicitudRequerimiento(solicitudRequerimiento);
                comentariosComentarioSolicitud = em.merge(comentariosComentarioSolicitud);
                if (oldSolicitudRequerimientoOfComentariosComentarioSolicitud != null) {
                    oldSolicitudRequerimientoOfComentariosComentarioSolicitud.getComentarios().remove(comentariosComentarioSolicitud);
                    oldSolicitudRequerimientoOfComentariosComentarioSolicitud = em.merge(oldSolicitudRequerimientoOfComentariosComentarioSolicitud);
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

    public void edit(SolicitudRequerimiento solicitudRequerimiento) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudRequerimiento persistentSolicitudRequerimiento = em.find(SolicitudRequerimiento.class, solicitudRequerimiento.getIdSolicitudRequerimiento());
            TipoPrioridad prioridadSolicitudOld = persistentSolicitudRequerimiento.getPrioridadSolicitud();
            TipoPrioridad prioridadSolicitudNew = solicitudRequerimiento.getPrioridadSolicitud();
            Funcionario solicitanteOld = persistentSolicitudRequerimiento.getSolicitante();
            Funcionario solicitanteNew = solicitudRequerimiento.getSolicitante();
            FuncionarioDisico responsableOld = persistentSolicitudRequerimiento.getResponsable();
            FuncionarioDisico responsableNew = solicitudRequerimiento.getResponsable();
            Area areaResponsableOld = persistentSolicitudRequerimiento.getAreaResponsable();
            Area areaResponsableNew = solicitudRequerimiento.getAreaResponsable();
            EstadoSolicitudRequerimiento estadoSolicitudOld = persistentSolicitudRequerimiento.getEstadoSolicitud();
            EstadoSolicitudRequerimiento estadoSolicitudNew = solicitudRequerimiento.getEstadoSolicitud();
            TipoSolicitudRequerimiento tipoSolicitudOld = persistentSolicitudRequerimiento.getTipoSolicitud();
            TipoSolicitudRequerimiento tipoSolicitudNew = solicitudRequerimiento.getTipoSolicitud();
            List<ComentarioSolicitud> comentariosOld = persistentSolicitudRequerimiento.getComentarios();
            List<ComentarioSolicitud> comentariosNew = solicitudRequerimiento.getComentarios();
            List<String> illegalOrphanMessages = null;
            for (ComentarioSolicitud comentariosOldComentarioSolicitud : comentariosOld) {
                if (!comentariosNew.contains(comentariosOldComentarioSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComentarioSolicitud " + comentariosOldComentarioSolicitud + " since its solicitudRequerimiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (prioridadSolicitudNew != null) {
                prioridadSolicitudNew = em.getReference(prioridadSolicitudNew.getClass(), prioridadSolicitudNew.getIdTipoPrioridad());
                solicitudRequerimiento.setPrioridadSolicitud(prioridadSolicitudNew);
            }
            if (solicitanteNew != null) {
                solicitanteNew = em.getReference(solicitanteNew.getClass(), solicitanteNew.getRut());
                solicitudRequerimiento.setSolicitante(solicitanteNew);
            }
            if (responsableNew != null) {
                responsableNew = em.getReference(responsableNew.getClass(), responsableNew.getRut());
                solicitudRequerimiento.setResponsable(responsableNew);
            }
            if (areaResponsableNew != null) {
                areaResponsableNew = em.getReference(areaResponsableNew.getClass(), areaResponsableNew.getIdArea());
                solicitudRequerimiento.setAreaResponsable(areaResponsableNew);
            }
            if (estadoSolicitudNew != null) {
                estadoSolicitudNew = em.getReference(estadoSolicitudNew.getClass(), estadoSolicitudNew.getIdEstadoSolicitudRequerimiento());
                solicitudRequerimiento.setEstadoSolicitud(estadoSolicitudNew);
            }
            if (tipoSolicitudNew != null) {
                tipoSolicitudNew = em.getReference(tipoSolicitudNew.getClass(), tipoSolicitudNew.getIdTipoSolicitudRequerimiento());
                solicitudRequerimiento.setTipoSolicitud(tipoSolicitudNew);
            }
            List<ComentarioSolicitud> attachedComentariosNew = new ArrayList<ComentarioSolicitud>();
            for (ComentarioSolicitud comentariosNewComentarioSolicitudToAttach : comentariosNew) {
                comentariosNewComentarioSolicitudToAttach = em.getReference(comentariosNewComentarioSolicitudToAttach.getClass(), comentariosNewComentarioSolicitudToAttach.getIdComentario());
                attachedComentariosNew.add(comentariosNewComentarioSolicitudToAttach);
            }
            comentariosNew = attachedComentariosNew;
            solicitudRequerimiento.setComentarios(comentariosNew);
            solicitudRequerimiento = em.merge(solicitudRequerimiento);
            if (prioridadSolicitudOld != null && !prioridadSolicitudOld.equals(prioridadSolicitudNew)) {
                prioridadSolicitudOld.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                prioridadSolicitudOld = em.merge(prioridadSolicitudOld);
            }
            if (prioridadSolicitudNew != null && !prioridadSolicitudNew.equals(prioridadSolicitudOld)) {
                prioridadSolicitudNew.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                prioridadSolicitudNew = em.merge(prioridadSolicitudNew);
            }
            if (solicitanteOld != null && !solicitanteOld.equals(solicitanteNew)) {
                solicitanteOld.getSolicitudesRequerimientoEnviadas().remove(solicitudRequerimiento);
                solicitanteOld = em.merge(solicitanteOld);
            }
            if (solicitanteNew != null && !solicitanteNew.equals(solicitanteOld)) {
                solicitanteNew.getSolicitudesRequerimientoEnviadas().add(solicitudRequerimiento);
                solicitanteNew = em.merge(solicitanteNew);
            }
            if (responsableOld != null && !responsableOld.equals(responsableNew)) {
                responsableOld.getSolicitudesRequerimientosAsignadas().remove(solicitudRequerimiento);
                responsableOld = em.merge(responsableOld);
            }
            if (responsableNew != null && !responsableNew.equals(responsableOld)) {
                responsableNew.getSolicitudesRequerimientosAsignadas().add(solicitudRequerimiento);
                responsableNew = em.merge(responsableNew);
            }
            if (areaResponsableOld != null && !areaResponsableOld.equals(areaResponsableNew)) {
                areaResponsableOld.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                areaResponsableOld = em.merge(areaResponsableOld);
            }
            if (areaResponsableNew != null && !areaResponsableNew.equals(areaResponsableOld)) {
                areaResponsableNew.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                areaResponsableNew = em.merge(areaResponsableNew);
            }
            if (estadoSolicitudOld != null && !estadoSolicitudOld.equals(estadoSolicitudNew)) {
                estadoSolicitudOld.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                estadoSolicitudOld = em.merge(estadoSolicitudOld);
            }
            if (estadoSolicitudNew != null && !estadoSolicitudNew.equals(estadoSolicitudOld)) {
                estadoSolicitudNew.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                estadoSolicitudNew = em.merge(estadoSolicitudNew);
            }
            if (tipoSolicitudOld != null && !tipoSolicitudOld.equals(tipoSolicitudNew)) {
                tipoSolicitudOld.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                tipoSolicitudOld = em.merge(tipoSolicitudOld);
            }
            if (tipoSolicitudNew != null && !tipoSolicitudNew.equals(tipoSolicitudOld)) {
                tipoSolicitudNew.getSolicitudRequerimientoList().add(solicitudRequerimiento);
                tipoSolicitudNew = em.merge(tipoSolicitudNew);
            }
            for (ComentarioSolicitud comentariosNewComentarioSolicitud : comentariosNew) {
                if (!comentariosOld.contains(comentariosNewComentarioSolicitud)) {
                    SolicitudRequerimiento oldSolicitudRequerimientoOfComentariosNewComentarioSolicitud = comentariosNewComentarioSolicitud.getSolicitudRequerimiento();
                    comentariosNewComentarioSolicitud.setSolicitudRequerimiento(solicitudRequerimiento);
                    comentariosNewComentarioSolicitud = em.merge(comentariosNewComentarioSolicitud);
                    if (oldSolicitudRequerimientoOfComentariosNewComentarioSolicitud != null && !oldSolicitudRequerimientoOfComentariosNewComentarioSolicitud.equals(solicitudRequerimiento)) {
                        oldSolicitudRequerimientoOfComentariosNewComentarioSolicitud.getComentarios().remove(comentariosNewComentarioSolicitud);
                        oldSolicitudRequerimientoOfComentariosNewComentarioSolicitud = em.merge(oldSolicitudRequerimientoOfComentariosNewComentarioSolicitud);
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
                Long id = solicitudRequerimiento.getIdSolicitudRequerimiento();
                if (findSolicitudRequerimiento(id) == null) {
                    throw new NonexistentEntityException("The solicitudRequerimiento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudRequerimiento solicitudRequerimiento;
            try {
                solicitudRequerimiento = em.getReference(SolicitudRequerimiento.class, id);
                solicitudRequerimiento.getIdSolicitudRequerimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudRequerimiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ComentarioSolicitud> comentariosOrphanCheck = solicitudRequerimiento.getComentarios();
            for (ComentarioSolicitud comentariosOrphanCheckComentarioSolicitud : comentariosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SolicitudRequerimiento (" + solicitudRequerimiento + ") cannot be destroyed since the ComentarioSolicitud " + comentariosOrphanCheckComentarioSolicitud + " in its comentarios field has a non-nullable solicitudRequerimiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoPrioridad prioridadSolicitud = solicitudRequerimiento.getPrioridadSolicitud();
            if (prioridadSolicitud != null) {
                prioridadSolicitud.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                prioridadSolicitud = em.merge(prioridadSolicitud);
            }
            Funcionario solicitante = solicitudRequerimiento.getSolicitante();
            if (solicitante != null) {
                solicitante.getSolicitudesRequerimientoEnviadas().remove(solicitudRequerimiento);
                solicitante = em.merge(solicitante);
            }
            FuncionarioDisico responsable = solicitudRequerimiento.getResponsable();
            if (responsable != null) {
                responsable.getSolicitudesRequerimientosAsignadas().remove(solicitudRequerimiento);
                responsable = em.merge(responsable);
            }
            Area areaResponsable = solicitudRequerimiento.getAreaResponsable();
            if (areaResponsable != null) {
                areaResponsable.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                areaResponsable = em.merge(areaResponsable);
            }
            EstadoSolicitudRequerimiento estadoSolicitud = solicitudRequerimiento.getEstadoSolicitud();
            if (estadoSolicitud != null) {
                estadoSolicitud.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                estadoSolicitud = em.merge(estadoSolicitud);
            }
            TipoSolicitudRequerimiento tipoSolicitud = solicitudRequerimiento.getTipoSolicitud();
            if (tipoSolicitud != null) {
                tipoSolicitud.getSolicitudRequerimientoList().remove(solicitudRequerimiento);
                tipoSolicitud = em.merge(tipoSolicitud);
            }
            em.remove(solicitudRequerimiento);
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

    public List<SolicitudRequerimiento> findSolicitudRequerimientoEntities() {
        return findSolicitudRequerimientoEntities(true, -1, -1);
    }

    public List<SolicitudRequerimiento> findSolicitudRequerimientoEntities(int maxResults, int firstResult) {
        return findSolicitudRequerimientoEntities(false, maxResults, firstResult);
    }

    private List<SolicitudRequerimiento> findSolicitudRequerimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudRequerimiento.class));
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

    public SolicitudRequerimiento findSolicitudRequerimiento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudRequerimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudRequerimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudRequerimiento> rt = cq.from(SolicitudRequerimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
