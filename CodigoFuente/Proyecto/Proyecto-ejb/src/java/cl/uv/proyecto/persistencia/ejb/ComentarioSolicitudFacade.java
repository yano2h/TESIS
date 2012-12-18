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
import javax.persistence.TypedQuery;

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
    public void remove(ComentarioSolicitud c){
        super.remove(find(c.getIdComentario()));
    }
    
    @Override
    public List<ComentarioSolicitud> buscarComentariosPorSolicitud(Long idSolicitudRequerimiento){
        System.out.println("/*Buscar Comentarios*/");
        TypedQuery<ComentarioSolicitud> q = em.createNamedQuery("ComentarioSolicitud.findByIdSolicitud", ComentarioSolicitud.class);
        q.setParameter("id", idSolicitudRequerimiento);
        return q.getResultList();
    }
    
}
