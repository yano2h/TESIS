/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.controladores;

import cl.uv.proyecto.persistencia.controladores.exceptions.IllegalOrphanException;
import cl.uv.proyecto.persistencia.controladores.exceptions.NonexistentEntityException;
import cl.uv.proyecto.persistencia.controladores.exceptions.PreexistingEntityException;
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
public class FuncionarioDisicoJpaController implements Serializable {

    public FuncionarioDisicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FuncionarioDisico funcionarioDisico) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (funcionarioDisico.getItemsConfiguracionAcargo() == null) {
            funcionarioDisico.setItemsConfiguracionAcargo(new ArrayList<ItemConfiguracion>());
        }
        if (funcionarioDisico.getTareasProyectoAgendadas() == null) {
            funcionarioDisico.setTareasProyectoAgendadas(new ArrayList<TareaProyecto>());
        }
        if (funcionarioDisico.getEstadisticasPersonales() == null) {
            funcionarioDisico.setEstadisticasPersonales(new ArrayList<EstadisticaPersonal>());
        }
        if (funcionarioDisico.getSolicitudesRequerimientosAsignadas() == null) {
            funcionarioDisico.setSolicitudesRequerimientosAsignadas(new ArrayList<SolicitudRequerimiento>());
        }
        if (funcionarioDisico.getTareasScmAsignadas() == null) {
            funcionarioDisico.setTareasScmAsignadas(new ArrayList<TareaScmProyecto>());
        }
        if (funcionarioDisico.getParticipacionesProyecto() == null) {
            funcionarioDisico.setParticipacionesProyecto(new ArrayList<ParticipanteProyecto>());
        }
        if (funcionarioDisico.getImplementadorForlumarios() == null) {
            funcionarioDisico.setImplementadorForlumarios(new ArrayList<FormularioImplementacion>());
        }
        if (funcionarioDisico.getVerificadorFormularios() == null) {
            funcionarioDisico.setVerificadorFormularios(new ArrayList<FormularioImplementacion>());
        }
        if (funcionarioDisico.getSolicitudesCambioEvaluadorFinal() == null) {
            funcionarioDisico.setSolicitudesCambioEvaluadorFinal(new ArrayList<SolicitudCambio>());
        }
        if (funcionarioDisico.getSolicitudesCambioEvaluadorImpacto() == null) {
            funcionarioDisico.setSolicitudesCambioEvaluadorImpacto(new ArrayList<SolicitudCambio>());
        }
        if (funcionarioDisico.getSolicitudesCambioEnviadas() == null) {
            funcionarioDisico.setSolicitudesCambioEnviadas(new ArrayList<SolicitudCambio>());
        }
        if (funcionarioDisico.getNotificaciones() == null) {
            funcionarioDisico.setNotificaciones(new ArrayList<Notificacion>());
        }
        if (funcionarioDisico.getSolicitudesRequerimientoEnviadas() == null) {
            funcionarioDisico.setSolicitudesRequerimientoEnviadas(new ArrayList<SolicitudRequerimiento>());
        }
        if (funcionarioDisico.getComentarioSolicitudList() == null) {
            funcionarioDisico.setComentarioSolicitudList(new ArrayList<ComentarioSolicitud>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Area area = funcionarioDisico.getArea();
            if (area != null) {
                area = em.getReference(area.getClass(), area.getIdArea());
                funcionarioDisico.setArea(area);
            }
            List<ItemConfiguracion> attachedItemsConfiguracionAcargo = new ArrayList<ItemConfiguracion>();
            for (ItemConfiguracion itemsConfiguracionAcargoItemConfiguracionToAttach : funcionarioDisico.getItemsConfiguracionAcargo()) {
                itemsConfiguracionAcargoItemConfiguracionToAttach = em.getReference(itemsConfiguracionAcargoItemConfiguracionToAttach.getClass(), itemsConfiguracionAcargoItemConfiguracionToAttach.getIdItemConfiguracion());
                attachedItemsConfiguracionAcargo.add(itemsConfiguracionAcargoItemConfiguracionToAttach);
            }
            funcionarioDisico.setItemsConfiguracionAcargo(attachedItemsConfiguracionAcargo);
            List<TareaProyecto> attachedTareasProyectoAgendadas = new ArrayList<TareaProyecto>();
            for (TareaProyecto tareasProyectoAgendadasTareaProyectoToAttach : funcionarioDisico.getTareasProyectoAgendadas()) {
                tareasProyectoAgendadasTareaProyectoToAttach = em.getReference(tareasProyectoAgendadasTareaProyectoToAttach.getClass(), tareasProyectoAgendadasTareaProyectoToAttach.getIdTareaProyecto());
                attachedTareasProyectoAgendadas.add(tareasProyectoAgendadasTareaProyectoToAttach);
            }
            funcionarioDisico.setTareasProyectoAgendadas(attachedTareasProyectoAgendadas);
            List<EstadisticaPersonal> attachedEstadisticasPersonales = new ArrayList<EstadisticaPersonal>();
            for (EstadisticaPersonal estadisticasPersonalesEstadisticaPersonalToAttach : funcionarioDisico.getEstadisticasPersonales()) {
                estadisticasPersonalesEstadisticaPersonalToAttach = em.getReference(estadisticasPersonalesEstadisticaPersonalToAttach.getClass(), estadisticasPersonalesEstadisticaPersonalToAttach.getId());
                attachedEstadisticasPersonales.add(estadisticasPersonalesEstadisticaPersonalToAttach);
            }
            funcionarioDisico.setEstadisticasPersonales(attachedEstadisticasPersonales);
            List<SolicitudRequerimiento> attachedSolicitudesRequerimientosAsignadas = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudesRequerimientosAsignadasSolicitudRequerimientoToAttach : funcionarioDisico.getSolicitudesRequerimientosAsignadas()) {
                solicitudesRequerimientosAsignadasSolicitudRequerimientoToAttach = em.getReference(solicitudesRequerimientosAsignadasSolicitudRequerimientoToAttach.getClass(), solicitudesRequerimientosAsignadasSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudesRequerimientosAsignadas.add(solicitudesRequerimientosAsignadasSolicitudRequerimientoToAttach);
            }
            funcionarioDisico.setSolicitudesRequerimientosAsignadas(attachedSolicitudesRequerimientosAsignadas);
            List<TareaScmProyecto> attachedTareasScmAsignadas = new ArrayList<TareaScmProyecto>();
            for (TareaScmProyecto tareasScmAsignadasTareaScmProyectoToAttach : funcionarioDisico.getTareasScmAsignadas()) {
                tareasScmAsignadasTareaScmProyectoToAttach = em.getReference(tareasScmAsignadasTareaScmProyectoToAttach.getClass(), tareasScmAsignadasTareaScmProyectoToAttach.getTareaScmProyectoPK());
                attachedTareasScmAsignadas.add(tareasScmAsignadasTareaScmProyectoToAttach);
            }
            funcionarioDisico.setTareasScmAsignadas(attachedTareasScmAsignadas);
            List<ParticipanteProyecto> attachedParticipacionesProyecto = new ArrayList<ParticipanteProyecto>();
            for (ParticipanteProyecto participacionesProyectoParticipanteProyectoToAttach : funcionarioDisico.getParticipacionesProyecto()) {
                participacionesProyectoParticipanteProyectoToAttach = em.getReference(participacionesProyectoParticipanteProyectoToAttach.getClass(), participacionesProyectoParticipanteProyectoToAttach.getParticipanteProyectoPK());
                attachedParticipacionesProyecto.add(participacionesProyectoParticipanteProyectoToAttach);
            }
            funcionarioDisico.setParticipacionesProyecto(attachedParticipacionesProyecto);
            List<FormularioImplementacion> attachedImplementadorForlumarios = new ArrayList<FormularioImplementacion>();
            for (FormularioImplementacion implementadorForlumariosFormularioImplementacionToAttach : funcionarioDisico.getImplementadorForlumarios()) {
                implementadorForlumariosFormularioImplementacionToAttach = em.getReference(implementadorForlumariosFormularioImplementacionToAttach.getClass(), implementadorForlumariosFormularioImplementacionToAttach.getIdFormularioImplementacion());
                attachedImplementadorForlumarios.add(implementadorForlumariosFormularioImplementacionToAttach);
            }
            funcionarioDisico.setImplementadorForlumarios(attachedImplementadorForlumarios);
            List<FormularioImplementacion> attachedVerificadorFormularios = new ArrayList<FormularioImplementacion>();
            for (FormularioImplementacion verificadorFormulariosFormularioImplementacionToAttach : funcionarioDisico.getVerificadorFormularios()) {
                verificadorFormulariosFormularioImplementacionToAttach = em.getReference(verificadorFormulariosFormularioImplementacionToAttach.getClass(), verificadorFormulariosFormularioImplementacionToAttach.getIdFormularioImplementacion());
                attachedVerificadorFormularios.add(verificadorFormulariosFormularioImplementacionToAttach);
            }
            funcionarioDisico.setVerificadorFormularios(attachedVerificadorFormularios);
            List<SolicitudCambio> attachedSolicitudesCambioEvaluadorFinal = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesCambioEvaluadorFinalSolicitudCambioToAttach : funcionarioDisico.getSolicitudesCambioEvaluadorFinal()) {
                solicitudesCambioEvaluadorFinalSolicitudCambioToAttach = em.getReference(solicitudesCambioEvaluadorFinalSolicitudCambioToAttach.getClass(), solicitudesCambioEvaluadorFinalSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesCambioEvaluadorFinal.add(solicitudesCambioEvaluadorFinalSolicitudCambioToAttach);
            }
            funcionarioDisico.setSolicitudesCambioEvaluadorFinal(attachedSolicitudesCambioEvaluadorFinal);
            List<SolicitudCambio> attachedSolicitudesCambioEvaluadorImpacto = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesCambioEvaluadorImpactoSolicitudCambioToAttach : funcionarioDisico.getSolicitudesCambioEvaluadorImpacto()) {
                solicitudesCambioEvaluadorImpactoSolicitudCambioToAttach = em.getReference(solicitudesCambioEvaluadorImpactoSolicitudCambioToAttach.getClass(), solicitudesCambioEvaluadorImpactoSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesCambioEvaluadorImpacto.add(solicitudesCambioEvaluadorImpactoSolicitudCambioToAttach);
            }
            funcionarioDisico.setSolicitudesCambioEvaluadorImpacto(attachedSolicitudesCambioEvaluadorImpacto);
            List<SolicitudCambio> attachedSolicitudesCambioEnviadas = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesCambioEnviadasSolicitudCambioToAttach : funcionarioDisico.getSolicitudesCambioEnviadas()) {
                solicitudesCambioEnviadasSolicitudCambioToAttach = em.getReference(solicitudesCambioEnviadasSolicitudCambioToAttach.getClass(), solicitudesCambioEnviadasSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesCambioEnviadas.add(solicitudesCambioEnviadasSolicitudCambioToAttach);
            }
            funcionarioDisico.setSolicitudesCambioEnviadas(attachedSolicitudesCambioEnviadas);
            List<Notificacion> attachedNotificaciones = new ArrayList<Notificacion>();
            for (Notificacion notificacionesNotificacionToAttach : funcionarioDisico.getNotificaciones()) {
                notificacionesNotificacionToAttach = em.getReference(notificacionesNotificacionToAttach.getClass(), notificacionesNotificacionToAttach.getIdNotificacion());
                attachedNotificaciones.add(notificacionesNotificacionToAttach);
            }
            funcionarioDisico.setNotificaciones(attachedNotificaciones);
            List<SolicitudRequerimiento> attachedSolicitudesRequerimientoEnviadas = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach : funcionarioDisico.getSolicitudesRequerimientoEnviadas()) {
                solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach = em.getReference(solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach.getClass(), solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudesRequerimientoEnviadas.add(solicitudesRequerimientoEnviadasSolicitudRequerimientoToAttach);
            }
            funcionarioDisico.setSolicitudesRequerimientoEnviadas(attachedSolicitudesRequerimientoEnviadas);
            List<ComentarioSolicitud> attachedComentarioSolicitudList = new ArrayList<ComentarioSolicitud>();
            for (ComentarioSolicitud comentarioSolicitudListComentarioSolicitudToAttach : funcionarioDisico.getComentarioSolicitudList()) {
                comentarioSolicitudListComentarioSolicitudToAttach = em.getReference(comentarioSolicitudListComentarioSolicitudToAttach.getClass(), comentarioSolicitudListComentarioSolicitudToAttach.getIdComentario());
                attachedComentarioSolicitudList.add(comentarioSolicitudListComentarioSolicitudToAttach);
            }
            funcionarioDisico.setComentarioSolicitudList(attachedComentarioSolicitudList);
            em.persist(funcionarioDisico);
            if (area != null) {
                area.getFuncionarioDisicoList().add(funcionarioDisico);
                area = em.merge(area);
            }
            for (ItemConfiguracion itemsConfiguracionAcargoItemConfiguracion : funcionarioDisico.getItemsConfiguracionAcargo()) {
                FuncionarioDisico oldResponsableItemOfItemsConfiguracionAcargoItemConfiguracion = itemsConfiguracionAcargoItemConfiguracion.getResponsableItem();
                itemsConfiguracionAcargoItemConfiguracion.setResponsableItem(funcionarioDisico);
                itemsConfiguracionAcargoItemConfiguracion = em.merge(itemsConfiguracionAcargoItemConfiguracion);
                if (oldResponsableItemOfItemsConfiguracionAcargoItemConfiguracion != null) {
                    oldResponsableItemOfItemsConfiguracionAcargoItemConfiguracion.getItemsConfiguracionAcargo().remove(itemsConfiguracionAcargoItemConfiguracion);
                    oldResponsableItemOfItemsConfiguracionAcargoItemConfiguracion = em.merge(oldResponsableItemOfItemsConfiguracionAcargoItemConfiguracion);
                }
            }
            for (TareaProyecto tareasProyectoAgendadasTareaProyecto : funcionarioDisico.getTareasProyectoAgendadas()) {
                FuncionarioDisico oldResponsableTareaOfTareasProyectoAgendadasTareaProyecto = tareasProyectoAgendadasTareaProyecto.getResponsableTarea();
                tareasProyectoAgendadasTareaProyecto.setResponsableTarea(funcionarioDisico);
                tareasProyectoAgendadasTareaProyecto = em.merge(tareasProyectoAgendadasTareaProyecto);
                if (oldResponsableTareaOfTareasProyectoAgendadasTareaProyecto != null) {
                    oldResponsableTareaOfTareasProyectoAgendadasTareaProyecto.getTareasProyectoAgendadas().remove(tareasProyectoAgendadasTareaProyecto);
                    oldResponsableTareaOfTareasProyectoAgendadasTareaProyecto = em.merge(oldResponsableTareaOfTareasProyectoAgendadasTareaProyecto);
                }
            }
            for (EstadisticaPersonal estadisticasPersonalesEstadisticaPersonal : funcionarioDisico.getEstadisticasPersonales()) {
                FuncionarioDisico oldFuncionarioDisicoOfEstadisticasPersonalesEstadisticaPersonal = estadisticasPersonalesEstadisticaPersonal.getFuncionarioDisico();
                estadisticasPersonalesEstadisticaPersonal.setFuncionarioDisico(funcionarioDisico);
                estadisticasPersonalesEstadisticaPersonal = em.merge(estadisticasPersonalesEstadisticaPersonal);
                if (oldFuncionarioDisicoOfEstadisticasPersonalesEstadisticaPersonal != null) {
                    oldFuncionarioDisicoOfEstadisticasPersonalesEstadisticaPersonal.getEstadisticasPersonales().remove(estadisticasPersonalesEstadisticaPersonal);
                    oldFuncionarioDisicoOfEstadisticasPersonalesEstadisticaPersonal = em.merge(oldFuncionarioDisicoOfEstadisticasPersonalesEstadisticaPersonal);
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientosAsignadasSolicitudRequerimiento : funcionarioDisico.getSolicitudesRequerimientosAsignadas()) {
                FuncionarioDisico oldResponsableOfSolicitudesRequerimientosAsignadasSolicitudRequerimiento = solicitudesRequerimientosAsignadasSolicitudRequerimiento.getResponsable();
                solicitudesRequerimientosAsignadasSolicitudRequerimiento.setResponsable(funcionarioDisico);
                solicitudesRequerimientosAsignadasSolicitudRequerimiento = em.merge(solicitudesRequerimientosAsignadasSolicitudRequerimiento);
                if (oldResponsableOfSolicitudesRequerimientosAsignadasSolicitudRequerimiento != null) {
                    oldResponsableOfSolicitudesRequerimientosAsignadasSolicitudRequerimiento.getSolicitudesRequerimientosAsignadas().remove(solicitudesRequerimientosAsignadasSolicitudRequerimiento);
                    oldResponsableOfSolicitudesRequerimientosAsignadasSolicitudRequerimiento = em.merge(oldResponsableOfSolicitudesRequerimientosAsignadasSolicitudRequerimiento);
                }
            }
            for (TareaScmProyecto tareasScmAsignadasTareaScmProyecto : funcionarioDisico.getTareasScmAsignadas()) {
                FuncionarioDisico oldResponsableOfTareasScmAsignadasTareaScmProyecto = tareasScmAsignadasTareaScmProyecto.getResponsable();
                tareasScmAsignadasTareaScmProyecto.setResponsable(funcionarioDisico);
                tareasScmAsignadasTareaScmProyecto = em.merge(tareasScmAsignadasTareaScmProyecto);
                if (oldResponsableOfTareasScmAsignadasTareaScmProyecto != null) {
                    oldResponsableOfTareasScmAsignadasTareaScmProyecto.getTareasScmAsignadas().remove(tareasScmAsignadasTareaScmProyecto);
                    oldResponsableOfTareasScmAsignadasTareaScmProyecto = em.merge(oldResponsableOfTareasScmAsignadasTareaScmProyecto);
                }
            }
            for (ParticipanteProyecto participacionesProyectoParticipanteProyecto : funcionarioDisico.getParticipacionesProyecto()) {
                FuncionarioDisico oldParticipanteOfParticipacionesProyectoParticipanteProyecto = participacionesProyectoParticipanteProyecto.getParticipante();
                participacionesProyectoParticipanteProyecto.setParticipante(funcionarioDisico);
                participacionesProyectoParticipanteProyecto = em.merge(participacionesProyectoParticipanteProyecto);
                if (oldParticipanteOfParticipacionesProyectoParticipanteProyecto != null) {
                    oldParticipanteOfParticipacionesProyectoParticipanteProyecto.getParticipacionesProyecto().remove(participacionesProyectoParticipanteProyecto);
                    oldParticipanteOfParticipacionesProyectoParticipanteProyecto = em.merge(oldParticipanteOfParticipacionesProyectoParticipanteProyecto);
                }
            }
            for (FormularioImplementacion implementadorForlumariosFormularioImplementacion : funcionarioDisico.getImplementadorForlumarios()) {
                FuncionarioDisico oldImplementadorOfImplementadorForlumariosFormularioImplementacion = implementadorForlumariosFormularioImplementacion.getImplementador();
                implementadorForlumariosFormularioImplementacion.setImplementador(funcionarioDisico);
                implementadorForlumariosFormularioImplementacion = em.merge(implementadorForlumariosFormularioImplementacion);
                if (oldImplementadorOfImplementadorForlumariosFormularioImplementacion != null) {
                    oldImplementadorOfImplementadorForlumariosFormularioImplementacion.getImplementadorForlumarios().remove(implementadorForlumariosFormularioImplementacion);
                    oldImplementadorOfImplementadorForlumariosFormularioImplementacion = em.merge(oldImplementadorOfImplementadorForlumariosFormularioImplementacion);
                }
            }
            for (FormularioImplementacion verificadorFormulariosFormularioImplementacion : funcionarioDisico.getVerificadorFormularios()) {
                FuncionarioDisico oldVerificadorOfVerificadorFormulariosFormularioImplementacion = verificadorFormulariosFormularioImplementacion.getVerificador();
                verificadorFormulariosFormularioImplementacion.setVerificador(funcionarioDisico);
                verificadorFormulariosFormularioImplementacion = em.merge(verificadorFormulariosFormularioImplementacion);
                if (oldVerificadorOfVerificadorFormulariosFormularioImplementacion != null) {
                    oldVerificadorOfVerificadorFormulariosFormularioImplementacion.getVerificadorFormularios().remove(verificadorFormulariosFormularioImplementacion);
                    oldVerificadorOfVerificadorFormulariosFormularioImplementacion = em.merge(oldVerificadorOfVerificadorFormulariosFormularioImplementacion);
                }
            }
            for (SolicitudCambio solicitudesCambioEvaluadorFinalSolicitudCambio : funcionarioDisico.getSolicitudesCambioEvaluadorFinal()) {
                FuncionarioDisico oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalSolicitudCambio = solicitudesCambioEvaluadorFinalSolicitudCambio.getEvaluadorFinal();
                solicitudesCambioEvaluadorFinalSolicitudCambio.setEvaluadorFinal(funcionarioDisico);
                solicitudesCambioEvaluadorFinalSolicitudCambio = em.merge(solicitudesCambioEvaluadorFinalSolicitudCambio);
                if (oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalSolicitudCambio != null) {
                    oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalSolicitudCambio.getSolicitudesCambioEvaluadorFinal().remove(solicitudesCambioEvaluadorFinalSolicitudCambio);
                    oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalSolicitudCambio = em.merge(oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalSolicitudCambio);
                }
            }
            for (SolicitudCambio solicitudesCambioEvaluadorImpactoSolicitudCambio : funcionarioDisico.getSolicitudesCambioEvaluadorImpacto()) {
                FuncionarioDisico oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoSolicitudCambio = solicitudesCambioEvaluadorImpactoSolicitudCambio.getEvaluadorImpacto();
                solicitudesCambioEvaluadorImpactoSolicitudCambio.setEvaluadorImpacto(funcionarioDisico);
                solicitudesCambioEvaluadorImpactoSolicitudCambio = em.merge(solicitudesCambioEvaluadorImpactoSolicitudCambio);
                if (oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoSolicitudCambio != null) {
                    oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoSolicitudCambio.getSolicitudesCambioEvaluadorImpacto().remove(solicitudesCambioEvaluadorImpactoSolicitudCambio);
                    oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoSolicitudCambio = em.merge(oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoSolicitudCambio);
                }
            }
            for (SolicitudCambio solicitudesCambioEnviadasSolicitudCambio : funcionarioDisico.getSolicitudesCambioEnviadas()) {
                FuncionarioDisico oldSolicitanteOfSolicitudesCambioEnviadasSolicitudCambio = solicitudesCambioEnviadasSolicitudCambio.getSolicitante();
                solicitudesCambioEnviadasSolicitudCambio.setSolicitante(funcionarioDisico);
                solicitudesCambioEnviadasSolicitudCambio = em.merge(solicitudesCambioEnviadasSolicitudCambio);
                if (oldSolicitanteOfSolicitudesCambioEnviadasSolicitudCambio != null) {
                    oldSolicitanteOfSolicitudesCambioEnviadasSolicitudCambio.getSolicitudesCambioEnviadas().remove(solicitudesCambioEnviadasSolicitudCambio);
                    oldSolicitanteOfSolicitudesCambioEnviadasSolicitudCambio = em.merge(oldSolicitanteOfSolicitudesCambioEnviadasSolicitudCambio);
                }
            }
            for (Notificacion notificacionesNotificacion : funcionarioDisico.getNotificaciones()) {
                cl.uv.proyecto.persistencia.entidades.Funcionario oldDestinatarioOfNotificacionesNotificacion = notificacionesNotificacion.getDestinatario();
                notificacionesNotificacion.setDestinatario(funcionarioDisico);
                notificacionesNotificacion = em.merge(notificacionesNotificacion);
                if (oldDestinatarioOfNotificacionesNotificacion != null) {
                    oldDestinatarioOfNotificacionesNotificacion.getNotificaciones().remove(notificacionesNotificacion);
                    oldDestinatarioOfNotificacionesNotificacion = em.merge(oldDestinatarioOfNotificacionesNotificacion);
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasSolicitudRequerimiento : funcionarioDisico.getSolicitudesRequerimientoEnviadas()) {
                cl.uv.proyecto.persistencia.entidades.Funcionario oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento = solicitudesRequerimientoEnviadasSolicitudRequerimiento.getSolicitante();
                solicitudesRequerimientoEnviadasSolicitudRequerimiento.setSolicitante(funcionarioDisico);
                solicitudesRequerimientoEnviadasSolicitudRequerimiento = em.merge(solicitudesRequerimientoEnviadasSolicitudRequerimiento);
                if (oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento != null) {
                    oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento.getSolicitudesRequerimientoEnviadas().remove(solicitudesRequerimientoEnviadasSolicitudRequerimiento);
                    oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento = em.merge(oldSolicitanteOfSolicitudesRequerimientoEnviadasSolicitudRequerimiento);
                }
            }
            for (ComentarioSolicitud comentarioSolicitudListComentarioSolicitud : funcionarioDisico.getComentarioSolicitudList()) {
                cl.uv.proyecto.persistencia.entidades.Funcionario oldAutorOfComentarioSolicitudListComentarioSolicitud = comentarioSolicitudListComentarioSolicitud.getAutor();
                comentarioSolicitudListComentarioSolicitud.setAutor(funcionarioDisico);
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
            if (findFuncionarioDisico(funcionarioDisico.getRut()) != null) {
                throw new PreexistingEntityException("FuncionarioDisico " + funcionarioDisico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FuncionarioDisico funcionarioDisico) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FuncionarioDisico persistentFuncionarioDisico = em.find(FuncionarioDisico.class, funcionarioDisico.getRut());
            Area areaOld = persistentFuncionarioDisico.getArea();
            Area areaNew = funcionarioDisico.getArea();
            List<ItemConfiguracion> itemsConfiguracionAcargoOld = persistentFuncionarioDisico.getItemsConfiguracionAcargo();
            List<ItemConfiguracion> itemsConfiguracionAcargoNew = funcionarioDisico.getItemsConfiguracionAcargo();
            List<TareaProyecto> tareasProyectoAgendadasOld = persistentFuncionarioDisico.getTareasProyectoAgendadas();
            List<TareaProyecto> tareasProyectoAgendadasNew = funcionarioDisico.getTareasProyectoAgendadas();
            List<EstadisticaPersonal> estadisticasPersonalesOld = persistentFuncionarioDisico.getEstadisticasPersonales();
            List<EstadisticaPersonal> estadisticasPersonalesNew = funcionarioDisico.getEstadisticasPersonales();
            List<SolicitudRequerimiento> solicitudesRequerimientosAsignadasOld = persistentFuncionarioDisico.getSolicitudesRequerimientosAsignadas();
            List<SolicitudRequerimiento> solicitudesRequerimientosAsignadasNew = funcionarioDisico.getSolicitudesRequerimientosAsignadas();
            List<TareaScmProyecto> tareasScmAsignadasOld = persistentFuncionarioDisico.getTareasScmAsignadas();
            List<TareaScmProyecto> tareasScmAsignadasNew = funcionarioDisico.getTareasScmAsignadas();
            List<ParticipanteProyecto> participacionesProyectoOld = persistentFuncionarioDisico.getParticipacionesProyecto();
            List<ParticipanteProyecto> participacionesProyectoNew = funcionarioDisico.getParticipacionesProyecto();
            List<FormularioImplementacion> implementadorForlumariosOld = persistentFuncionarioDisico.getImplementadorForlumarios();
            List<FormularioImplementacion> implementadorForlumariosNew = funcionarioDisico.getImplementadorForlumarios();
            List<FormularioImplementacion> verificadorFormulariosOld = persistentFuncionarioDisico.getVerificadorFormularios();
            List<FormularioImplementacion> verificadorFormulariosNew = funcionarioDisico.getVerificadorFormularios();
            List<SolicitudCambio> solicitudesCambioEvaluadorFinalOld = persistentFuncionarioDisico.getSolicitudesCambioEvaluadorFinal();
            List<SolicitudCambio> solicitudesCambioEvaluadorFinalNew = funcionarioDisico.getSolicitudesCambioEvaluadorFinal();
            List<SolicitudCambio> solicitudesCambioEvaluadorImpactoOld = persistentFuncionarioDisico.getSolicitudesCambioEvaluadorImpacto();
            List<SolicitudCambio> solicitudesCambioEvaluadorImpactoNew = funcionarioDisico.getSolicitudesCambioEvaluadorImpacto();
            List<SolicitudCambio> solicitudesCambioEnviadasOld = persistentFuncionarioDisico.getSolicitudesCambioEnviadas();
            List<SolicitudCambio> solicitudesCambioEnviadasNew = funcionarioDisico.getSolicitudesCambioEnviadas();
            List<Notificacion> notificacionesOld = persistentFuncionarioDisico.getNotificaciones();
            List<Notificacion> notificacionesNew = funcionarioDisico.getNotificaciones();
            List<SolicitudRequerimiento> solicitudesRequerimientoEnviadasOld = persistentFuncionarioDisico.getSolicitudesRequerimientoEnviadas();
            List<SolicitudRequerimiento> solicitudesRequerimientoEnviadasNew = funcionarioDisico.getSolicitudesRequerimientoEnviadas();
            List<ComentarioSolicitud> comentarioSolicitudListOld = persistentFuncionarioDisico.getComentarioSolicitudList();
            List<ComentarioSolicitud> comentarioSolicitudListNew = funcionarioDisico.getComentarioSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (ItemConfiguracion itemsConfiguracionAcargoOldItemConfiguracion : itemsConfiguracionAcargoOld) {
                if (!itemsConfiguracionAcargoNew.contains(itemsConfiguracionAcargoOldItemConfiguracion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemConfiguracion " + itemsConfiguracionAcargoOldItemConfiguracion + " since its responsableItem field is not nullable.");
                }
            }
            for (TareaProyecto tareasProyectoAgendadasOldTareaProyecto : tareasProyectoAgendadasOld) {
                if (!tareasProyectoAgendadasNew.contains(tareasProyectoAgendadasOldTareaProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TareaProyecto " + tareasProyectoAgendadasOldTareaProyecto + " since its responsableTarea field is not nullable.");
                }
            }
            for (EstadisticaPersonal estadisticasPersonalesOldEstadisticaPersonal : estadisticasPersonalesOld) {
                if (!estadisticasPersonalesNew.contains(estadisticasPersonalesOldEstadisticaPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstadisticaPersonal " + estadisticasPersonalesOldEstadisticaPersonal + " since its funcionarioDisico field is not nullable.");
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientosAsignadasOldSolicitudRequerimiento : solicitudesRequerimientosAsignadasOld) {
                if (!solicitudesRequerimientosAsignadasNew.contains(solicitudesRequerimientosAsignadasOldSolicitudRequerimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudRequerimiento " + solicitudesRequerimientosAsignadasOldSolicitudRequerimiento + " since its responsable field is not nullable.");
                }
            }
            for (TareaScmProyecto tareasScmAsignadasOldTareaScmProyecto : tareasScmAsignadasOld) {
                if (!tareasScmAsignadasNew.contains(tareasScmAsignadasOldTareaScmProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TareaScmProyecto " + tareasScmAsignadasOldTareaScmProyecto + " since its responsable field is not nullable.");
                }
            }
            for (ParticipanteProyecto participacionesProyectoOldParticipanteProyecto : participacionesProyectoOld) {
                if (!participacionesProyectoNew.contains(participacionesProyectoOldParticipanteProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipanteProyecto " + participacionesProyectoOldParticipanteProyecto + " since its participante field is not nullable.");
                }
            }
            for (FormularioImplementacion implementadorForlumariosOldFormularioImplementacion : implementadorForlumariosOld) {
                if (!implementadorForlumariosNew.contains(implementadorForlumariosOldFormularioImplementacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FormularioImplementacion " + implementadorForlumariosOldFormularioImplementacion + " since its implementador field is not nullable.");
                }
            }
            for (FormularioImplementacion verificadorFormulariosOldFormularioImplementacion : verificadorFormulariosOld) {
                if (!verificadorFormulariosNew.contains(verificadorFormulariosOldFormularioImplementacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FormularioImplementacion " + verificadorFormulariosOldFormularioImplementacion + " since its verificador field is not nullable.");
                }
            }
            for (SolicitudCambio solicitudesCambioEnviadasOldSolicitudCambio : solicitudesCambioEnviadasOld) {
                if (!solicitudesCambioEnviadasNew.contains(solicitudesCambioEnviadasOldSolicitudCambio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudCambio " + solicitudesCambioEnviadasOldSolicitudCambio + " since its solicitante field is not nullable.");
                }
            }
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
            if (areaNew != null) {
                areaNew = em.getReference(areaNew.getClass(), areaNew.getIdArea());
                funcionarioDisico.setArea(areaNew);
            }
            List<ItemConfiguracion> attachedItemsConfiguracionAcargoNew = new ArrayList<ItemConfiguracion>();
            for (ItemConfiguracion itemsConfiguracionAcargoNewItemConfiguracionToAttach : itemsConfiguracionAcargoNew) {
                itemsConfiguracionAcargoNewItemConfiguracionToAttach = em.getReference(itemsConfiguracionAcargoNewItemConfiguracionToAttach.getClass(), itemsConfiguracionAcargoNewItemConfiguracionToAttach.getIdItemConfiguracion());
                attachedItemsConfiguracionAcargoNew.add(itemsConfiguracionAcargoNewItemConfiguracionToAttach);
            }
            itemsConfiguracionAcargoNew = attachedItemsConfiguracionAcargoNew;
            funcionarioDisico.setItemsConfiguracionAcargo(itemsConfiguracionAcargoNew);
            List<TareaProyecto> attachedTareasProyectoAgendadasNew = new ArrayList<TareaProyecto>();
            for (TareaProyecto tareasProyectoAgendadasNewTareaProyectoToAttach : tareasProyectoAgendadasNew) {
                tareasProyectoAgendadasNewTareaProyectoToAttach = em.getReference(tareasProyectoAgendadasNewTareaProyectoToAttach.getClass(), tareasProyectoAgendadasNewTareaProyectoToAttach.getIdTareaProyecto());
                attachedTareasProyectoAgendadasNew.add(tareasProyectoAgendadasNewTareaProyectoToAttach);
            }
            tareasProyectoAgendadasNew = attachedTareasProyectoAgendadasNew;
            funcionarioDisico.setTareasProyectoAgendadas(tareasProyectoAgendadasNew);
            List<EstadisticaPersonal> attachedEstadisticasPersonalesNew = new ArrayList<EstadisticaPersonal>();
            for (EstadisticaPersonal estadisticasPersonalesNewEstadisticaPersonalToAttach : estadisticasPersonalesNew) {
                estadisticasPersonalesNewEstadisticaPersonalToAttach = em.getReference(estadisticasPersonalesNewEstadisticaPersonalToAttach.getClass(), estadisticasPersonalesNewEstadisticaPersonalToAttach.getId());
                attachedEstadisticasPersonalesNew.add(estadisticasPersonalesNewEstadisticaPersonalToAttach);
            }
            estadisticasPersonalesNew = attachedEstadisticasPersonalesNew;
            funcionarioDisico.setEstadisticasPersonales(estadisticasPersonalesNew);
            List<SolicitudRequerimiento> attachedSolicitudesRequerimientosAsignadasNew = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudesRequerimientosAsignadasNewSolicitudRequerimientoToAttach : solicitudesRequerimientosAsignadasNew) {
                solicitudesRequerimientosAsignadasNewSolicitudRequerimientoToAttach = em.getReference(solicitudesRequerimientosAsignadasNewSolicitudRequerimientoToAttach.getClass(), solicitudesRequerimientosAsignadasNewSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudesRequerimientosAsignadasNew.add(solicitudesRequerimientosAsignadasNewSolicitudRequerimientoToAttach);
            }
            solicitudesRequerimientosAsignadasNew = attachedSolicitudesRequerimientosAsignadasNew;
            funcionarioDisico.setSolicitudesRequerimientosAsignadas(solicitudesRequerimientosAsignadasNew);
            List<TareaScmProyecto> attachedTareasScmAsignadasNew = new ArrayList<TareaScmProyecto>();
            for (TareaScmProyecto tareasScmAsignadasNewTareaScmProyectoToAttach : tareasScmAsignadasNew) {
                tareasScmAsignadasNewTareaScmProyectoToAttach = em.getReference(tareasScmAsignadasNewTareaScmProyectoToAttach.getClass(), tareasScmAsignadasNewTareaScmProyectoToAttach.getTareaScmProyectoPK());
                attachedTareasScmAsignadasNew.add(tareasScmAsignadasNewTareaScmProyectoToAttach);
            }
            tareasScmAsignadasNew = attachedTareasScmAsignadasNew;
            funcionarioDisico.setTareasScmAsignadas(tareasScmAsignadasNew);
            List<ParticipanteProyecto> attachedParticipacionesProyectoNew = new ArrayList<ParticipanteProyecto>();
            for (ParticipanteProyecto participacionesProyectoNewParticipanteProyectoToAttach : participacionesProyectoNew) {
                participacionesProyectoNewParticipanteProyectoToAttach = em.getReference(participacionesProyectoNewParticipanteProyectoToAttach.getClass(), participacionesProyectoNewParticipanteProyectoToAttach.getParticipanteProyectoPK());
                attachedParticipacionesProyectoNew.add(participacionesProyectoNewParticipanteProyectoToAttach);
            }
            participacionesProyectoNew = attachedParticipacionesProyectoNew;
            funcionarioDisico.setParticipacionesProyecto(participacionesProyectoNew);
            List<FormularioImplementacion> attachedImplementadorForlumariosNew = new ArrayList<FormularioImplementacion>();
            for (FormularioImplementacion implementadorForlumariosNewFormularioImplementacionToAttach : implementadorForlumariosNew) {
                implementadorForlumariosNewFormularioImplementacionToAttach = em.getReference(implementadorForlumariosNewFormularioImplementacionToAttach.getClass(), implementadorForlumariosNewFormularioImplementacionToAttach.getIdFormularioImplementacion());
                attachedImplementadorForlumariosNew.add(implementadorForlumariosNewFormularioImplementacionToAttach);
            }
            implementadorForlumariosNew = attachedImplementadorForlumariosNew;
            funcionarioDisico.setImplementadorForlumarios(implementadorForlumariosNew);
            List<FormularioImplementacion> attachedVerificadorFormulariosNew = new ArrayList<FormularioImplementacion>();
            for (FormularioImplementacion verificadorFormulariosNewFormularioImplementacionToAttach : verificadorFormulariosNew) {
                verificadorFormulariosNewFormularioImplementacionToAttach = em.getReference(verificadorFormulariosNewFormularioImplementacionToAttach.getClass(), verificadorFormulariosNewFormularioImplementacionToAttach.getIdFormularioImplementacion());
                attachedVerificadorFormulariosNew.add(verificadorFormulariosNewFormularioImplementacionToAttach);
            }
            verificadorFormulariosNew = attachedVerificadorFormulariosNew;
            funcionarioDisico.setVerificadorFormularios(verificadorFormulariosNew);
            List<SolicitudCambio> attachedSolicitudesCambioEvaluadorFinalNew = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesCambioEvaluadorFinalNewSolicitudCambioToAttach : solicitudesCambioEvaluadorFinalNew) {
                solicitudesCambioEvaluadorFinalNewSolicitudCambioToAttach = em.getReference(solicitudesCambioEvaluadorFinalNewSolicitudCambioToAttach.getClass(), solicitudesCambioEvaluadorFinalNewSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesCambioEvaluadorFinalNew.add(solicitudesCambioEvaluadorFinalNewSolicitudCambioToAttach);
            }
            solicitudesCambioEvaluadorFinalNew = attachedSolicitudesCambioEvaluadorFinalNew;
            funcionarioDisico.setSolicitudesCambioEvaluadorFinal(solicitudesCambioEvaluadorFinalNew);
            List<SolicitudCambio> attachedSolicitudesCambioEvaluadorImpactoNew = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesCambioEvaluadorImpactoNewSolicitudCambioToAttach : solicitudesCambioEvaluadorImpactoNew) {
                solicitudesCambioEvaluadorImpactoNewSolicitudCambioToAttach = em.getReference(solicitudesCambioEvaluadorImpactoNewSolicitudCambioToAttach.getClass(), solicitudesCambioEvaluadorImpactoNewSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesCambioEvaluadorImpactoNew.add(solicitudesCambioEvaluadorImpactoNewSolicitudCambioToAttach);
            }
            solicitudesCambioEvaluadorImpactoNew = attachedSolicitudesCambioEvaluadorImpactoNew;
            funcionarioDisico.setSolicitudesCambioEvaluadorImpacto(solicitudesCambioEvaluadorImpactoNew);
            List<SolicitudCambio> attachedSolicitudesCambioEnviadasNew = new ArrayList<SolicitudCambio>();
            for (SolicitudCambio solicitudesCambioEnviadasNewSolicitudCambioToAttach : solicitudesCambioEnviadasNew) {
                solicitudesCambioEnviadasNewSolicitudCambioToAttach = em.getReference(solicitudesCambioEnviadasNewSolicitudCambioToAttach.getClass(), solicitudesCambioEnviadasNewSolicitudCambioToAttach.getIdSolicitudCambio());
                attachedSolicitudesCambioEnviadasNew.add(solicitudesCambioEnviadasNewSolicitudCambioToAttach);
            }
            solicitudesCambioEnviadasNew = attachedSolicitudesCambioEnviadasNew;
            funcionarioDisico.setSolicitudesCambioEnviadas(solicitudesCambioEnviadasNew);
            List<Notificacion> attachedNotificacionesNew = new ArrayList<Notificacion>();
            for (Notificacion notificacionesNewNotificacionToAttach : notificacionesNew) {
                notificacionesNewNotificacionToAttach = em.getReference(notificacionesNewNotificacionToAttach.getClass(), notificacionesNewNotificacionToAttach.getIdNotificacion());
                attachedNotificacionesNew.add(notificacionesNewNotificacionToAttach);
            }
            notificacionesNew = attachedNotificacionesNew;
            funcionarioDisico.setNotificaciones(notificacionesNew);
            List<SolicitudRequerimiento> attachedSolicitudesRequerimientoEnviadasNew = new ArrayList<SolicitudRequerimiento>();
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach : solicitudesRequerimientoEnviadasNew) {
                solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach = em.getReference(solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach.getClass(), solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach.getIdSolicitudRequerimiento());
                attachedSolicitudesRequerimientoEnviadasNew.add(solicitudesRequerimientoEnviadasNewSolicitudRequerimientoToAttach);
            }
            solicitudesRequerimientoEnviadasNew = attachedSolicitudesRequerimientoEnviadasNew;
            funcionarioDisico.setSolicitudesRequerimientoEnviadas(solicitudesRequerimientoEnviadasNew);
            List<ComentarioSolicitud> attachedComentarioSolicitudListNew = new ArrayList<ComentarioSolicitud>();
            for (ComentarioSolicitud comentarioSolicitudListNewComentarioSolicitudToAttach : comentarioSolicitudListNew) {
                comentarioSolicitudListNewComentarioSolicitudToAttach = em.getReference(comentarioSolicitudListNewComentarioSolicitudToAttach.getClass(), comentarioSolicitudListNewComentarioSolicitudToAttach.getIdComentario());
                attachedComentarioSolicitudListNew.add(comentarioSolicitudListNewComentarioSolicitudToAttach);
            }
            comentarioSolicitudListNew = attachedComentarioSolicitudListNew;
            funcionarioDisico.setComentarioSolicitudList(comentarioSolicitudListNew);
            funcionarioDisico = em.merge(funcionarioDisico);
            if (areaOld != null && !areaOld.equals(areaNew)) {
                areaOld.getFuncionarioDisicoList().remove(funcionarioDisico);
                areaOld = em.merge(areaOld);
            }
            if (areaNew != null && !areaNew.equals(areaOld)) {
                areaNew.getFuncionarioDisicoList().add(funcionarioDisico);
                areaNew = em.merge(areaNew);
            }
            for (ItemConfiguracion itemsConfiguracionAcargoNewItemConfiguracion : itemsConfiguracionAcargoNew) {
                if (!itemsConfiguracionAcargoOld.contains(itemsConfiguracionAcargoNewItemConfiguracion)) {
                    FuncionarioDisico oldResponsableItemOfItemsConfiguracionAcargoNewItemConfiguracion = itemsConfiguracionAcargoNewItemConfiguracion.getResponsableItem();
                    itemsConfiguracionAcargoNewItemConfiguracion.setResponsableItem(funcionarioDisico);
                    itemsConfiguracionAcargoNewItemConfiguracion = em.merge(itemsConfiguracionAcargoNewItemConfiguracion);
                    if (oldResponsableItemOfItemsConfiguracionAcargoNewItemConfiguracion != null && !oldResponsableItemOfItemsConfiguracionAcargoNewItemConfiguracion.equals(funcionarioDisico)) {
                        oldResponsableItemOfItemsConfiguracionAcargoNewItemConfiguracion.getItemsConfiguracionAcargo().remove(itemsConfiguracionAcargoNewItemConfiguracion);
                        oldResponsableItemOfItemsConfiguracionAcargoNewItemConfiguracion = em.merge(oldResponsableItemOfItemsConfiguracionAcargoNewItemConfiguracion);
                    }
                }
            }
            for (TareaProyecto tareasProyectoAgendadasNewTareaProyecto : tareasProyectoAgendadasNew) {
                if (!tareasProyectoAgendadasOld.contains(tareasProyectoAgendadasNewTareaProyecto)) {
                    FuncionarioDisico oldResponsableTareaOfTareasProyectoAgendadasNewTareaProyecto = tareasProyectoAgendadasNewTareaProyecto.getResponsableTarea();
                    tareasProyectoAgendadasNewTareaProyecto.setResponsableTarea(funcionarioDisico);
                    tareasProyectoAgendadasNewTareaProyecto = em.merge(tareasProyectoAgendadasNewTareaProyecto);
                    if (oldResponsableTareaOfTareasProyectoAgendadasNewTareaProyecto != null && !oldResponsableTareaOfTareasProyectoAgendadasNewTareaProyecto.equals(funcionarioDisico)) {
                        oldResponsableTareaOfTareasProyectoAgendadasNewTareaProyecto.getTareasProyectoAgendadas().remove(tareasProyectoAgendadasNewTareaProyecto);
                        oldResponsableTareaOfTareasProyectoAgendadasNewTareaProyecto = em.merge(oldResponsableTareaOfTareasProyectoAgendadasNewTareaProyecto);
                    }
                }
            }
            for (EstadisticaPersonal estadisticasPersonalesNewEstadisticaPersonal : estadisticasPersonalesNew) {
                if (!estadisticasPersonalesOld.contains(estadisticasPersonalesNewEstadisticaPersonal)) {
                    FuncionarioDisico oldFuncionarioDisicoOfEstadisticasPersonalesNewEstadisticaPersonal = estadisticasPersonalesNewEstadisticaPersonal.getFuncionarioDisico();
                    estadisticasPersonalesNewEstadisticaPersonal.setFuncionarioDisico(funcionarioDisico);
                    estadisticasPersonalesNewEstadisticaPersonal = em.merge(estadisticasPersonalesNewEstadisticaPersonal);
                    if (oldFuncionarioDisicoOfEstadisticasPersonalesNewEstadisticaPersonal != null && !oldFuncionarioDisicoOfEstadisticasPersonalesNewEstadisticaPersonal.equals(funcionarioDisico)) {
                        oldFuncionarioDisicoOfEstadisticasPersonalesNewEstadisticaPersonal.getEstadisticasPersonales().remove(estadisticasPersonalesNewEstadisticaPersonal);
                        oldFuncionarioDisicoOfEstadisticasPersonalesNewEstadisticaPersonal = em.merge(oldFuncionarioDisicoOfEstadisticasPersonalesNewEstadisticaPersonal);
                    }
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientosAsignadasNewSolicitudRequerimiento : solicitudesRequerimientosAsignadasNew) {
                if (!solicitudesRequerimientosAsignadasOld.contains(solicitudesRequerimientosAsignadasNewSolicitudRequerimiento)) {
                    FuncionarioDisico oldResponsableOfSolicitudesRequerimientosAsignadasNewSolicitudRequerimiento = solicitudesRequerimientosAsignadasNewSolicitudRequerimiento.getResponsable();
                    solicitudesRequerimientosAsignadasNewSolicitudRequerimiento.setResponsable(funcionarioDisico);
                    solicitudesRequerimientosAsignadasNewSolicitudRequerimiento = em.merge(solicitudesRequerimientosAsignadasNewSolicitudRequerimiento);
                    if (oldResponsableOfSolicitudesRequerimientosAsignadasNewSolicitudRequerimiento != null && !oldResponsableOfSolicitudesRequerimientosAsignadasNewSolicitudRequerimiento.equals(funcionarioDisico)) {
                        oldResponsableOfSolicitudesRequerimientosAsignadasNewSolicitudRequerimiento.getSolicitudesRequerimientosAsignadas().remove(solicitudesRequerimientosAsignadasNewSolicitudRequerimiento);
                        oldResponsableOfSolicitudesRequerimientosAsignadasNewSolicitudRequerimiento = em.merge(oldResponsableOfSolicitudesRequerimientosAsignadasNewSolicitudRequerimiento);
                    }
                }
            }
            for (TareaScmProyecto tareasScmAsignadasNewTareaScmProyecto : tareasScmAsignadasNew) {
                if (!tareasScmAsignadasOld.contains(tareasScmAsignadasNewTareaScmProyecto)) {
                    FuncionarioDisico oldResponsableOfTareasScmAsignadasNewTareaScmProyecto = tareasScmAsignadasNewTareaScmProyecto.getResponsable();
                    tareasScmAsignadasNewTareaScmProyecto.setResponsable(funcionarioDisico);
                    tareasScmAsignadasNewTareaScmProyecto = em.merge(tareasScmAsignadasNewTareaScmProyecto);
                    if (oldResponsableOfTareasScmAsignadasNewTareaScmProyecto != null && !oldResponsableOfTareasScmAsignadasNewTareaScmProyecto.equals(funcionarioDisico)) {
                        oldResponsableOfTareasScmAsignadasNewTareaScmProyecto.getTareasScmAsignadas().remove(tareasScmAsignadasNewTareaScmProyecto);
                        oldResponsableOfTareasScmAsignadasNewTareaScmProyecto = em.merge(oldResponsableOfTareasScmAsignadasNewTareaScmProyecto);
                    }
                }
            }
            for (ParticipanteProyecto participacionesProyectoNewParticipanteProyecto : participacionesProyectoNew) {
                if (!participacionesProyectoOld.contains(participacionesProyectoNewParticipanteProyecto)) {
                    FuncionarioDisico oldParticipanteOfParticipacionesProyectoNewParticipanteProyecto = participacionesProyectoNewParticipanteProyecto.getParticipante();
                    participacionesProyectoNewParticipanteProyecto.setParticipante(funcionarioDisico);
                    participacionesProyectoNewParticipanteProyecto = em.merge(participacionesProyectoNewParticipanteProyecto);
                    if (oldParticipanteOfParticipacionesProyectoNewParticipanteProyecto != null && !oldParticipanteOfParticipacionesProyectoNewParticipanteProyecto.equals(funcionarioDisico)) {
                        oldParticipanteOfParticipacionesProyectoNewParticipanteProyecto.getParticipacionesProyecto().remove(participacionesProyectoNewParticipanteProyecto);
                        oldParticipanteOfParticipacionesProyectoNewParticipanteProyecto = em.merge(oldParticipanteOfParticipacionesProyectoNewParticipanteProyecto);
                    }
                }
            }
            for (FormularioImplementacion implementadorForlumariosNewFormularioImplementacion : implementadorForlumariosNew) {
                if (!implementadorForlumariosOld.contains(implementadorForlumariosNewFormularioImplementacion)) {
                    FuncionarioDisico oldImplementadorOfImplementadorForlumariosNewFormularioImplementacion = implementadorForlumariosNewFormularioImplementacion.getImplementador();
                    implementadorForlumariosNewFormularioImplementacion.setImplementador(funcionarioDisico);
                    implementadorForlumariosNewFormularioImplementacion = em.merge(implementadorForlumariosNewFormularioImplementacion);
                    if (oldImplementadorOfImplementadorForlumariosNewFormularioImplementacion != null && !oldImplementadorOfImplementadorForlumariosNewFormularioImplementacion.equals(funcionarioDisico)) {
                        oldImplementadorOfImplementadorForlumariosNewFormularioImplementacion.getImplementadorForlumarios().remove(implementadorForlumariosNewFormularioImplementacion);
                        oldImplementadorOfImplementadorForlumariosNewFormularioImplementacion = em.merge(oldImplementadorOfImplementadorForlumariosNewFormularioImplementacion);
                    }
                }
            }
            for (FormularioImplementacion verificadorFormulariosNewFormularioImplementacion : verificadorFormulariosNew) {
                if (!verificadorFormulariosOld.contains(verificadorFormulariosNewFormularioImplementacion)) {
                    FuncionarioDisico oldVerificadorOfVerificadorFormulariosNewFormularioImplementacion = verificadorFormulariosNewFormularioImplementacion.getVerificador();
                    verificadorFormulariosNewFormularioImplementacion.setVerificador(funcionarioDisico);
                    verificadorFormulariosNewFormularioImplementacion = em.merge(verificadorFormulariosNewFormularioImplementacion);
                    if (oldVerificadorOfVerificadorFormulariosNewFormularioImplementacion != null && !oldVerificadorOfVerificadorFormulariosNewFormularioImplementacion.equals(funcionarioDisico)) {
                        oldVerificadorOfVerificadorFormulariosNewFormularioImplementacion.getVerificadorFormularios().remove(verificadorFormulariosNewFormularioImplementacion);
                        oldVerificadorOfVerificadorFormulariosNewFormularioImplementacion = em.merge(oldVerificadorOfVerificadorFormulariosNewFormularioImplementacion);
                    }
                }
            }
            for (SolicitudCambio solicitudesCambioEvaluadorFinalOldSolicitudCambio : solicitudesCambioEvaluadorFinalOld) {
                if (!solicitudesCambioEvaluadorFinalNew.contains(solicitudesCambioEvaluadorFinalOldSolicitudCambio)) {
                    solicitudesCambioEvaluadorFinalOldSolicitudCambio.setEvaluadorFinal(null);
                    solicitudesCambioEvaluadorFinalOldSolicitudCambio = em.merge(solicitudesCambioEvaluadorFinalOldSolicitudCambio);
                }
            }
            for (SolicitudCambio solicitudesCambioEvaluadorFinalNewSolicitudCambio : solicitudesCambioEvaluadorFinalNew) {
                if (!solicitudesCambioEvaluadorFinalOld.contains(solicitudesCambioEvaluadorFinalNewSolicitudCambio)) {
                    FuncionarioDisico oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalNewSolicitudCambio = solicitudesCambioEvaluadorFinalNewSolicitudCambio.getEvaluadorFinal();
                    solicitudesCambioEvaluadorFinalNewSolicitudCambio.setEvaluadorFinal(funcionarioDisico);
                    solicitudesCambioEvaluadorFinalNewSolicitudCambio = em.merge(solicitudesCambioEvaluadorFinalNewSolicitudCambio);
                    if (oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalNewSolicitudCambio != null && !oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalNewSolicitudCambio.equals(funcionarioDisico)) {
                        oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalNewSolicitudCambio.getSolicitudesCambioEvaluadorFinal().remove(solicitudesCambioEvaluadorFinalNewSolicitudCambio);
                        oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalNewSolicitudCambio = em.merge(oldEvaluadorFinalOfSolicitudesCambioEvaluadorFinalNewSolicitudCambio);
                    }
                }
            }
            for (SolicitudCambio solicitudesCambioEvaluadorImpactoOldSolicitudCambio : solicitudesCambioEvaluadorImpactoOld) {
                if (!solicitudesCambioEvaluadorImpactoNew.contains(solicitudesCambioEvaluadorImpactoOldSolicitudCambio)) {
                    solicitudesCambioEvaluadorImpactoOldSolicitudCambio.setEvaluadorImpacto(null);
                    solicitudesCambioEvaluadorImpactoOldSolicitudCambio = em.merge(solicitudesCambioEvaluadorImpactoOldSolicitudCambio);
                }
            }
            for (SolicitudCambio solicitudesCambioEvaluadorImpactoNewSolicitudCambio : solicitudesCambioEvaluadorImpactoNew) {
                if (!solicitudesCambioEvaluadorImpactoOld.contains(solicitudesCambioEvaluadorImpactoNewSolicitudCambio)) {
                    FuncionarioDisico oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoNewSolicitudCambio = solicitudesCambioEvaluadorImpactoNewSolicitudCambio.getEvaluadorImpacto();
                    solicitudesCambioEvaluadorImpactoNewSolicitudCambio.setEvaluadorImpacto(funcionarioDisico);
                    solicitudesCambioEvaluadorImpactoNewSolicitudCambio = em.merge(solicitudesCambioEvaluadorImpactoNewSolicitudCambio);
                    if (oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoNewSolicitudCambio != null && !oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoNewSolicitudCambio.equals(funcionarioDisico)) {
                        oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoNewSolicitudCambio.getSolicitudesCambioEvaluadorImpacto().remove(solicitudesCambioEvaluadorImpactoNewSolicitudCambio);
                        oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoNewSolicitudCambio = em.merge(oldEvaluadorImpactoOfSolicitudesCambioEvaluadorImpactoNewSolicitudCambio);
                    }
                }
            }
            for (SolicitudCambio solicitudesCambioEnviadasNewSolicitudCambio : solicitudesCambioEnviadasNew) {
                if (!solicitudesCambioEnviadasOld.contains(solicitudesCambioEnviadasNewSolicitudCambio)) {
                    FuncionarioDisico oldSolicitanteOfSolicitudesCambioEnviadasNewSolicitudCambio = solicitudesCambioEnviadasNewSolicitudCambio.getSolicitante();
                    solicitudesCambioEnviadasNewSolicitudCambio.setSolicitante(funcionarioDisico);
                    solicitudesCambioEnviadasNewSolicitudCambio = em.merge(solicitudesCambioEnviadasNewSolicitudCambio);
                    if (oldSolicitanteOfSolicitudesCambioEnviadasNewSolicitudCambio != null && !oldSolicitanteOfSolicitudesCambioEnviadasNewSolicitudCambio.equals(funcionarioDisico)) {
                        oldSolicitanteOfSolicitudesCambioEnviadasNewSolicitudCambio.getSolicitudesCambioEnviadas().remove(solicitudesCambioEnviadasNewSolicitudCambio);
                        oldSolicitanteOfSolicitudesCambioEnviadasNewSolicitudCambio = em.merge(oldSolicitanteOfSolicitudesCambioEnviadasNewSolicitudCambio);
                    }
                }
            }
            for (Notificacion notificacionesNewNotificacion : notificacionesNew) {
                if (!notificacionesOld.contains(notificacionesNewNotificacion)) {
                    FuncionarioDisico oldDestinatarioOfNotificacionesNewNotificacion = (FuncionarioDisico) notificacionesNewNotificacion.getDestinatario();
                    notificacionesNewNotificacion.setDestinatario(funcionarioDisico);
                    notificacionesNewNotificacion = em.merge(notificacionesNewNotificacion);
                    if (oldDestinatarioOfNotificacionesNewNotificacion != null && !oldDestinatarioOfNotificacionesNewNotificacion.equals(funcionarioDisico)) {
                        oldDestinatarioOfNotificacionesNewNotificacion.getNotificaciones().remove(notificacionesNewNotificacion);
                        oldDestinatarioOfNotificacionesNewNotificacion = em.merge(oldDestinatarioOfNotificacionesNewNotificacion);
                    }
                }
            }
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasNewSolicitudRequerimiento : solicitudesRequerimientoEnviadasNew) {
                if (!solicitudesRequerimientoEnviadasOld.contains(solicitudesRequerimientoEnviadasNewSolicitudRequerimiento)) {
                    FuncionarioDisico oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento = (FuncionarioDisico) solicitudesRequerimientoEnviadasNewSolicitudRequerimiento.getSolicitante();
                    solicitudesRequerimientoEnviadasNewSolicitudRequerimiento.setSolicitante(funcionarioDisico);
                    solicitudesRequerimientoEnviadasNewSolicitudRequerimiento = em.merge(solicitudesRequerimientoEnviadasNewSolicitudRequerimiento);
                    if (oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento != null && !oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento.equals(funcionarioDisico)) {
                        oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento.getSolicitudesRequerimientoEnviadas().remove(solicitudesRequerimientoEnviadasNewSolicitudRequerimiento);
                        oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento = em.merge(oldSolicitanteOfSolicitudesRequerimientoEnviadasNewSolicitudRequerimiento);
                    }
                }
            }
            for (ComentarioSolicitud comentarioSolicitudListNewComentarioSolicitud : comentarioSolicitudListNew) {
                if (!comentarioSolicitudListOld.contains(comentarioSolicitudListNewComentarioSolicitud)) {
                    FuncionarioDisico oldAutorOfComentarioSolicitudListNewComentarioSolicitud = (FuncionarioDisico) comentarioSolicitudListNewComentarioSolicitud.getAutor();
                    comentarioSolicitudListNewComentarioSolicitud.setAutor(funcionarioDisico);
                    comentarioSolicitudListNewComentarioSolicitud = em.merge(comentarioSolicitudListNewComentarioSolicitud);
                    if (oldAutorOfComentarioSolicitudListNewComentarioSolicitud != null && !oldAutorOfComentarioSolicitudListNewComentarioSolicitud.equals(funcionarioDisico)) {
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
                Integer id = funcionarioDisico.getRut();
                if (findFuncionarioDisico(id) == null) {
                    throw new NonexistentEntityException("The funcionarioDisico with id " + id + " no longer exists.");
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
            FuncionarioDisico funcionarioDisico;
            try {
                funcionarioDisico = em.getReference(FuncionarioDisico.class, id);
                funcionarioDisico.getRut();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionarioDisico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemConfiguracion> itemsConfiguracionAcargoOrphanCheck = funcionarioDisico.getItemsConfiguracionAcargo();
            for (ItemConfiguracion itemsConfiguracionAcargoOrphanCheckItemConfiguracion : itemsConfiguracionAcargoOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the ItemConfiguracion " + itemsConfiguracionAcargoOrphanCheckItemConfiguracion + " in its itemsConfiguracionAcargo field has a non-nullable responsableItem field.");
            }
            List<TareaProyecto> tareasProyectoAgendadasOrphanCheck = funcionarioDisico.getTareasProyectoAgendadas();
            for (TareaProyecto tareasProyectoAgendadasOrphanCheckTareaProyecto : tareasProyectoAgendadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the TareaProyecto " + tareasProyectoAgendadasOrphanCheckTareaProyecto + " in its tareasProyectoAgendadas field has a non-nullable responsableTarea field.");
            }
            List<EstadisticaPersonal> estadisticasPersonalesOrphanCheck = funcionarioDisico.getEstadisticasPersonales();
            for (EstadisticaPersonal estadisticasPersonalesOrphanCheckEstadisticaPersonal : estadisticasPersonalesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the EstadisticaPersonal " + estadisticasPersonalesOrphanCheckEstadisticaPersonal + " in its estadisticasPersonales field has a non-nullable funcionarioDisico field.");
            }
            List<SolicitudRequerimiento> solicitudesRequerimientosAsignadasOrphanCheck = funcionarioDisico.getSolicitudesRequerimientosAsignadas();
            for (SolicitudRequerimiento solicitudesRequerimientosAsignadasOrphanCheckSolicitudRequerimiento : solicitudesRequerimientosAsignadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the SolicitudRequerimiento " + solicitudesRequerimientosAsignadasOrphanCheckSolicitudRequerimiento + " in its solicitudesRequerimientosAsignadas field has a non-nullable responsable field.");
            }
            List<TareaScmProyecto> tareasScmAsignadasOrphanCheck = funcionarioDisico.getTareasScmAsignadas();
            for (TareaScmProyecto tareasScmAsignadasOrphanCheckTareaScmProyecto : tareasScmAsignadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the TareaScmProyecto " + tareasScmAsignadasOrphanCheckTareaScmProyecto + " in its tareasScmAsignadas field has a non-nullable responsable field.");
            }
            List<ParticipanteProyecto> participacionesProyectoOrphanCheck = funcionarioDisico.getParticipacionesProyecto();
            for (ParticipanteProyecto participacionesProyectoOrphanCheckParticipanteProyecto : participacionesProyectoOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the ParticipanteProyecto " + participacionesProyectoOrphanCheckParticipanteProyecto + " in its participacionesProyecto field has a non-nullable participante field.");
            }
            List<FormularioImplementacion> implementadorForlumariosOrphanCheck = funcionarioDisico.getImplementadorForlumarios();
            for (FormularioImplementacion implementadorForlumariosOrphanCheckFormularioImplementacion : implementadorForlumariosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the FormularioImplementacion " + implementadorForlumariosOrphanCheckFormularioImplementacion + " in its implementadorForlumarios field has a non-nullable implementador field.");
            }
            List<FormularioImplementacion> verificadorFormulariosOrphanCheck = funcionarioDisico.getVerificadorFormularios();
            for (FormularioImplementacion verificadorFormulariosOrphanCheckFormularioImplementacion : verificadorFormulariosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the FormularioImplementacion " + verificadorFormulariosOrphanCheckFormularioImplementacion + " in its verificadorFormularios field has a non-nullable verificador field.");
            }
            List<SolicitudCambio> solicitudesCambioEnviadasOrphanCheck = funcionarioDisico.getSolicitudesCambioEnviadas();
            for (SolicitudCambio solicitudesCambioEnviadasOrphanCheckSolicitudCambio : solicitudesCambioEnviadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the SolicitudCambio " + solicitudesCambioEnviadasOrphanCheckSolicitudCambio + " in its solicitudesCambioEnviadas field has a non-nullable solicitante field.");
            }
            List<Notificacion> notificacionesOrphanCheck = funcionarioDisico.getNotificaciones();
            for (Notificacion notificacionesOrphanCheckNotificacion : notificacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the Notificacion " + notificacionesOrphanCheckNotificacion + " in its notificaciones field has a non-nullable destinatario field.");
            }
            List<SolicitudRequerimiento> solicitudesRequerimientoEnviadasOrphanCheck = funcionarioDisico.getSolicitudesRequerimientoEnviadas();
            for (SolicitudRequerimiento solicitudesRequerimientoEnviadasOrphanCheckSolicitudRequerimiento : solicitudesRequerimientoEnviadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the SolicitudRequerimiento " + solicitudesRequerimientoEnviadasOrphanCheckSolicitudRequerimiento + " in its solicitudesRequerimientoEnviadas field has a non-nullable solicitante field.");
            }
            List<ComentarioSolicitud> comentarioSolicitudListOrphanCheck = funcionarioDisico.getComentarioSolicitudList();
            for (ComentarioSolicitud comentarioSolicitudListOrphanCheckComentarioSolicitud : comentarioSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FuncionarioDisico (" + funcionarioDisico + ") cannot be destroyed since the ComentarioSolicitud " + comentarioSolicitudListOrphanCheckComentarioSolicitud + " in its comentarioSolicitudList field has a non-nullable autor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Area area = funcionarioDisico.getArea();
            if (area != null) {
                area.getFuncionarioDisicoList().remove(funcionarioDisico);
                area = em.merge(area);
            }
            List<SolicitudCambio> solicitudesCambioEvaluadorFinal = funcionarioDisico.getSolicitudesCambioEvaluadorFinal();
            for (SolicitudCambio solicitudesCambioEvaluadorFinalSolicitudCambio : solicitudesCambioEvaluadorFinal) {
                solicitudesCambioEvaluadorFinalSolicitudCambio.setEvaluadorFinal(null);
                solicitudesCambioEvaluadorFinalSolicitudCambio = em.merge(solicitudesCambioEvaluadorFinalSolicitudCambio);
            }
            List<SolicitudCambio> solicitudesCambioEvaluadorImpacto = funcionarioDisico.getSolicitudesCambioEvaluadorImpacto();
            for (SolicitudCambio solicitudesCambioEvaluadorImpactoSolicitudCambio : solicitudesCambioEvaluadorImpacto) {
                solicitudesCambioEvaluadorImpactoSolicitudCambio.setEvaluadorImpacto(null);
                solicitudesCambioEvaluadorImpactoSolicitudCambio = em.merge(solicitudesCambioEvaluadorImpactoSolicitudCambio);
            }
            em.remove(funcionarioDisico);
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

    public List<FuncionarioDisico> findFuncionarioDisicoEntities() {
        return findFuncionarioDisicoEntities(true, -1, -1);
    }

    public List<FuncionarioDisico> findFuncionarioDisicoEntities(int maxResults, int firstResult) {
        return findFuncionarioDisicoEntities(false, maxResults, firstResult);
    }

    private List<FuncionarioDisico> findFuncionarioDisicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FuncionarioDisico.class));
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

    public FuncionarioDisico findFuncionarioDisico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FuncionarioDisico.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioDisicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FuncionarioDisico> rt = cq.from(FuncionarioDisico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
