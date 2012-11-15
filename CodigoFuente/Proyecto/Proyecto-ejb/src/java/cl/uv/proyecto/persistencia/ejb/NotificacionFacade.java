/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jano
 */
@Stateless
public class NotificacionFacade extends AbstractFacade<Notificacion> implements NotificacionFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionFacade() {
        super(Notificacion.class);
    }
    
    @Override
    public List<Notificacion> buscarNotificacionPorDestinatario(Funcionario destinatario){
        Query q = em.createQuery("SELECT n FROM Notificacion n WHERE n.destinatario = :destinatario ORDER BY n.fecha DESC");
        q.setParameter("destinatario", destinatario);
        q.setMaxResults(100);
        return q.getResultList();
    }
    
    @Override
    @Asynchronous
    public void marcarNotificacionRevisada(Notificacion n){
        n.setRevisada(true);
        getEntityManager().merge(n);
    }
}
