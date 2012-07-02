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
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) throws RollbackFailureException, Exception {
        if (proyecto.getItemsDeConfiguracion() == null) {
            proyecto.setItemsDeConfiguracion(new ArrayList<ItemConfiguracion>());
        }
        if (proyecto.getTareasAgendadas() == null) {
            proyecto.setTareasAgendadas(new ArrayList<TareaProyecto>());
        }
        if (proyecto.getTareasScmProyecto() == null) {
            proyecto.setTareasScmProyecto(new ArrayList<TareaScmProyecto>());
        }
        if (proyecto.getParticipantes() == null) {
            proyecto.setParticipantes(new ArrayList<ParticipanteProyecto>());
        }
        if (proyecto.getSolicitudesDeCambio() == null) {
            proyecto.setSolicitudesDeCambio(new ArrayList<SolicitudCambio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoProyecto estadoProyecto = proyecto.getEstadoProyecto();
            if (estadoProyecto != null) {
                estadoProyecto = em.getReference(estadoProyecto.getClass(), estadoProyecto.getIdEstadoProyecto());
                proyecto.setEstadoProyecto(estadoProyecto);
            }
            TipoProyecto tipoProyecto = proyecto.getTipoProyecto();
            if (tipoProyecto != null) {
                tipoProyecto = em.getReference(tipoProyecto.getClass(), tipoProyecto.getIdTipoProyecto());
                proyecto.setTipoProyecto(tipoProyecto);
            }
            List<ItemConfiguracion> attachedItemsDeConfiguracion = new ArrayList<ItemConfiguracion>();
            for (ItemConfiguracion itemsDeConfiguracionItemConfiguracionToAttach : proyecto.getItemsDeConfiguracion()) {
                itemsDeConfiguracionItemConfiguracionToAttach = em.getReference(itemsDeConfiguracionItemConfiguracionToAttach.getClass(), itemsDeConfiguracionItemConfiguracionToAttach.getIdItemConfiguracion());
                attachedItemsDeConfiguracion.add(itemsDeConfiguracionItemConfiguracionToAttach);
            }
            proyecto.setItemsDeConfiguracion(attachedItemsDeConfiguracion);
            List<TareaProyecto> attachedTareasAgendadas = new ArrayList<TareaProyecto>();
            for (TareaProyecto tareasAgendadasTareaProyectoToAttach : proyecto.getTareasAgendadas()) {
                tareasAgendadasTareaProyectoToAttach = em.getReference(tareasAgendadasTareaProyectoToAttach.getClass(), tareasAgendadasTareaProyectoToAttach.getIdTareaProyecto());
                attachedTareasAgendadas.add(tareasAgendadasTareaProyectoToAttach);
            }
            proyecto.setTareasAgendadas(attachedTareasAgendadas);
            List<TareaScmProyecto> attachedTareasScmProyecto = new ArrayList<TareaScmProyecto>();
            for (TareaScmProyecto tareasScmProyectoTareaScmProyectoToAttach : proyecto.getTareasScmProyecto()) {
                tareasScmProyectoTareaScmProyectoToAttach = em.getReference(tareasScmProyectoTareaScmProyectoToAttach.getClass(), tareasScmProyectoTareaScmProyectoToAttach.getTareaScmProyectoPK());
                attachedTareasScmProyecto.add(tareasScmProyectoTareaScmProyectoToAttach);
            }
            proyecto.setTareasScmProyecto(attachedTareasScmProyecto);
            List<ParticipanteProyecto> attachedParticipantes = new ArrayList<ParticipanteProyecto>();
            for (ParticipanteProyecto participantesParticipanteProyectoToAttach : proyecto.getParticipantes()) {
                participantesParticipanteProyectoToAttach = em.getReference(participantesParticipanteProyectoToAttach.getClass(), participantesParticipanteProyectoToAttach.getParticipanteProyectoPK());
                attachedParticipantes.add(participantesParticipanteProyectoToAttach);
            }
            proyecto.setParticipantes(attachedParticipantes);
            List<SolicitudCambio> attachedSolicitudesDeCambio = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesDeCambioSolicitudCambioToAttach : proyecto.getSolicitudesDeCambio()) {
                solicitudesDeCambioSolicitudCambioToAttach = em.getReference(solicitudesDeCambioSolicitudCambioToAttach.getClass(), solicitudesDeCambioSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesDeCambio.add(solicitudesDeCambioSolicitudCambioToAttach);
            }
            proyecto.setSolicitudesDeCambio(attachedSolicitudesDeCambio);
            em.persist(proyecto);
            if (estadoProyecto != null) {
                estadoProyecto.getProyectoList().add(proyecto);
                estadoProyecto = em.merge(estadoProyecto);
            }
            if (tipoProyecto != null) {
                tipoProyecto.getProyectoList().add(proyecto);
                tipoProyecto = em.merge(tipoProyecto);
            }
            for (ItemConfiguracion itemsDeConfiguracionItemConfiguracion : proyecto.getItemsDeConfiguracion()) {
                Proyecto oldProyectoOfItemsDeConfiguracionItemConfiguracion = itemsDeConfiguracionItemConfiguracion.getProyecto();
                itemsDeConfiguracionItemConfiguracion.setProyecto(proyecto);
                itemsDeConfiguracionItemConfiguracion = em.merge(itemsDeConfiguracionItemConfiguracion);
                if (oldProyectoOfItemsDeConfiguracionItemConfiguracion != null) {
                    oldProyectoOfItemsDeConfiguracionItemConfiguracion.getItemsDeConfiguracion().remove(itemsDeConfiguracionItemConfiguracion);
                    oldProyectoOfItemsDeConfiguracionItemConfiguracion = em.merge(oldProyectoOfItemsDeConfiguracionItemConfiguracion);
                }
            }
            for (TareaProyecto tareasAgendadasTareaProyecto : proyecto.getTareasAgendadas()) {
                Proyecto oldProyectoOfTareasAgendadasTareaProyecto = tareasAgendadasTareaProyecto.getProyecto();
                tareasAgendadasTareaProyecto.setProyecto(proyecto);
                tareasAgendadasTareaProyecto = em.merge(tareasAgendadasTareaProyecto);
                if (oldProyectoOfTareasAgendadasTareaProyecto != null) {
                    oldProyectoOfTareasAgendadasTareaProyecto.getTareasAgendadas().remove(tareasAgendadasTareaProyecto);
                    oldProyectoOfTareasAgendadasTareaProyecto = em.merge(oldProyectoOfTareasAgendadasTareaProyecto);
                }
            }
            for (TareaScmProyecto tareasScmProyectoTareaScmProyecto : proyecto.getTareasScmProyecto()) {
                Proyecto oldProyectoOfTareasScmProyectoTareaScmProyecto = tareasScmProyectoTareaScmProyecto.getProyecto();
                tareasScmProyectoTareaScmProyecto.setProyecto(proyecto);
                tareasScmProyectoTareaScmProyecto = em.merge(tareasScmProyectoTareaScmProyecto);
                if (oldProyectoOfTareasScmProyectoTareaScmProyecto != null) {
                    oldProyectoOfTareasScmProyectoTareaScmProyecto.getTareasScmProyecto().remove(tareasScmProyectoTareaScmProyecto);
                    oldProyectoOfTareasScmProyectoTareaScmProyecto = em.merge(oldProyectoOfTareasScmProyectoTareaScmProyecto);
                }
            }
            for (ParticipanteProyecto participantesParticipanteProyecto : proyecto.getParticipantes()) {
                Proyecto oldProyectoOfParticipantesParticipanteProyecto = participantesParticipanteProyecto.getProyecto();
                participantesParticipanteProyecto.setProyecto(proyecto);
                participantesParticipanteProyecto = em.merge(participantesParticipanteProyecto);
                if (oldProyectoOfParticipantesParticipanteProyecto != null) {
                    oldProyectoOfParticipantesParticipanteProyecto.getParticipantes().remove(participantesParticipanteProyecto);
                    oldProyectoOfParticipantesParticipanteProyecto = em.merge(oldProyectoOfParticipantesParticipanteProyecto);
                }
            }
            for (SolicitudCambio solicitudesDeCambioSolicitudCambio : proyecto.getSolicitudesDeCambio()) {
                Proyecto oldProyectoOfSolicitudesDeCambioSolicitudCambio = solicitudesDeCambioSolicitudCambio.getProyecto();
                solicitudesDeCambioSolicitudCambio.setProyecto(proyecto);
                solicitudesDeCambioSolicitudCambio = em.merge(solicitudesDeCambioSolicitudCambio);
                if (oldProyectoOfSolicitudesDeCambioSolicitudCambio != null) {
                    oldProyectoOfSolicitudesDeCambioSolicitudCambio.getSolicitudesDeCambio().remove(solicitudesDeCambioSolicitudCambio);
                    oldProyectoOfSolicitudesDeCambioSolicitudCambio = em.merge(oldProyectoOfSolicitudesDeCambioSolicitudCambio);
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

    public void edit(Proyecto proyecto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getIdProyecto());
            EstadoProyecto estadoProyectoOld = persistentProyecto.getEstadoProyecto();
            EstadoProyecto estadoProyectoNew = proyecto.getEstadoProyecto();
            TipoProyecto tipoProyectoOld = persistentProyecto.getTipoProyecto();
            TipoProyecto tipoProyectoNew = proyecto.getTipoProyecto();
            List<ItemConfiguracion> itemsDeConfiguracionOld = persistentProyecto.getItemsDeConfiguracion();
            List<ItemConfiguracion> itemsDeConfiguracionNew = proyecto.getItemsDeConfiguracion();
            List<TareaProyecto> tareasAgendadasOld = persistentProyecto.getTareasAgendadas();
            List<TareaProyecto> tareasAgendadasNew = proyecto.getTareasAgendadas();
            List<TareaScmProyecto> tareasScmProyectoOld = persistentProyecto.getTareasScmProyecto();
            List<TareaScmProyecto> tareasScmProyectoNew = proyecto.getTareasScmProyecto();
            List<ParticipanteProyecto> participantesOld = persistentProyecto.getParticipantes();
            List<ParticipanteProyecto> participantesNew = proyecto.getParticipantes();
            List<SolicitudCambio> solicitudesDeCambioOld = persistentProyecto.getSolicitudesDeCambio();
            List<SolicitudCambio> solicitudesDeCambioNew = proyecto.getSolicitudesDeCambio();
            List<String> illegalOrphanMessages = null;
            for (ItemConfiguracion itemsDeConfiguracionOldItemConfiguracion : itemsDeConfiguracionOld) {
                if (!itemsDeConfiguracionNew.contains(itemsDeConfiguracionOldItemConfiguracion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemConfiguracion " + itemsDeConfiguracionOldItemConfiguracion + " since its proyecto field is not nullable.");
                }
            }
            for (TareaProyecto tareasAgendadasOldTareaProyecto : tareasAgendadasOld) {
                if (!tareasAgendadasNew.contains(tareasAgendadasOldTareaProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TareaProyecto " + tareasAgendadasOldTareaProyecto + " since its proyecto field is not nullable.");
                }
            }
            for (TareaScmProyecto tareasScmProyectoOldTareaScmProyecto : tareasScmProyectoOld) {
                if (!tareasScmProyectoNew.contains(tareasScmProyectoOldTareaScmProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TareaScmProyecto " + tareasScmProyectoOldTareaScmProyecto + " since its proyecto field is not nullable.");
                }
            }
            for (ParticipanteProyecto participantesOldParticipanteProyecto : participantesOld) {
                if (!participantesNew.contains(participantesOldParticipanteProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipanteProyecto " + participantesOldParticipanteProyecto + " since its proyecto field is not nullable.");
                }
            }
            for (SolicitudCambio solicitudesDeCambioOldSolicitudCambio : solicitudesDeCambioOld) {
                if (!solicitudesDeCambioNew.contains(solicitudesDeCambioOldSolicitudCambio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudCambio " + solicitudesDeCambioOldSolicitudCambio + " since its proyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadoProyectoNew != null) {
                estadoProyectoNew = em.getReference(estadoProyectoNew.getClass(), estadoProyectoNew.getIdEstadoProyecto());
                proyecto.setEstadoProyecto(estadoProyectoNew);
            }
            if (tipoProyectoNew != null) {
                tipoProyectoNew = em.getReference(tipoProyectoNew.getClass(), tipoProyectoNew.getIdTipoProyecto());
                proyecto.setTipoProyecto(tipoProyectoNew);
            }
            List<ItemConfiguracion> attachedItemsDeConfiguracionNew = new ArrayList<ItemConfiguracion>();
            for (ItemConfiguracion itemsDeConfiguracionNewItemConfiguracionToAttach : itemsDeConfiguracionNew) {
                itemsDeConfiguracionNewItemConfiguracionToAttach = em.getReference(itemsDeConfiguracionNewItemConfiguracionToAttach.getClass(), itemsDeConfiguracionNewItemConfiguracionToAttach.getIdItemConfiguracion());
                attachedItemsDeConfiguracionNew.add(itemsDeConfiguracionNewItemConfiguracionToAttach);
            }
            itemsDeConfiguracionNew = attachedItemsDeConfiguracionNew;
            proyecto.setItemsDeConfiguracion(itemsDeConfiguracionNew);
            List<TareaProyecto> attachedTareasAgendadasNew = new ArrayList<TareaProyecto>();
            for (TareaProyecto tareasAgendadasNewTareaProyectoToAttach : tareasAgendadasNew) {
                tareasAgendadasNewTareaProyectoToAttach = em.getReference(tareasAgendadasNewTareaProyectoToAttach.getClass(), tareasAgendadasNewTareaProyectoToAttach.getIdTareaProyecto());
                attachedTareasAgendadasNew.add(tareasAgendadasNewTareaProyectoToAttach);
            }
            tareasAgendadasNew = attachedTareasAgendadasNew;
            proyecto.setTareasAgendadas(tareasAgendadasNew);
            List<TareaScmProyecto> attachedTareasScmProyectoNew = new ArrayList<TareaScmProyecto>();
            for (TareaScmProyecto tareasScmProyectoNewTareaScmProyectoToAttach : tareasScmProyectoNew) {
                tareasScmProyectoNewTareaScmProyectoToAttach = em.getReference(tareasScmProyectoNewTareaScmProyectoToAttach.getClass(), tareasScmProyectoNewTareaScmProyectoToAttach.getTareaScmProyectoPK());
                attachedTareasScmProyectoNew.add(tareasScmProyectoNewTareaScmProyectoToAttach);
            }
            tareasScmProyectoNew = attachedTareasScmProyectoNew;
            proyecto.setTareasScmProyecto(tareasScmProyectoNew);
            List<ParticipanteProyecto> attachedParticipantesNew = new ArrayList<ParticipanteProyecto>();
            for (ParticipanteProyecto participantesNewParticipanteProyectoToAttach : participantesNew) {
                participantesNewParticipanteProyectoToAttach = em.getReference(participantesNewParticipanteProyectoToAttach.getClass(), participantesNewParticipanteProyectoToAttach.getParticipanteProyectoPK());
                attachedParticipantesNew.add(participantesNewParticipanteProyectoToAttach);
            }
            participantesNew = attachedParticipantesNew;
            proyecto.setParticipantes(participantesNew);
            List<SolicitudCambio> attachedSolicitudesDeCambioNew = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesDeCambioNewSolicitudCambioToAttach : solicitudesDeCambioNew) {
                solicitudesDeCambioNewSolicitudCambioToAttach = em.getReference(solicitudesDeCambioNewSolicitudCambioToAttach.getClass(), solicitudesDeCambioNewSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesDeCambioNew.add(solicitudesDeCambioNewSolicitudCambioToAttach);
            }
            solicitudesDeCambioNew = attachedSolicitudesDeCambioNew;
            proyecto.setSolicitudesDeCambio(solicitudesDeCambioNew);
            proyecto = em.merge(proyecto);
            if (estadoProyectoOld != null && !estadoProyectoOld.equals(estadoProyectoNew)) {
                estadoProyectoOld.getProyectoList().remove(proyecto);
                estadoProyectoOld = em.merge(estadoProyectoOld);
            }
            if (estadoProyectoNew != null && !estadoProyectoNew.equals(estadoProyectoOld)) {
                estadoProyectoNew.getProyectoList().add(proyecto);
                estadoProyectoNew = em.merge(estadoProyectoNew);
            }
            if (tipoProyectoOld != null && !tipoProyectoOld.equals(tipoProyectoNew)) {
                tipoProyectoOld.getProyectoList().remove(proyecto);
                tipoProyectoOld = em.merge(tipoProyectoOld);
            }
            if (tipoProyectoNew != null && !tipoProyectoNew.equals(tipoProyectoOld)) {
                tipoProyectoNew.getProyectoList().add(proyecto);
                tipoProyectoNew = em.merge(tipoProyectoNew);
            }
            for (ItemConfiguracion itemsDeConfiguracionNewItemConfiguracion : itemsDeConfiguracionNew) {
                if (!itemsDeConfiguracionOld.contains(itemsDeConfiguracionNewItemConfiguracion)) {
                    Proyecto oldProyectoOfItemsDeConfiguracionNewItemConfiguracion = itemsDeConfiguracionNewItemConfiguracion.getProyecto();
                    itemsDeConfiguracionNewItemConfiguracion.setProyecto(proyecto);
                    itemsDeConfiguracionNewItemConfiguracion = em.merge(itemsDeConfiguracionNewItemConfiguracion);
                    if (oldProyectoOfItemsDeConfiguracionNewItemConfiguracion != null && !oldProyectoOfItemsDeConfiguracionNewItemConfiguracion.equals(proyecto)) {
                        oldProyectoOfItemsDeConfiguracionNewItemConfiguracion.getItemsDeConfiguracion().remove(itemsDeConfiguracionNewItemConfiguracion);
                        oldProyectoOfItemsDeConfiguracionNewItemConfiguracion = em.merge(oldProyectoOfItemsDeConfiguracionNewItemConfiguracion);
                    }
                }
            }
            for (TareaProyecto tareasAgendadasNewTareaProyecto : tareasAgendadasNew) {
                if (!tareasAgendadasOld.contains(tareasAgendadasNewTareaProyecto)) {
                    Proyecto oldProyectoOfTareasAgendadasNewTareaProyecto = tareasAgendadasNewTareaProyecto.getProyecto();
                    tareasAgendadasNewTareaProyecto.setProyecto(proyecto);
                    tareasAgendadasNewTareaProyecto = em.merge(tareasAgendadasNewTareaProyecto);
                    if (oldProyectoOfTareasAgendadasNewTareaProyecto != null && !oldProyectoOfTareasAgendadasNewTareaProyecto.equals(proyecto)) {
                        oldProyectoOfTareasAgendadasNewTareaProyecto.getTareasAgendadas().remove(tareasAgendadasNewTareaProyecto);
                        oldProyectoOfTareasAgendadasNewTareaProyecto = em.merge(oldProyectoOfTareasAgendadasNewTareaProyecto);
                    }
                }
            }
            for (TareaScmProyecto tareasScmProyectoNewTareaScmProyecto : tareasScmProyectoNew) {
                if (!tareasScmProyectoOld.contains(tareasScmProyectoNewTareaScmProyecto)) {
                    Proyecto oldProyectoOfTareasScmProyectoNewTareaScmProyecto = tareasScmProyectoNewTareaScmProyecto.getProyecto();
                    tareasScmProyectoNewTareaScmProyecto.setProyecto(proyecto);
                    tareasScmProyectoNewTareaScmProyecto = em.merge(tareasScmProyectoNewTareaScmProyecto);
                    if (oldProyectoOfTareasScmProyectoNewTareaScmProyecto != null && !oldProyectoOfTareasScmProyectoNewTareaScmProyecto.equals(proyecto)) {
                        oldProyectoOfTareasScmProyectoNewTareaScmProyecto.getTareasScmProyecto().remove(tareasScmProyectoNewTareaScmProyecto);
                        oldProyectoOfTareasScmProyectoNewTareaScmProyecto = em.merge(oldProyectoOfTareasScmProyectoNewTareaScmProyecto);
                    }
                }
            }
            for (ParticipanteProyecto participantesNewParticipanteProyecto : participantesNew) {
                if (!participantesOld.contains(participantesNewParticipanteProyecto)) {
                    Proyecto oldProyectoOfParticipantesNewParticipanteProyecto = participantesNewParticipanteProyecto.getProyecto();
                    participantesNewParticipanteProyecto.setProyecto(proyecto);
                    participantesNewParticipanteProyecto = em.merge(participantesNewParticipanteProyecto);
                    if (oldProyectoOfParticipantesNewParticipanteProyecto != null && !oldProyectoOfParticipantesNewParticipanteProyecto.equals(proyecto)) {
                        oldProyectoOfParticipantesNewParticipanteProyecto.getParticipantes().remove(participantesNewParticipanteProyecto);
                        oldProyectoOfParticipantesNewParticipanteProyecto = em.merge(oldProyectoOfParticipantesNewParticipanteProyecto);
                    }
                }
            }
            for (SolicitudCambio solicitudesDeCambioNewSolicitudCambio : solicitudesDeCambioNew) {
                if (!solicitudesDeCambioOld.contains(solicitudesDeCambioNewSolicitudCambio)) {
                    Proyecto oldProyectoOfSolicitudesDeCambioNewSolicitudCambio = solicitudesDeCambioNewSolicitudCambio.getProyecto();
                    solicitudesDeCambioNewSolicitudCambio.setProyecto(proyecto);
                    solicitudesDeCambioNewSolicitudCambio = em.merge(solicitudesDeCambioNewSolicitudCambio);
                    if (oldProyectoOfSolicitudesDeCambioNewSolicitudCambio != null && !oldProyectoOfSolicitudesDeCambioNewSolicitudCambio.equals(proyecto)) {
                        oldProyectoOfSolicitudesDeCambioNewSolicitudCambio.getSolicitudesDeCambio().remove(solicitudesDeCambioNewSolicitudCambio);
                        oldProyectoOfSolicitudesDeCambioNewSolicitudCambio = em.merge(oldProyectoOfSolicitudesDeCambioNewSolicitudCambio);
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
                Integer id = proyecto.getIdProyecto();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getIdProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemConfiguracion> itemsDeConfiguracionOrphanCheck = proyecto.getItemsDeConfiguracion();
            for (ItemConfiguracion itemsDeConfiguracionOrphanCheckItemConfiguracion : itemsDeConfiguracionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the ItemConfiguracion " + itemsDeConfiguracionOrphanCheckItemConfiguracion + " in its itemsDeConfiguracion field has a non-nullable proyecto field.");
            }
            List<TareaProyecto> tareasAgendadasOrphanCheck = proyecto.getTareasAgendadas();
            for (TareaProyecto tareasAgendadasOrphanCheckTareaProyecto : tareasAgendadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the TareaProyecto " + tareasAgendadasOrphanCheckTareaProyecto + " in its tareasAgendadas field has a non-nullable proyecto field.");
            }
            List<TareaScmProyecto> tareasScmProyectoOrphanCheck = proyecto.getTareasScmProyecto();
            for (TareaScmProyecto tareasScmProyectoOrphanCheckTareaScmProyecto : tareasScmProyectoOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the TareaScmProyecto " + tareasScmProyectoOrphanCheckTareaScmProyecto + " in its tareasScmProyecto field has a non-nullable proyecto field.");
            }
            List<ParticipanteProyecto> participantesOrphanCheck = proyecto.getParticipantes();
            for (ParticipanteProyecto participantesOrphanCheckParticipanteProyecto : participantesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the ParticipanteProyecto " + participantesOrphanCheckParticipanteProyecto + " in its participantes field has a non-nullable proyecto field.");
            }
            List<SolicitudCambio> solicitudesDeCambioOrphanCheck = proyecto.getSolicitudesDeCambio();
            for (SolicitudCambio solicitudesDeCambioOrphanCheckSolicitudCambio : solicitudesDeCambioOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the SolicitudCambio " + solicitudesDeCambioOrphanCheckSolicitudCambio + " in its solicitudesDeCambio field has a non-nullable proyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EstadoProyecto estadoProyecto = proyecto.getEstadoProyecto();
            if (estadoProyecto != null) {
                estadoProyecto.getProyectoList().remove(proyecto);
                estadoProyecto = em.merge(estadoProyecto);
            }
            TipoProyecto tipoProyecto = proyecto.getTipoProyecto();
            if (tipoProyecto != null) {
                tipoProyecto.getProyectoList().remove(proyecto);
                tipoProyecto = em.merge(tipoProyecto);
            }
            em.remove(proyecto);
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

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
