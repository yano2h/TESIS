/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.FormularioImplementacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jano
 */
@Stateless
public class FormularioImplementacionFacade extends AbstractFacade<FormularioImplementacion> implements FormularioImplementacionFacadeLocal {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormularioImplementacionFacade() {
        super(FormularioImplementacion.class);
    }
    
}
