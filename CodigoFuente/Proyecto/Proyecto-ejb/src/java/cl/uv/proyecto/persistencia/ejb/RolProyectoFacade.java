/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.RolProyecto;
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
public class RolProyectoFacade extends AbstractFacade<RolProyecto> implements RolProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolProyectoFacade() {
        super(RolProyecto.class);
    }

    @Override
    public List<RolProyecto> findAll() {
        TypedQuery<RolProyecto> q = getEntityManager().createNamedQuery("RolProyecto.findAll", RolProyecto.class);
        return q.getResultList();
    }
    
    
}
