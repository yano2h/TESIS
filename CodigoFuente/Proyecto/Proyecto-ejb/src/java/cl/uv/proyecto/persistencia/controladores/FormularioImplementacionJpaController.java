/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.RollbackFailureException;
import cl.uv.proyecto.persistencia.entidades.FormularioImplementacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jano
 */
public class FormularioImplementacionJpaController implements Serializable {

    public FormularioImplementacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormularioImplementacion formularioImplementacion) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        SolicitudCambio solicitudCambioOrphanCheck = formularioImplementacion.getSolicitudCambio();
        if (solicitudCambioOrphanCheck != null) {
            FormularioImplementacion oldFormularioImplementacionOfSolicitudCambio = solicitudCambioOrphanCheck.getFormularioImplementacion();
            if (oldFormularioImplementacionOfSolicitudCambio != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The SolicitudCambio " + solicitudCambioOrphanCheck + " already has an item of type FormularioImplementacion whose solicitudCambio column cannot be null. Please make another selection for the solicitudCambio field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudCambio solicitudCambio = formularioImplementacion.getSolicitudCambio();
            if (solicitudCambio != null) {
                solicitudCambio = em.getReference(solicitudCambio.getClass(), solicitudCambio.getIdSolicitudCambio());
                formularioImplementacion.setSolicitudCambio(solicitudCambio);
            }
            FuncionarioDisico implementador = formularioImplementacion.getImplementador();
            if (implementador != null) {
                implementador = em.getReference(implementador.getClass(), implementador.getRut());
                formularioImplementacion.setImplementador(implementador);
            }
            FuncionarioDisico verificador = formularioImplementacion.getVerificador();
            if (verificador != null) {
                verificador = em.getReference(verificador.getClass(), verificador.getRut());
                formularioImplementacion.setVerificador(verificador);
            }
            em.persist(formularioImplementacion);
            if (solicitudCambio != null) {
                solicitudCambio.setFormularioImplementacion(formularioImplementacion);
                solicitudCambio = em.merge(solicitudCambio);
            }
            if (implementador != null) {
                implementador.getImplementadorForlumarios().add(formularioImplementacion);
                implementador = em.merge(implementador);
            }
            if (verificador != null) {
                verificador.getImplementadorForlumarios().add(formularioImplementacion);
                verificador = em.merge(verificador);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFormularioImplementacion(formularioImplementacion.getIdFormularioImplementacion()) != null) {
                throw new PreexistingEntityException("FormularioImplementacion " + formularioImplementacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormularioImplementacion formularioImplementacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FormularioImplementacion persistentFormularioImplementacion = em.find(FormularioImplementacion.class, formularioImplementacion.getIdFormularioImplementacion());
            SolicitudCambio solicitudCambioOld = persistentFormularioImplementacion.getSolicitudCambio();
            SolicitudCambio solicitudCambioNew = formularioImplementacion.getSolicitudCambio();
            FuncionarioDisico implementadorOld = persistentFormularioImplementacion.getImplementador();
            FuncionarioDisico implementadorNew = formularioImplementacion.getImplementador();
            FuncionarioDisico verificadorOld = persistentFormularioImplementacion.getVerificador();
            FuncionarioDisico verificadorNew = formularioImplementacion.getVerificador();
            List<String> illegalOrphanMessages = null;
            if (solicitudCambioNew != null && !solicitudCambioNew.equals(solicitudCambioOld)) {
                FormularioImplementacion oldFormularioImplementacionOfSolicitudCambio = solicitudCambioNew.getFormularioImplementacion();
                if (oldFormularioImplementacionOfSolicitudCambio != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The SolicitudCambio " + solicitudCambioNew + " already has an item of type FormularioImplementacion whose solicitudCambio column cannot be null. Please make another selection for the solicitudCambio field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (solicitudCambioNew != null) {
                solicitudCambioNew = em.getReference(solicitudCambioNew.getClass(), solicitudCambioNew.getIdSolicitudCambio());
                formularioImplementacion.setSolicitudCambio(solicitudCambioNew);
            }
            if (implementadorNew != null) {
                implementadorNew = em.getReference(implementadorNew.getClass(), implementadorNew.getRut());
                formularioImplementacion.setImplementador(implementadorNew);
            }
            if (verificadorNew != null) {
                verificadorNew = em.getReference(verificadorNew.getClass(), verificadorNew.getRut());
                formularioImplementacion.setVerificador(verificadorNew);
            }
            formularioImplementacion = em.merge(formularioImplementacion);
            if (solicitudCambioOld != null && !solicitudCambioOld.equals(solicitudCambioNew)) {
                solicitudCambioOld.setFormularioImplementacion(null);
                solicitudCambioOld = em.merge(solicitudCambioOld);
            }
            if (solicitudCambioNew != null && !solicitudCambioNew.equals(solicitudCambioOld)) {
                solicitudCambioNew.setFormularioImplementacion(formularioImplementacion);
                solicitudCambioNew = em.merge(solicitudCambioNew);
            }
            if (implementadorOld != null && !implementadorOld.equals(implementadorNew)) {
                implementadorOld.getImplementadorForlumarios().remove(formularioImplementacion);
                implementadorOld = em.merge(implementadorOld);
            }
            if (implementadorNew != null && !implementadorNew.equals(implementadorOld)) {
                implementadorNew.getImplementadorForlumarios().add(formularioImplementacion);
                implementadorNew = em.merge(implementadorNew);
            }
            if (verificadorOld != null && !verificadorOld.equals(verificadorNew)) {
                verificadorOld.getImplementadorForlumarios().remove(formularioImplementacion);
                verificadorOld = em.merge(verificadorOld);
            }
            if (verificadorNew != null && !verificadorNew.equals(verificadorOld)) {
                verificadorNew.getImplementadorForlumarios().add(formularioImplementacion);
                verificadorNew = em.merge(verificadorNew);
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
                Integer id = formularioImplementacion.getIdFormularioImplementacion();
                if (findFormularioImplementacion(id) == null) {
                    throw new NonexistentEntityException("The formularioImplementacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FormularioImplementacion formularioImplementacion;
            try {
                formularioImplementacion = em.getReference(FormularioImplementacion.class, id);
                formularioImplementacion.getIdFormularioImplementacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formularioImplementacion with id " + id + " no longer exists.", enfe);
            }
            SolicitudCambio solicitudCambio = formularioImplementacion.getSolicitudCambio();
            if (solicitudCambio != null) {
                solicitudCambio.setFormularioImplementacion(null);
                solicitudCambio = em.merge(solicitudCambio);
            }
            FuncionarioDisico implementador = formularioImplementacion.getImplementador();
            if (implementador != null) {
                implementador.getImplementadorForlumarios().remove(formularioImplementacion);
                implementador = em.merge(implementador);
            }
            FuncionarioDisico verificador = formularioImplementacion.getVerificador();
            if (verificador != null) {
                verificador.getImplementadorForlumarios().remove(formularioImplementacion);
                verificador = em.merge(verificador);
            }
            em.remove(formularioImplementacion);
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

    public List<FormularioImplementacion> findFormularioImplementacionEntities() {
        return findFormularioImplementacionEntities(true, -1, -1);
    }

    public List<FormularioImplementacion> findFormularioImplementacionEntities(int maxResults, int firstResult) {
        return findFormularioImplementacionEntities(false, maxResults, firstResult);
    }

    private List<FormularioImplementacion> findFormularioImplementacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormularioImplementacion.class));
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

    public FormularioImplementacion findFormularioImplementacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormularioImplementacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormularioImplementacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormularioImplementacion> rt = cq.from(FormularioImplementacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
