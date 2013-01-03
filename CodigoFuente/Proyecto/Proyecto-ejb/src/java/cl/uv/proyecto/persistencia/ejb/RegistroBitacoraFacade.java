/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.RegistroBitacora;
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
public class RegistroBitacoraFacade extends AbstractFacade<RegistroBitacora> implements RegistroBitacoraFacadeLocal {

@PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroBitacoraFacade() {
        super(RegistroBitacora.class);
    }

    @Override
    public List<RegistroBitacora> buscarBitacoraProyecto(Proyecto p) {
        TypedQuery<RegistroBitacora> q = em.createNamedQuery("RegistroBitacora.findByProyecto", RegistroBitacora.class);
        q.setParameter("proyecto", p);
        return q.getResultList();
    }

}
