/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
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
public class ComentarioSolicitudFacade extends AbstractFacade<ComentarioSolicitud> implements ComentarioSolicitudFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentarioSolicitudFacade() {
        super(ComentarioSolicitud.class);
    }
    
    @Override
    public List<ComentarioSolicitud> buscarComentariosPorSolicitud(Long idSolicitudRequerimiento){
        Query q = em.createQuery("SELECT c FROM ComentarioSolicitud c WHERE c.solicitudRequerimiento.idSolicitudRequerimiento = :id");
        q.setParameter("id", idSolicitudRequerimiento);
        return q.getResultList();
    }
    
}
