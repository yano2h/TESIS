/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.RegistroBitacora;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @Override
    public Date getFechaUltimoRegistro(Proyecto p) {
        Query q = em.createNativeQuery("SELECT MAX(fecha_registro) FROM registro_bitacora where proyecto = ? group by proyecto");
        q.setParameter(1, p.getIdProyecto());
        return (Date)q.getSingleResult();
    }

    @Override
    public List<String> buscarContraparte(String contraparte) {
        Query q = em.createNativeQuery("SELECT DISTINCT(contraparte_responsable) FROM REGISTRO_BITACORA WHERE contraparte_responsable ILIKE ?");
        q.setParameter(1, contraparte+"%");
        return q.getResultList();
    }
    
    

}
