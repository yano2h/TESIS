/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.EstadisticaPersonal;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class EstadisticaPersonalJpaController implements Serializable {

    public EstadisticaPersonalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadisticaPersonal estadisticaPersonal) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FuncionarioDisico funcionarioDisico = estadisticaPersonal.getFuncionarioDisico();
            if (funcionarioDisico != null) {
                funcionarioDisico = em.getReference(funcionarioDisico.getClass(), funcionarioDisico.getRut());
                estadisticaPersonal.setFuncionarioDisico(funcionarioDisico);
            }
            em.persist(estadisticaPersonal);
            if (funcionarioDisico != null) {
                funcionarioDisico.getEstadisticasPersonales().add(estadisticaPersonal);
                funcionarioDisico = em.merge(funcionarioDisico);
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

    public void edit(EstadisticaPersonal estadisticaPersonal) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadisticaPersonal persistentEstadisticaPersonal = em.find(EstadisticaPersonal.class, estadisticaPersonal.getId());
            FuncionarioDisico funcionarioDisicoOld = persistentEstadisticaPersonal.getFuncionarioDisico();
            FuncionarioDisico funcionarioDisicoNew = estadisticaPersonal.getFuncionarioDisico();
            if (funcionarioDisicoNew != null) {
                funcionarioDisicoNew = em.getReference(funcionarioDisicoNew.getClass(), funcionarioDisicoNew.getRut());
                estadisticaPersonal.setFuncionarioDisico(funcionarioDisicoNew);
            }
            estadisticaPersonal = em.merge(estadisticaPersonal);
            if (funcionarioDisicoOld != null && !funcionarioDisicoOld.equals(funcionarioDisicoNew)) {
                funcionarioDisicoOld.getEstadisticasPersonales().remove(estadisticaPersonal);
                funcionarioDisicoOld = em.merge(funcionarioDisicoOld);
            }
            if (funcionarioDisicoNew != null && !funcionarioDisicoNew.equals(funcionarioDisicoOld)) {
                funcionarioDisicoNew.getEstadisticasPersonales().add(estadisticaPersonal);
                funcionarioDisicoNew = em.merge(funcionarioDisicoNew);
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
                Long id = estadisticaPersonal.getId();
                if (findEstadisticaPersonal(id) == null) {
                    throw new NonexistentEntityException("The estadisticaPersonal with id " + id + " no longer exists.");
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
            EstadisticaPersonal estadisticaPersonal;
            try {
                estadisticaPersonal = em.getReference(EstadisticaPersonal.class, id);
                estadisticaPersonal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadisticaPersonal with id " + id + " no longer exists.", enfe);
            }
            FuncionarioDisico funcionarioDisico = estadisticaPersonal.getFuncionarioDisico();
            if (funcionarioDisico != null) {
                funcionarioDisico.getEstadisticasPersonales().remove(estadisticaPersonal);
                funcionarioDisico = em.merge(funcionarioDisico);
            }
            em.remove(estadisticaPersonal);
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

    public List<EstadisticaPersonal> findEstadisticaPersonalEntities() {
        return findEstadisticaPersonalEntities(true, -1, -1);
    }

    public List<EstadisticaPersonal> findEstadisticaPersonalEntities(int maxResults, int firstResult) {
        return findEstadisticaPersonalEntities(false, maxResults, firstResult);
    }

    private List<EstadisticaPersonal> findEstadisticaPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadisticaPersonal.class));
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

    public EstadisticaPersonal findEstadisticaPersonal(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadisticaPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadisticaPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadisticaPersonal> rt = cq.from(EstadisticaPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
