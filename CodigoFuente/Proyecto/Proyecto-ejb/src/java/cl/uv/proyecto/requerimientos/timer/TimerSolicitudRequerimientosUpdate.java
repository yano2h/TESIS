/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.timer;

import cl.uv.model.base.utils.Resources;
import java.util.Date;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alejandro
 */
@Singleton
@LocalBean
public class TimerSolicitudRequerimientosUpdate {
    @PersistenceContext(unitName = "Proyecto-ejbPU")
    private EntityManager em;
    
    @Schedule(hour= "*", minute= "*/30", second= "0")
    public void buscarSolicitudesVencidas(){
        
       String query = "UPDATE SolicitudRequerimiento s "
                     + "SET s.estadoSolicitud.idEstadoSolicitudRequerimiento = :estadoVencida "
                     + "WHERE s.fechaVencimiento < :currentDate AND "
                     + "s.estadoSolicitud.idEstadoSolicitudRequerimiento != :estadoCerrada AND "
                     + "s.estadoSolicitud.idEstadoSolicitudRequerimiento != :estadoCerradaSinResp AND "
                     + "s.estadoSolicitud.idEstadoSolicitudRequerimiento != :estadoVencida";
        Query q = em.createQuery(query);
        q.setParameter("currentDate", new Date());
        q.setParameter("estadoVencida", Resources.getValueShort("Estados", "EstadoSR_VENCIDA"));
        q.setParameter("estadoCerrada", Resources.getValueShort("Estados", "EstadoSR_CERRADA"));
        q.setParameter("estadoCerradaSinResp", Resources.getValueShort("Estados", "EstadoSR_FINALIZADA_SIN_RESPUESTA"));

        int cantUpdate = q.executeUpdate();
        System.out.println("Execute buscarSolicitudesVencidas, "+cantUpdate+ " solicitudes fueron modificadas.");
    }
    
}
