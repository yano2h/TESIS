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
import cl.uv.proyecto.persistencia.entidades.ParticipanteProyecto;
import cl.uv.proyecto.persistencia.entidades.RolProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class RolProyectoJpaController implements Serializable {

    public RolProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RolProyecto rolProyecto) throws RollbackFailureException, Exception {
        if (rolProyecto.getParticipanteProyectoList() == null) {
            rolProyecto.setParticipanteProyectoList(new ArrayList<ParticipanteProyecto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<ParticipanteProyecto> attachedParticipanteProyectoList = new ArrayList<ParticipanteProyecto>();
            for (ParticipanteProyecto participanteProyectoListParticipanteProyectoToAttach : rolProyecto.getParticipanteProyectoList()) {
                participanteProyectoListParticipanteProyectoToAttach = em.getReference(participanteProyectoListParticipanteProyectoToAttach.getClass(), participanteProyectoListParticipanteProyectoToAttach.getParticipanteProyectoPK());
                attachedParticipanteProyectoList.add(participanteProyectoListParticipanteProyectoToAttach);
            }
            rolProyecto.setParticipanteProyectoList(attachedParticipanteProyectoList);
            em.persist(rolProyecto);
            for (ParticipanteProyecto participanteProyectoListParticipanteProyecto : rolProyecto.getParticipanteProyectoList()) {
                RolProyecto oldRolOfParticipanteProyectoListParticipanteProyecto = participanteProyectoListParticipanteProyecto.getRol();
                participanteProyectoListParticipanteProyecto.setRol(rolProyecto);
                participanteProyectoListParticipanteProyecto = em.merge(participanteProyectoListParticipanteProyecto);
                if (oldRolOfParticipanteProyectoListParticipanteProyecto != null) {
                    oldRolOfParticipanteProyectoListParticipanteProyecto.getParticipanteProyectoList().remove(participanteProyectoListParticipanteProyecto);
                    oldRolOfParticipanteProyectoListParticipanteProyecto = em.merge(oldRolOfParticipanteProyectoListParticipanteProyecto);
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

    public void edit(RolProyecto rolProyecto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RolProyecto persistentRolProyecto = em.find(RolProyecto.class, rolProyecto.getIdRol());
            List<ParticipanteProyecto> participanteProyectoListOld = persistentRolProyecto.getParticipanteProyectoList();
            List<ParticipanteProyecto> participanteProyectoListNew = rolProyecto.getParticipanteProyectoList();
            List<String> illegalOrphanMessages = null;
            for (ParticipanteProyecto participanteProyectoListOldParticipanteProyecto : participanteProyectoListOld) {
                if (!participanteProyectoListNew.contains(participanteProyectoListOldParticipanteProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipanteProyecto " + participanteProyectoListOldParticipanteProyecto + " since its rol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ParticipanteProyecto> attachedParticipanteProyectoListNew = new ArrayList<ParticipanteProyecto>();
            for (ParticipanteProyecto participanteProyectoListNewParticipanteProyectoToAttach : participanteProyectoListNew) {
                participanteProyectoListNewParticipanteProyectoToAttach = em.getReference(participanteProyectoListNewParticipanteProyectoToAttach.getClass(), participanteProyectoListNewParticipanteProyectoToAttach.getParticipanteProyectoPK());
                attachedParticipanteProyectoListNew.add(participanteProyectoListNewParticipanteProyectoToAttach);
            }
            participanteProyectoListNew = attachedParticipanteProyectoListNew;
            rolProyecto.setParticipanteProyectoList(participanteProyectoListNew);
            rolProyecto = em.merge(rolProyecto);
            for (ParticipanteProyecto participanteProyectoListNewParticipanteProyecto : participanteProyectoListNew) {
                if (!participanteProyectoListOld.contains(participanteProyectoListNewParticipanteProyecto)) {
                    RolProyecto oldRolOfParticipanteProyectoListNewParticipanteProyecto = participanteProyectoListNewParticipanteProyecto.getRol();
                    participanteProyectoListNewParticipanteProyecto.setRol(rolProyecto);
                    participanteProyectoListNewParticipanteProyecto = em.merge(participanteProyectoListNewParticipanteProyecto);
                    if (oldRolOfParticipanteProyectoListNewParticipanteProyecto != null && !oldRolOfParticipanteProyectoListNewParticipanteProyecto.equals(rolProyecto)) {
                        oldRolOfParticipanteProyectoListNewParticipanteProyecto.getParticipanteProyectoList().remove(participanteProyectoListNewParticipanteProyecto);
                        oldRolOfParticipanteProyectoListNewParticipanteProyecto = em.merge(oldRolOfParticipanteProyectoListNewParticipanteProyecto);
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
                Short id = rolProyecto.getIdRol();
                if (findRolProyecto(id) == null) {
                    throw new NonexistentEntityException("The rolProyecto with id " + id + " no longer exists.");
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
            RolProyecto rolProyecto;
            try {
                rolProyecto = em.getReference(RolProyecto.class, id);
                rolProyecto.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolProyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ParticipanteProyecto> participanteProyectoListOrphanCheck = rolProyecto.getParticipanteProyectoList();
            for (ParticipanteProyecto participanteProyectoListOrphanCheckParticipanteProyecto : participanteProyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RolProyecto (" + rolProyecto + ") cannot be destroyed since the ParticipanteProyecto " + participanteProyectoListOrphanCheckParticipanteProyecto + " in its participanteProyectoList field has a non-nullable rol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rolProyecto);
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

    public List<RolProyecto> findRolProyectoEntities() {
        return findRolProyectoEntities(true, -1, -1);
    }

    public List<RolProyecto> findRolProyectoEntities(int maxResults, int firstResult) {
        return findRolProyectoEntities(false, maxResults, firstResult);
    }

    private List<RolProyecto> findRolProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RolProyecto.class));
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

    public RolProyecto findRolProyecto(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RolProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RolProyecto> rt = cq.from(RolProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
