/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.TipoSolicitudRequerimiento;
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
public class TipoSolicitudRequerimientoFacade extends AbstractFacade<TipoSolicitudRequerimiento> implements TipoSolicitudRequerimientoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoSolicitudRequerimientoFacade() {
        super(TipoSolicitudRequerimiento.class);
    }

    @Override
    public List<TipoSolicitudRequerimiento> findAll() {
        TypedQuery<TipoSolicitudRequerimiento> q = getEntityManager().createNamedQuery("TipoSolicitudRequerimiento.findAll",TipoSolicitudRequerimiento.class);
        return q.getResultList();
    }
    
}
