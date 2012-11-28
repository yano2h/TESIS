/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.TipoPrioridad;
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
public class TipoPrioridadFacade extends AbstractFacade<TipoPrioridad> implements TipoPrioridadFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoPrioridadFacade() {
        super(TipoPrioridad.class);
    }

    @Override
    public List<TipoPrioridad> findAll() {
        TypedQuery<TipoPrioridad> q = getEntityManager().createNamedQuery("TipoPrioridad.findAll",TipoPrioridad.class);
        return q.getResultList();
    }
    
    
}
