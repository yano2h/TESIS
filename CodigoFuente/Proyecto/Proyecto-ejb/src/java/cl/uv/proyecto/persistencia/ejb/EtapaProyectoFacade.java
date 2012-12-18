/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EtapaProyecto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alejandro
 */
@Stateless
public class EtapaProyectoFacade extends AbstractFacade<EtapaProyecto> implements EtapaProyectoFacadeLocal {

    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtapaProyectoFacade() {
        super(EtapaProyecto.class);
    }

    @Override
    public List<EtapaProyecto> findAll() {
        TypedQuery<EtapaProyecto> q = getEntityManager().createNamedQuery("EstapaProyecto.findAll", EtapaProyecto.class);
        return q.getResultList();
    }
    
}
