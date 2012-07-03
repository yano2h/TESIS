/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.EstadoSolicitudCambio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jano
 */
@Stateless
public class EstadoSolicitudCambioFacade extends AbstractFacade<EstadoSolicitudCambio> implements EstadoSolicitudCambioFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoSolicitudCambioFacade() {
        super(EstadoSolicitudCambio.class);
    }
    
}
