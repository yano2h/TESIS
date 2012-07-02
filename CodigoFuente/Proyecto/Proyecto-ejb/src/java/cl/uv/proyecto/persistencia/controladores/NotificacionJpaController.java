/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class NotificacionJpaController implements Serializable {

    public NotificacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificacion notificacion) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcionario destinatario = notificacion.getDestinatario();
            if (destinatario != null) {
                destinatario = em.getReference(destinatario.getClass(), destinatario.getRut());
                notificacion.setDestinatario(destinatario);
            }
            em.persist(notificacion);
            if (destinatario != null) {
                destinatario.getNotificaciones().add(notificacion);
                destinatario = em.merge(destinatario);
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

    public void edit(Notificacion notificacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Notificacion persistentNotificacion = em.find(Notificacion.class, notificacion.getIdNotificacion());
            Funcionario destinatarioOld = persistentNotificacion.getDestinatario();
            Funcionario destinatarioNew = notificacion.getDestinatario();
            if (destinatarioNew != null) {
                destinatarioNew = em.getReference(destinatarioNew.getClass(), destinatarioNew.getRut());
                notificacion.setDestinatario(destinatarioNew);
            }
            notificacion = em.merge(notificacion);
            if (destinatarioOld != null && !destinatarioOld.equals(destinatarioNew)) {
                destinatarioOld.getNotificaciones().remove(notificacion);
                destinatarioOld = em.merge(destinatarioOld);
            }
            if (destinatarioNew != null && !destinatarioNew.equals(destinatarioOld)) {
                destinatarioNew.getNotificaciones().add(notificacion);
                destinatarioNew = em.merge(destinatarioNew);
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
                Long id = notificacion.getIdNotificacion();
                if (findNotificacion(id) == null) {
                    throw new NonexistentEntityException("The notificacion with id " + id + " no longer exists.");
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
            Notificacion notificacion;
            try {
                notificacion = em.getReference(Notificacion.class, id);
                notificacion.getIdNotificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacion with id " + id + " no longer exists.", enfe);
            }
            Funcionario destinatario = notificacion.getDestinatario();
            if (destinatario != null) {
                destinatario.getNotificaciones().remove(notificacion);
                destinatario = em.merge(destinatario);
            }
            em.remove(notificacion);
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

    public List<Notificacion> findNotificacionEntities() {
        return findNotificacionEntities(true, -1, -1);
    }

    public List<Notificacion> findNotificacionEntities(int maxResults, int firstResult) {
        return findNotificacionEntities(false, maxResults, firstResult);
    }

    private List<Notificacion> findNotificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificacion.class));
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

    public Notificacion findNotificacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificacion> rt = cq.from(Notificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
