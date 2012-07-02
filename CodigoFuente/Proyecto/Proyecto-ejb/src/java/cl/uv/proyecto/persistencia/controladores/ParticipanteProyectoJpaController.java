/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class ParticipanteProyectoJpaController implements Serializable {

    public ParticipanteProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParticipanteProyecto participanteProyecto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (participanteProyecto.getParticipanteProyectoPK() == null) {
            participanteProyecto.setParticipanteProyectoPK(new ParticipanteProyectoPK());
        }
        participanteProyecto.getParticipanteProyectoPK().setIdProyecto(participanteProyecto.getProyecto().getIdProyecto());
        participanteProyecto.getParticipanteProyectoPK().setRutParticipante(participanteProyecto.getParticipante().getRut());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RolProyecto rol = participanteProyecto.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getIdRol());
                participanteProyecto.setRol(rol);
            }
            Proyecto proyecto = participanteProyecto.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getIdProyecto());
                participanteProyecto.setProyecto(proyecto);
            }
            FuncionarioDisico participante = participanteProyecto.getParticipante();
            if (participante != null) {
                participante = em.getReference(participante.getClass(), participante.getRut());
                participanteProyecto.setParticipante(participante);
            }
            em.persist(participanteProyecto);
            if (rol != null) {
                rol.getParticipanteProyectoList().add(participanteProyecto);
                rol = em.merge(rol);
            }
            if (proyecto != null) {
                proyecto.getParticipantes().add(participanteProyecto);
                proyecto = em.merge(proyecto);
            }
            if (participante != null) {
                participante.getParticipacionesProyecto().add(participanteProyecto);
                participante = em.merge(participante);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findParticipanteProyecto(participanteProyecto.getParticipanteProyectoPK()) != null) {
                throw new PreexistingEntityException("ParticipanteProyecto " + participanteProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParticipanteProyecto participanteProyecto) throws NonexistentEntityException, RollbackFailureException, Exception {
        participanteProyecto.getParticipanteProyectoPK().setIdProyecto(participanteProyecto.getProyecto().getIdProyecto());
        participanteProyecto.getParticipanteProyectoPK().setRutParticipante(participanteProyecto.getParticipante().getRut());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ParticipanteProyecto persistentParticipanteProyecto = em.find(ParticipanteProyecto.class, participanteProyecto.getParticipanteProyectoPK());
            RolProyecto rolOld = persistentParticipanteProyecto.getRol();
            RolProyecto rolNew = participanteProyecto.getRol();
            Proyecto proyectoOld = persistentParticipanteProyecto.getProyecto();
            Proyecto proyectoNew = participanteProyecto.getProyecto();
            FuncionarioDisico participanteOld = persistentParticipanteProyecto.getParticipante();
            FuncionarioDisico participanteNew = participanteProyecto.getParticipante();
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getIdRol());
                participanteProyecto.setRol(rolNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getIdProyecto());
                participanteProyecto.setProyecto(proyectoNew);
            }
            if (participanteNew != null) {
                participanteNew = em.getReference(participanteNew.getClass(), participanteNew.getRut());
                participanteProyecto.setParticipante(participanteNew);
            }
            participanteProyecto = em.merge(participanteProyecto);
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getParticipanteProyectoList().remove(participanteProyecto);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getParticipanteProyectoList().add(participanteProyecto);
                rolNew = em.merge(rolNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getParticipantes().remove(participanteProyecto);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getParticipantes().add(participanteProyecto);
                proyectoNew = em.merge(proyectoNew);
            }
            if (participanteOld != null && !participanteOld.equals(participanteNew)) {
                participanteOld.getParticipacionesProyecto().remove(participanteProyecto);
                participanteOld = em.merge(participanteOld);
            }
            if (participanteNew != null && !participanteNew.equals(participanteOld)) {
                participanteNew.getParticipacionesProyecto().add(participanteProyecto);
                participanteNew = em.merge(participanteNew);
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
                ParticipanteProyectoPK id = participanteProyecto.getParticipanteProyectoPK();
                if (findParticipanteProyecto(id) == null) {
                    throw new NonexistentEntityException("The participanteProyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ParticipanteProyectoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ParticipanteProyecto participanteProyecto;
            try {
                participanteProyecto = em.getReference(ParticipanteProyecto.class, id);
                participanteProyecto.getParticipanteProyectoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participanteProyecto with id " + id + " no longer exists.", enfe);
            }
            RolProyecto rol = participanteProyecto.getRol();
            if (rol != null) {
                rol.getParticipanteProyectoList().remove(participanteProyecto);
                rol = em.merge(rol);
            }
            Proyecto proyecto = participanteProyecto.getProyecto();
            if (proyecto != null) {
                proyecto.getParticipantes().remove(participanteProyecto);
                proyecto = em.merge(proyecto);
            }
            FuncionarioDisico participante = participanteProyecto.getParticipante();
            if (participante != null) {
                participante.getParticipacionesProyecto().remove(participanteProyecto);
                participante = em.merge(participante);
            }
            em.remove(participanteProyecto);
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

    public List<ParticipanteProyecto> findParticipanteProyectoEntities() {
        return findParticipanteProyectoEntities(true, -1, -1);
    }

    public List<ParticipanteProyecto> findParticipanteProyectoEntities(int maxResults, int firstResult) {
        return findParticipanteProyectoEntities(false, maxResults, firstResult);
    }

    private List<ParticipanteProyecto> findParticipanteProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParticipanteProyecto.class));
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

    public ParticipanteProyecto findParticipanteProyecto(ParticipanteProyectoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParticipanteProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipanteProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParticipanteProyecto> rt = cq.from(ParticipanteProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
