/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.consts.EstadoSC;
import cl.uv.proyecto.persistencia.entidades.FormularioImplementacion;
import javax.ejb.EJB;
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

    @EJB
    private EstadoSolicitudCambioFacadeLocal estadoSolicitudCambioFacade;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormularioImplementacionFacade() {
        super(FormularioImplementacion.class);
    }
    
    @Override
    public void create(FormularioImplementacion f){
          f.getSolicitudCambio().setEstadoSolicitud(estadoSolicitudCambioFacade.find(Resources.getValueShort("Estados", "EstadoSC_IMPLEMENTADA")));
          getEntityManager().merge(f.getSolicitudCambio());
          getEntityManager().persist(f);
    }
}
