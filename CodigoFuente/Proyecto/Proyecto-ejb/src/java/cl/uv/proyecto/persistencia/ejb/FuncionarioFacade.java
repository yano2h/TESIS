/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Funcionario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.hibernate.validator.HibernateValidator;

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
    
    public void loadSolicitudesEnviadas(Funcionario f){
        EntityTransaction t = getEntityManager().getTransaction();
        t.begin();
        Hibernate.initialize(f.getSolicitudesRequerimientoEnviadas());
        em.close();
    }
}
