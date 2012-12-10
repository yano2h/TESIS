/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EstadoProyecto;
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
public class EstadoProyectoFacade extends AbstractFacade<EstadoProyecto> implements EstadoProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoProyectoFacade() {
        super(EstadoProyecto.class);
    }

    @Override
    public List<EstadoProyecto> findAll() {
        TypedQuery<EstadoProyecto> q = getEntityManager().createNamedQuery("EstadoProyecto.findAll", EstadoProyecto.class);
        return q.getResultList();
    }
    
    
}
