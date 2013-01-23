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
import javax.persistence.TypedQuery;

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
    public List<TareaProyecto> buscarTareas(Proyecto proyecto){
        TypedQuery<TareaProyecto> q = em.createQuery("SELECT t FROM TareaProyecto t WHERE t.proyecto = :proyecto AND t.visible = TRUE ORDER BY t.fechaInicioPropuesta", TareaProyecto.class);
        q.setParameter("proyecto", proyecto);
        return q.getResultList();
    }

    @Override
    public List<TareaProyecto> buscarTareas(FuncionarioDisico responsable) {
        TypedQuery<TareaProyecto> q = em.createQuery("SELECT t FROM TareaProyecto t WHERE t.responsableTarea = :responsableTarea AND t.visible = TRUE ORDER BY t.fechaInicioPropuesta DESC", TareaProyecto.class);
        q.setParameter("responsableTarea", responsable);
        return q.getResultList();
    }
    
    @Override
    public List<TareaProyecto> buscarTareas(Proyecto proyecto, FuncionarioDisico responsable) {
        TypedQuery<TareaProyecto> q = em.createQuery("SELECT t FROM TareaProyecto t WHERE t.proyecto = :proyecto AND t.responsableTarea = :responsableTarea AND t.visible = TRUE ORDER BY t.fechaInicioPropuesta DESC", TareaProyecto.class);
        q.setParameter("proyecto", proyecto);
        q.setParameter("responsableTarea", responsable);
        return q.getResultList();
    }
    
    @Override
    public Integer calcularAvancePromedioTareasPorProyecto(Proyecto p){
        List<TareaProyecto> tareasProyecto = buscarTareas(p);
        Integer sumaAvances = 0;
        for (TareaProyecto tareaProyecto : tareasProyecto) {
            sumaAvances += tareaProyecto.getNivelAvance();
        }
        return (tareasProyecto.size()>0)?sumaAvances/tareasProyecto.size():0;
    }

    @Override
    public void edit(TareaProyecto tarea) {
        TareaProyecto old = find(tarea.getIdTareaProyecto());
        if (old.getNivelAvance() == 0 && tarea.getNivelAvance()>0 ) {
            tarea.setFechaInicioReal(new Date());
        }
        
        if (tarea.getNivelAvance()==100 && tarea.getFechaTerminoReal()==null) {
            tarea.setFechaTerminoReal(new Date());
        }
        
        super.edit(tarea); 
    }

    
}
