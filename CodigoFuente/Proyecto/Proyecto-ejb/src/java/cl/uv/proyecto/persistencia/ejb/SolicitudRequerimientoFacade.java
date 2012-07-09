/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
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
public class SolicitudRequerimientoFacade extends AbstractFacade<SolicitudRequerimiento> implements SolicitudRequerimientoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudRequerimientoFacade() {
        super(SolicitudRequerimiento.class);
    }
    
    @Override
    public List<SolicitudRequerimiento> buscarPorSolicitante(Integer rutSolicitante){
        Query q = em.createQuery("SELECT s FROM SolicitudRequerimiento s WHERE s.solicitante.rut = :rut");
        q.setParameter("rut", rutSolicitante);
        return q.getResultList();
    }
    
}
