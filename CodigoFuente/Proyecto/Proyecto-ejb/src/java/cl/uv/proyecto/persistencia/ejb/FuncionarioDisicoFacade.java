/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
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
public class FuncionarioDisicoFacade extends AbstractFacade<FuncionarioDisico> implements FuncionarioDisicoFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FuncionarioDisicoFacade() {
        super(FuncionarioDisico.class);
    }
    
    @Override
    public List<FuncionarioDisico> buscarFuncrionariosPorArea(Area area){
        Query q = em.createQuery("SELECT f FROM FuncionarioDisico f WHERE f.area = :area");
        q.setParameter("area", area); 
        return q.getResultList();
    }
}
