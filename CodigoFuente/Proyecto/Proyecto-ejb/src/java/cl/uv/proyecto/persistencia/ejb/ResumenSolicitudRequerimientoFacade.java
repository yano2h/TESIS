/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.ResumenSolicitudRequerimiento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alejandro
 */
@Stateless
public class ResumenSolicitudRequerimientoFacade extends AbstractFacade<ResumenSolicitudRequerimiento> implements ResumenSolicitudRequerimientoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResumenSolicitudRequerimientoFacade() {
        super(ResumenSolicitudRequerimiento.class);
    }

    @Override
    public List<ResumenSolicitudRequerimiento> buscarPorArea(Area a) {
         Query q = em.createNamedQuery("ResumenSolicitudRequerimiento.findByAreaResponsable");
         q.setParameter("areaResponsable", a);
         List<ResumenSolicitudRequerimiento> resultQuery = q.getResultList();
         return resultQuery;
    }

    @Override
    public List<ResumenSolicitudRequerimiento> buscarPorResponsable(Integer rut) {
         Query q = em.createNamedQuery("ResumenSolicitudRequerimiento.findByResponsable");
         q.setParameter("responsable", rut);
         List<ResumenSolicitudRequerimiento> resultQuery = q.getResultList();
         return resultQuery;
    }

    
    
}
