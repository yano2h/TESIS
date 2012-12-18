/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoProyecto;
import cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
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
public class ArchivoProyectoFacade extends AbstractFacade<ArchivoProyecto> implements ArchivoProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoProyectoFacade() {
        super(ArchivoProyecto.class);
    }

    @Override
    public List<ArchivoProyecto> buscarArchivosPorProyecto(Proyecto p) {
        TypedQuery<ArchivoProyecto> q = getEntityManager().createNamedQuery("ArchivoProyecto.findByIdProyecto", ArchivoProyecto.class);
        q.setParameter("idProyecto", p.getIdProyecto());
        return q.getResultList();
    }

    
}
