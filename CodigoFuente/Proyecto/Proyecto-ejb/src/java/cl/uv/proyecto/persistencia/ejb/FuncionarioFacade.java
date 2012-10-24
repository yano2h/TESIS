/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Funcionario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jano
 */
@Stateless
public class FuncionarioFacade extends AbstractFacade<Funcionario> implements FuncionarioFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FuncionarioFacade() {
        super(Funcionario.class);
    }

    @Override
    public Integer buscarRutPorEmail(String email) {
        Query q = em.createQuery("SELECT f.rut FROM Funcionario f WHERE f.correoUv = :correoUv");
        q.setParameter("correoUv", email);
        return (Integer) q.getSingleResult();
    }
    
    
}
