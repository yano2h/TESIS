/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaScmProyecto;
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
public class TareaScmProyectoFacade extends AbstractFacade<TareaScmProyecto> implements TareaScmProyectoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaScmProyectoFacade() {
        super(TareaScmProyecto.class);
    }
    
    @Override
    public List<TareaScmProyecto> buscarTareasSCMPorIdProyecto(Integer idProyecto){
        Query q = em.createNamedQuery("TareaScmProyecto.findByIdProyecto");
        q.setParameter("idProyecto", idProyecto);
        return q.getResultList();
    }
    
    @Override
    public void guardarListaDeTareas(List<TareaScmProyecto> tareasScmProyecto){
    for (TareaScmProyecto t : tareasScmProyecto) {
           if( find(t.getTareaScmProyectoPK()) != null  ){
               edit(t);
           }else{
               create(t);
           }
       }
    }
    
    @Override
    public List<TareaScmProyecto> buscarTareasSCMPorResponsable(FuncionarioDisico responsable){
        Query q = em.createQuery("SELECT t FROM TareaScmProyecto t WHERE t.responsable = :responsable");
        q.setParameter("responsable", responsable);
        return q.getResultList();
    }
 
}
