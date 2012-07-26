/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.consts.EstadoSC;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.SolicitudCambio;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jano
 */
@Stateless
public class SolicitudCambioFacade extends AbstractFacade<SolicitudCambio> implements SolicitudCambioFacadeLocal {

    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;
    @EJB
    private EstadoSolicitudCambioFacadeLocal estadoSolicitudCambioFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudCambioFacade() {
        super(SolicitudCambio.class);
    }

    @Override
    public void enviarSolicitudCambio(SolicitudCambio sc, FuncionarioDisico solicitante) {
        sc.setSolicitante(solicitante);
        sc.setFechaEnvio(new Date());
        sc.setEstadoSolicitud(estadoSolicitudCambioFacade.find(EstadoSC.ENVIADA));
        create(sc);
    }
    
    @Override
    public void guardarAnalisisImpacto(SolicitudCambio sc, FuncionarioDisico funcionario){
        sc.setEvaluadorImpacto(funcionario);
        sc.setFechaAnalisis(new Date());
        sc.setEstadoSolicitud(estadoSolicitudCambioFacade.find(EstadoSC.ANALISADA));
        edit(sc);
    }

    @Override
    public void guardarEvaluacionSolicitud(SolicitudCambio sc, FuncionarioDisico funcionario){
        sc.setEvaluadorFinal(funcionario);
        sc.setFechaCierre(new Date());
        if(sc.getAprobada()){
            sc.setEstadoSolicitud(estadoSolicitudCambioFacade.find(EstadoSC.APROBADA));
        }else{
            sc.setEstadoSolicitud(estadoSolicitudCambioFacade.find(EstadoSC.RECHAZADA));
        }
        edit(sc);
        
    }
    @Override
    public List<SolicitudCambio> buscarSolicitudPorProyecto(Proyecto proyecto) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s WHERE s.proyecto = :proyecto");
        q.setParameter("proyecto", proyecto);
        return q.getResultList();
    }

    @Override
    public List<SolicitudCambio> buscarSolicitudAnalisisPendiente(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s, TareaScmProyecto t WHERE t.responsable = :responsable AND t.tareaScmProyectoPK.idTareaScm = :idTarea  AND s.proyecto = t.proyecto AND s.estadoSolicitud = :estado");
        q.setParameter("responsable", funcionario);
        q.setParameter("idTarea", (Integer)3);
        q.setParameter("estado", estadoSolicitudCambioFacade.find(EstadoSC.ENVIADA));
        return q.getResultList();
    }

    @Override
    public List<SolicitudCambio> buscarSolicitudAnalisadas(FuncionarioDisico funcionario) {
        Query q = em.createQuery("SELECT s FROM SolicitudCambio s WHERE s.evaluadorImpacto = :evaluador");
        q.setParameter("evaluador", funcionario);
        return q.getResultList();
    }
}
