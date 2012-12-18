/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.TipoProyecto;
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
public class TipoProyectoFacade extends AbstractFacade<TipoProyecto> implements TipoProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoProyectoFacade() {
        super(TipoProyecto.class);
    }

    @Override
    public List<TipoProyecto> findAll() {
        TypedQuery<TipoProyecto> q = getEntityManager().createNamedQuery("TipoProyecto.findAll",TipoProyecto.class);
        return q.getResultList();
    }
    
    
}
