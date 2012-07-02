/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class ComentarioSolicitudJpaController implements Serializable {

    public ComentarioSolicitudJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComentarioSolicitud comentarioSolicitud) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcionario autor = comentarioSolicitud.getAutor();
            if (autor != null) {
                autor = em.getReference(autor.getClass(), autor.getRut());
                comentarioSolicitud.setAutor(autor);
            }
            SolicitudRequerimiento solicitudRequerimiento = comentarioSolicitud.getSolicitudRequerimiento();
            if (solicitudRequerimiento != null) {
                solicitudRequerimiento = em.getReference(solicitudRequerimiento.getClass(), solicitudRequerimiento.getIdSolicitudRequerimiento());
                comentarioSolicitud.setSolicitudRequerimiento(solicitudRequerimiento);
            }
            em.persist(comentarioSolicitud);
            if (autor != null) {
                autor.getComentarioSolicitudList().add(comentarioSolicitud);
                autor = em.merge(autor);
            }
            if (solicitudRequerimiento != null) {
                solicitudRequerimiento.getComentarios().add(comentarioSolicitud);
                solicitudRequerimiento = em.merge(solicitudRequerimiento);
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

    public void edit(ComentarioSolicitud comentarioSolicitud) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ComentarioSolicitud persistentComentarioSolicitud = em.find(ComentarioSolicitud.class, comentarioSolicitud.getIdComentario());
            Funcionario autorOld = persistentComentarioSolicitud.getAutor();
            Funcionario autorNew = comentarioSolicitud.getAutor();
            SolicitudRequerimiento solicitudRequerimientoOld = persistentComentarioSolicitud.getSolicitudRequerimiento();
            SolicitudRequerimiento solicitudRequerimientoNew = comentarioSolicitud.getSolicitudRequerimiento();
            if (autorNew != null) {
                autorNew = em.getReference(autorNew.getClass(), autorNew.getRut());
                comentarioSolicitud.setAutor(autorNew);
            }
            if (solicitudRequerimientoNew != null) {
                solicitudRequerimientoNew = em.getReference(solicitudRequerimientoNew.getClass(), solicitudRequerimientoNew.getIdSolicitudRequerimiento());
                comentarioSolicitud.setSolicitudRequerimiento(solicitudRequerimientoNew);
            }
            comentarioSolicitud = em.merge(comentarioSolicitud);
            if (autorOld != null && !autorOld.equals(autorNew)) {
                autorOld.getComentarioSolicitudList().remove(comentarioSolicitud);
                autorOld = em.merge(autorOld);
            }
            if (autorNew != null && !autorNew.equals(autorOld)) {
                autorNew.getComentarioSolicitudList().add(comentarioSolicitud);
                autorNew = em.merge(autorNew);
            }
            if (solicitudRequerimientoOld != null && !solicitudRequerimientoOld.equals(solicitudRequerimientoNew)) {
                solicitudRequerimientoOld.getComentarios().remove(comentarioSolicitud);
                solicitudRequerimientoOld = em.merge(solicitudRequerimientoOld);
            }
            if (solicitudRequerimientoNew != null && !solicitudRequerimientoNew.equals(solicitudRequerimientoOld)) {
                solicitudRequerimientoNew.getComentarios().add(comentarioSolicitud);
                solicitudRequerimientoNew = em.merge(solicitudRequerimientoNew);
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
                Long id = comentarioSolicitud.getIdComentario();
                if (findComentarioSolicitud(id) == null) {
                    throw new NonexistentEntityException("The comentarioSolicitud with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ComentarioSolicitud comentarioSolicitud;
            try {
                comentarioSolicitud = em.getReference(ComentarioSolicitud.class, id);
                comentarioSolicitud.getIdComentario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentarioSolicitud with id " + id + " no longer exists.", enfe);
            }
            Funcionario autor = comentarioSolicitud.getAutor();
            if (autor != null) {
                autor.getComentarioSolicitudList().remove(comentarioSolicitud);
                autor = em.merge(autor);
            }
            SolicitudRequerimiento solicitudRequerimiento = comentarioSolicitud.getSolicitudRequerimiento();
            if (solicitudRequerimiento != null) {
                solicitudRequerimiento.getComentarios().remove(comentarioSolicitud);
                solicitudRequerimiento = em.merge(solicitudRequerimiento);
            }
            em.remove(comentarioSolicitud);
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

    public List<ComentarioSolicitud> findComentarioSolicitudEntities() {
        return findComentarioSolicitudEntities(true, -1, -1);
    }

    public List<ComentarioSolicitud> findComentarioSolicitudEntities(int maxResults, int firstResult) {
        return findComentarioSolicitudEntities(false, maxResults, firstResult);
    }

    private List<ComentarioSolicitud> findComentarioSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComentarioSolicitud.class));
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

    public ComentarioSolicitud findComentarioSolicitud(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComentarioSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComentarioSolicitud> rt = cq.from(ComentarioSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
