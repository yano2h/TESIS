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
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import java.util.ArrayList;
import java.util.List;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (funcionario.getNotificaciones() == null) {
            funcionario.setNotificaciones(new ArrayList<Notificacion>());
        }
        if (funcionario.getSolicitudesRequerimientoEnviadas() == null) {
            funcionario.setSolicitudesRequerimientoEnviadas(new ArrayList<SolicitudRequerimiento>());
        }
        if (funcionario.getComentarioSolicitudList() == null) {
            funcionario.setComentarioSolicitudList(new ArrayList<ComentarioSolicitud>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Notificacion> attachedNotificaciones = new ArrayList<Notificacion>();
            for (Notificacion notificacionesNotificacionToAttach : funcionario.getNotificaciones()) {
                notificacionesNotificacionToAttach = em.getReference(notificacionesNotificacionToAttach.getClass(), notificacionesNotificacionToAttach.getIdNotificacion());
                attachedNotificaciones.add(notificacionesNotificacionToAttach);
            }
            funcionario.setNotificaciones(attachedNotificaciones);
            List<SolicitudRequerimiento> attachedSolicitudesRequerimientoEnviadas = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach : funcionario.getSolicitudesRequerimientoEnviadas()) {
                solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach = em.getReference(solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach.getClass(), solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudesRequerimientoEnviadas.add(solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach);
            }
            funcionario.setSolicitudesRequerimientoEnviadas(attachedSolicitudesRequerimientoEnviadas);
            List<ComentarioSolicitud> attachedComentarioSolicitudList = new ArrayList<ComentarioSolicitud>();
            for (ComentarioSolicitud comentarioSolicitudListComentarioSolicitudToAttach : funcionario.getComentarioSolicitudList()) {
                comentarioSolicitudListComentarioSolicitudToAttach = em.getReference(comentarioSolicitudListComentarioSolicitudToAttach.getClass(), comentarioSolicitudListComentarioSolicitudToAttach.getIdComentario());
                attachedComentarioSolicitudList.add(comentarioSolicitudListComentarioSolicitudToAttach);
            }
            funcionario.setComentarioSolicitudList(attachedComentarioSolicitudList);
            em.persist(funcionario);
            for (Notificacion notificacionesNotificacion : funcionario.getNotificaciones()) {
                Funcionario oldDestinatarioOfNotificacionesNotificacion = notificacionesNotificacion.getDestinatario();
                notificacionesNotificacion.setDestinatario(funcionario);
                notificacionesNotificacion = em.merge(notificacionesNotificacion);
                if (oldDestinatarioOfNotificacionesNotificacion != null) {
                    oldDestinatarioOfNotificacionesNotificacion.getNotificaciones().remove(notificacionesNotificacion);
                    oldDestinatarioOfNotificacionesNotificacion = em.merge(oldDestinatarioOfNotificacionesNotificacion);
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasSolicitudRequerimiento : funcionario.getSolicitudesRequerimientoEnviadas()) {
                Funcionario oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento = solicitudesRequerimientoEnviadasSolicitudRequerimiento.getSolicitante();
                solicitudesRequerimientoEnviadasSolicitudRequerimiento.setSolicitante(funcionario);
                solicitudesRequerimientoEnviadasSolicitudRequerimiento = em.merge(solicitudesRequerimientoEnviadasSolicitudRequerimiento);
                if (oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento != null) {
                    oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento.getSolicitudesRequerimientoEnviadas().remove(solicitudesRequerimientoEnviadasSolicitudRequerimiento);
                    oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento = em.merge(oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento);
                }
            }
            for (ComentarioSolicitud comentarioSolicitudListComentarioSolicitud : funcionario.getComentarioSolicitudList()) {
                Funcionario oldAutorOfComentarioSolicitudListComentarioSolicitud = comentarioSolicitudListComentarioSolicitud.getAutor();
                comentarioSolicitudListComentarioSolicitud.setAutor(funcionario);
                comentarioSolicitudListComentarioSolicitud = em.merge(comentarioSolicitudListComentarioSolicitud);
                if (oldAutorOfComentarioSolicitudListComentarioSolicitud != null) {
                    oldAutorOfComentarioSolicitudListComentarioSolicitud.getComentarioSolicitudList().remove(comentarioSolicitudListComentarioSolicitud);
                    oldAutorOfComentarioSolicitudListComentarioSolicitud = em.merge(oldAutorOfComentarioSolicitudListComentarioSolicitud);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFuncionario(funcionario.getRut()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getRut());
            List<Notificacion> notificacionesOld = persistentFuncionario.getNotificaciones();
            List<Notificacion> notificacionesNew = funcionario.getNotificaciones();
            List<SolicitudRequerimiento> solicitudesRequerimientoEnviadasOld = persistentFuncionario.getSolicitudesRequerimientoEnviadas();
            List<SolicitudRequerimiento> solicitudesRequerimientoEnviadasNew = funcionario.getSolicitudesRequerimientoEnviadas();
            List<ComentarioSolicitud> comentarioSolicitudListOld = persistentFuncionario.getComentarioSolicitudList();
            List<ComentarioSolicitud> comentarioSolicitudListNew = funcionario.getComentarioSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (Notificacion notificacionesOldNotificacion : notificacionesOld) {
                if (!notificacionesNew.contains(notificacionesOldNotificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notificacion " + notificacionesOldNotificacion + " since its destinatario field is not nullable.");
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasOldSolicitudRequerimiento : solicitudesRequerimientoEnviadasOld) {
                if (!solicitudesRequerimientoEnviadasNew.contains(solicitudesRequerimientoEnviadasOldSolicitudRequerimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudRequerimiento " + solicitudesRequerimientoEnviadasOldSolicitudRequerimiento + " since its solicitante field is not nullable.");
                }
            }
            for (ComentarioSolicitud comentarioSolicitudListOldComentarioSolicitud : comentarioSolicitudListOld) {
                if (!comentarioSolicitudListNew.contains(comentarioSolicitudListOldComentarioSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComentarioSolicitud " + comentarioSolicitudListOldComentarioSolicitud + " since its autor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Notificacion> attachedNotificacionesNew = new ArrayList<Notificacion>();
            for (Notificacion notificacionesNewNotificacionToAttach : notificacionesNew) {
                notificacionesNewNotificacionToAttach = em.getReference(notificacionesNewNotificacionToAttach.getClass(), notificacionesNewNotificacionToAttach.getIdNotificacion());
                attachedNotificacionesNew.add(notificacionesNewNotificacionToAttach);
            }
            notificacionesNew = attachedNotificacionesNew;
            funcionario.setNotificaciones(notificacionesNew);
            List<SolicitudRequerimiento> attachedSolicitudesRequerimientoEnviadasNew = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach : solicitudesRequerimientoEnviadasNew) {
                solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach = em.getReference(solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach.getClass(), solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudesRequerimientoEnviadasNew.add(solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach);
            }
            solicitudesRequerimientoEnviadasNew = attachedSolicitudesRequerimientoEnviadasNew;
            funcionario.setSolicitudesRequerimientoEnviadas(solicitudesRequerimientoEnviadasNew);
            List<ComentarioSolicitud> attachedComentarioSolicitudListNew = new ArrayList<ComentarioSolicitud>();
            for (ComentarioSolicitud comentarioSolicitudListNewComentarioSolicitudToAttach : comentarioSolicitudListNew) {
                comentarioSolicitudListNewComentarioSolicitudToAttach = em.getReference(comentarioSolicitudListNewComentarioSolicitudToAttach.getClass(), comentarioSolicitudListNewComentarioSolicitudToAttach.getIdComentario());
                attachedComentarioSolicitudListNew.add(comentarioSolicitudListNewComentarioSolicitudToAttach);
            }
            comentarioSolicitudListNew = attachedComentarioSolicitudListNew;
            funcionario.setComentarioSolicitudList(comentarioSolicitudListNew);
            funcionario = em.merge(funcionario);
            for (Notificacion notificacionesNewNotificacion : notificacionesNew) {
                if (!notificacionesOld.contains(notificacionesNewNotificacion)) {
                    Funcionario oldDestinatarioOfNotificacionesNewNotificacion = notificacionesNewNotificacion.getDestinatario();
                    notificacionesNewNotificacion.setDestinatario(funcionario);
                    notificacionesNewNotificacion = em.merge(notificacionesNewNotificacion);
                    if (oldDestinatarioOfNotificacionesNewNotificacion != null && !oldDestinatarioOfNotificacionesNewNotificacion.equals(funcionario)) {
                        oldDestinatarioOfNotificacionesNewNotificacion.getNotificaciones().remove(notificacionesNewNotificacion);
                        oldDestinatarioOfNotificacionesNewNotificacion = em.merge(oldDestinatarioOfNotificacionesNewNotificacion);
                    }
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasNewSolicitudRequerimiento : solicitudesRequerimientoEnviadasNew) {
                if (!solicitudesRequerimientoEnviadasOld.contains(solicitudesRequerimientoEnviadasNewSolicitudRequerimiento)) {
                    Funcionario oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento = solicitudesRequerimientoEnviadasNewSolicitudRequerimiento.getSolicitante();
                    solicitudesRequerimientoEnviadasNewSolicitudRequerimiento.setSolicitante(funcionario);
                    solicitudesRequerimientoEnviadasNewSolicitudRequerimiento = em.merge(solicitudesRequerimientoEnviadasNewSolicitudRequerimiento);
                    if (oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento != null && !oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento.equals(funcionario)) {
                        oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento.getSolicitudesRequerimientoEnviadas().remove(solicitudesRequerimientoEnviadasNewSolicitudRequerimiento);
                        oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento = em.merge(oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento);
                    }
                }
            }
            for (ComentarioSolicitud comentarioSolicitudListNewComentarioSolicitud : comentarioSolicitudListNew) {
                if (!comentarioSolicitudListOld.contains(comentarioSolicitudListNewComentarioSolicitud)) {
                    Funcionario oldAutorOfComentarioSolicitudListNewComentarioSolicitud = comentarioSolicitudListNewComentarioSolicitud.getAutor();
                    comentarioSolicitudListNewComentarioSolicitud.setAutor(funcionario);
                    comentarioSolicitudListNewComentarioSolicitud = em.merge(comentarioSolicitudListNewComentarioSolicitud);
                    if (oldAutorOfComentarioSolicitudListNewComentarioSolicitud != null && !oldAutorOfComentarioSolicitudListNewComentarioSolicitud.equals(funcionario)) {
                        oldAutorOfComentarioSolicitudListNewComentarioSolicitud.getComentarioSolicitudList().remove(comentarioSolicitudListNewComentarioSolicitud);
                        oldAutorOfComentarioSolicitudListNewComentarioSolicitud = em.merge(oldAutorOfComentarioSolicitudListNewComentarioSolicitud);
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
                Integer id = funcionario.getRut();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getRut();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Notificacion> notificacionesOrphanCheck = funcionario.getNotificaciones();
            for (Notificacion notificacionesOrphanCheckNotificacion : notificacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Notificacion " + notificacionesOrphanCheckNotificacion + " in its notificaciones field has a non-nullable destinatario field.");
            }
            List<SolicitudRequerimiento> solicitudesRequerimientoEnviadasOrphanCheck = funcionario.getSolicitudesRequerimientoEnviadas();
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasOrphanCheckSolicitudRequerimiento : solicitudesRequerimientoEnviadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the SolicitudRequerimiento " + solicitudesRequerimientoEnviadasOrphanCheckSolicitudRequerimiento + " in its solicitudesRequerimientoEnviadas field has a non-nullable solicitante field.");
            }
            List<ComentarioSolicitud> comentarioSolicitudListOrphanCheck = funcionario.getComentarioSolicitudList();
            for (ComentarioSolicitud comentarioSolicitudListOrphanCheckComentarioSolicitud : comentarioSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the ComentarioSolicitud " + comentarioSolicitudListOrphanCheckComentarioSolicitud + " in its comentarioSolicitudList field has a non-nullable autor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(funcionario);
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

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
