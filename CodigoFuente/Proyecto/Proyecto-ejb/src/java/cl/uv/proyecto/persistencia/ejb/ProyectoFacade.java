/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

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
    public void remove(Proyecto p){
        super.remove(find(p.getIdProyecto()));
    }
    
    @Override
    public List<Proyecto> buscarProyectosPorArea(Area area){
        TypedQuery<Proyecto> q = em.createNamedQuery("Proyecto.findByArea", Proyecto.class);
        q.setParameter("areaResponsable", area);
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
        
        if (proyecto.getAreaResponsable()!= null) {
            condicion += (condicion.isEmpty()) ? " p.areaResponsable = :areaResponsable" : " AND p.areaResponsable = :areaResponsable";
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
        
        if (proyecto.getAreaResponsable() != null) {
            q.setParameter("areaResponsable", proyecto.getAreaResponsable());
        }
        
        if (minDate != null) {
            q.setParameter("minFechaInicio", minDate, TemporalType.DATE);
        }
        
        if (maxDate != null) {
            q.setParameter("maxFechaInicio", maxDate, TemporalType.DATE);
        }
        
        return q.getResultList();
    }
    
    @Override
    public List<Proyecto> buscarProyectoPorTareaFuncionario(FuncionarioDisico funcionarioDisico, Integer idTarea){
        Query q = em.createQuery("SELECT t.proyecto FROM TareaScmProyecto t WHERE t.responsable = :funcionario AND t.tareaScm.idTareaScm = :idTareScm ");
        q.setParameter("funcionario", funcionarioDisico);
        q.setParameter("idTareScm", idTarea);
        return q.getResultList(); 
    }

    @Override
    public List<Proyecto> buscarProyectosPorParticipante(FuncionarioDisico funcionarioDisico) {
        Query q = em.createQuery("SELECT DISTINCT p.proyecto FROM ParticipanteProyecto p WHERE p.participanteProyectoPK.rutParticipante = :rutParticipant");
        q.setParameter("rutParticipant", funcionarioDisico.getRut());
        return q.getResultList();
    }
    
    @Override
    public String buscarMaximoCodigo(String prefijo){
        Query q = em.createQuery("SELECT p.codigoInterno FROM Proyecto p WHERE p.codigoInterno LIKE :prefijo ORDER BY p.codigoInterno DESC");
        q.setParameter("prefijo", prefijo+"%");
        List<String> l = q.getResultList();
        if (!l.isEmpty()) {
            return l.get(0);
            
        }
        return "0";
    }
    
    @Override
    public boolean existCode(String code){
        Query q = em.createNamedQuery("Proyecto.findByCodigoInterno");
        q.setParameter("codigoInterno", code);
        return !q.getResultList().isEmpty();
    }
}
