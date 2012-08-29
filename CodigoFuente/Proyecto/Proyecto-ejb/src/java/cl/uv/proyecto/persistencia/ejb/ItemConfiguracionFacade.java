/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.ItemConfiguracion;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
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
public class ItemConfiguracionFacade extends AbstractFacade<ItemConfiguracion> implements ItemConfiguracionFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemConfiguracionFacade() {
        super(ItemConfiguracion.class);
    }
    
    @Override
    public List<ItemConfiguracion> buscarItemsPorProyecto(Proyecto proyecto){
        Query q = em.createQuery("SELECT i FROM ItemConfiguracion i WHERE i.proyecto = :proyecto");
        q.setParameter("proyecto", proyecto);
        return q.getResultList();
    }
    
    @Override
    public void guardarItems(List<ItemConfiguracion> listaItemsConfiguracion){
        for (ItemConfiguracion ic : listaItemsConfiguracion) {
           if( ic.getIdItemConfiguracion() != null  ){
               edit(ic);
           }else{
               create(ic);
           }
       }
    }
}
