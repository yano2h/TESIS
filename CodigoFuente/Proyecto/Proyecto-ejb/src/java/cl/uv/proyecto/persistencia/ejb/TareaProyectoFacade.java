/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jano
 */
@Stateless
public class TareaProyectoFacade extends AbstractFacade<TareaProyecto> implements TareaProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaProyectoFacade() {
        super(TareaProyecto.class);
    }
    
    @Override
    public void create(TareaProyecto t){
        t.setFechaCreacion(new Date());
        getEntityManager().persist(t);
    }
    
    @Override
    public List<TareaProyecto> buscarTareasPorProyecto(Proyecto proyecto){
        Query q = em.createQuery("SELECT t FROM TareaProyecto t WHERE t.proyecto = :proyecto AND t.visible = TRUE ORDER BY t.fechaInicioPropuesta");
        q.setParameter("proyecto", proyecto);
        return q.getResultList();
    }

    @Override
    public List<TareaProyecto> buscarTareasPorResponsable(FuncionarioDisico responsable) {
        Query q = em.createQuery("SELECT t FROM TareaProyecto t WHERE t.responsableTarea = :responsableTarea AND t.visible = TRUE ORDER BY t.fechaInicioPropuesta DESC");
        q.setParameter("responsableTarea", responsable);
        return q.getResultList();
    }
    
}
