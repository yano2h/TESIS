/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ParticipanteProyecto;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
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
public class ParticipanteProyectoFacade extends AbstractFacade<ParticipanteProyecto> implements ParticipanteProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipanteProyectoFacade() {
        super(ParticipanteProyecto.class);
    }
    
    @Override
    public List<ParticipanteProyecto> buscarParticipantesProyecto(Proyecto proyecto){
        Query q = em.createQuery("SELECT p FROM ParticipanteProyecto p WHERE p.proyecto = :proyecto");
        q.setParameter("proyecto", proyecto);
        return q.getResultList();
    }
}
