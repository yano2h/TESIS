/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimiento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alejandro
 */
@Stateless
public class ArchivoSolicitudRequerimientoFacade extends AbstractFacade<ArchivoSolicitudRequerimiento> implements ArchivoSolicitudRequerimientoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoSolicitudRequerimientoFacade() {
        super(ArchivoSolicitudRequerimiento.class);
    }
    
}
