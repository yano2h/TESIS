/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Jano
 */
@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> implements ProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }
    
    @Override
    public List<Proyecto> buscarProyectosPorArea(Area area){
        Query q = em.createQuery("SELECT DISTINCT p FROM Proyecto p, ParticipanteProyecto pp WHERE p = pp.proyecto AND pp.participante.area = :area ORDER BY p.fechaInicio DESC");
        q.setParameter("area", area);
        return q.getResultList();
    }
    
    @Override
    public List<Proyecto> buscarProyectoPorFiltros(Proyecto proyecto, Date minDate, Date maxDate) {
        
        //Create query
        String select = "SELECT p FROM Proyecto p ";
        String where = "WHERE ";
        String condicion = "";
        String order = " ORDER BY p.fechaInicio DESC";
        
        if (!proyecto.getCodigoInterno().isEmpty()) {
            condicion += " p.codigoInterno LIKE :codigoInterno ";
        }

        if (!proyecto.getNombre().isEmpty()) {
            condicion += (condicion.isEmpty()) ? " p.nombre LIKE :nombre" : " AND p.nombre LIKE :nombre";
        }

        if (!proyecto.getDescripcion().isEmpty()) {
            condicion += (condicion.isEmpty()) ? " p.descripcion LIKE :descripcion" : " AND p.descripcion LIKE :descripcion";
        }

        if (proyecto.getEstadoProyecto() != null) {
            condicion += (condicion.isEmpty()) ? " p.estadoProyecto = :estadoProyecto" : " AND p.estadoProyecto = :estadoProyecto";
        }

        if (proyecto.getTipoProyecto() != null) {
            condicion += (condicion.isEmpty()) ? " p.tipoProyecto = :tipoProyecto" : " AND p.tipoProyecto = :tipoProyecto";
        }
        
        if (minDate != null) {
            condicion += (condicion.isEmpty()) ? " p.fechaInicio >= :minFechaInicio" : " AND p.fechaInicio >= :minFechaInicio";
        }
        
        if (maxDate != null) {
            condicion += (condicion.isEmpty()) ? " p.fechaInicio <= :maxFechaInicio" : " AND p.fechaInicio <= :maxFechaInicio";
        }

        //Set Paramenters
        String consulta = (condicion.isEmpty())? select+order : select+where+condicion+order;        
        Query q = em.createQuery(consulta);
        
        if (!proyecto.getCodigoInterno().isEmpty()) {
            q.setParameter("codigoInterno", "%"+proyecto.getCodigoInterno()+"%");
        }

        if (!proyecto.getNombre().isEmpty()) {
            String nombre = "%"+proyecto.getNombre()+"%";
            nombre = nombre.replaceAll(" ", "%");
            q.setParameter("nombre", nombre);
        }

        if (!proyecto.getDescripcion().isEmpty()) {
            String descripcion = "%"+proyecto.getDescripcion()+"%";
            descripcion = descripcion.replaceAll(" ", "%");
            q.setParameter("descripcion", descripcion);
        }

        if (proyecto.getEstadoProyecto() != null) {
            q.setParameter("estadoProyecto", proyecto.getEstadoProyecto());
        }


        if (proyecto.getTipoProyecto() != null) {
            q.setParameter("tipoProyecto", proyecto.getTipoProyecto());
        }
        
        if (minDate != null) {
            q.setParameter("minFechaInicio", minDate, TemporalType.DATE);
        }
        
        if (maxDate != null) {
            q.setParameter("maxFechaInicio", maxDate, TemporalType.DATE);
        }
        
        return q.getResultList();
    }
}
