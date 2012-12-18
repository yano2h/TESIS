/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
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

    @Override
    public List<ArchivoSolicitudRequerimiento> buscarArchivosPorSolicitud(SolicitudRequerimiento s) {
        TypedQuery<ArchivoSolicitudRequerimiento> q = em.createNamedQuery("ArchivoSolicitudRequerimiento.findByIdSolicitud", ArchivoSolicitudRequerimiento.class);
        q.setParameter("idSolicitud", s.getIdSolicitudRequerimiento());
        return q.getResultList();
    }
    
}
