/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.ejb.EstadoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TipoPrioridadFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.EstadoSolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.TipoPrioridad;
import java.util.Date;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Jano
 */
@Stateless
public class SolicitudRequerimientoEJB implements SolicitudRequerimientoEJBLocal {
    
    private long MOD = 1000L;
    private long DESPLAZAMIENTO = 10000000000L;
    private long TIME_INIT = 1341773584868L;
    
    private short ESTADO_INICIAL = 0;
    private short PRIORIDAD_INICIAL = 0;
    private short ESTADO_RECHAZADA = 1;

    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private EstadoSolicitudRequerimientoFacadeLocal estadoSolicitudFacade;
    @EJB
    private TipoPrioridadFacadeLocal tipoPrioridadFacade;

    @Override
    public String generarCodigoConsulta(SolicitudRequerimiento solicitud) {
        
        String codigo = "";
        
        Random rand = new Random();
        rand.setSeed(new Date().getTime());
        
        long base = ( rand.nextInt(8) + 1 ) * MOD;
        long time = (solicitud.getFechaEnvio().getTime() - TIME_INIT)/MOD;
        base += solicitud.getSolicitante().getRut()%MOD;
        base = base*DESPLAZAMIENTO + time;
        
        return generarCodigo(base);
    }

    @Override
    public boolean validarCodigoConsulta(String codigoConsulta) {
        return false;
    }

    @Override
    public String enviarSolicitud(SolicitudRequerimiento solicitud, Funcionario solicitante) {
        Date fechaActual = new Date();
        solicitud.setFechaEnvio(fechaActual);
        solicitud.setFechaUltimaActualizacion(fechaActual);
        solicitud.setSolicitante(solicitante);
        TipoPrioridad t = tipoPrioridadFacade.find(PRIORIDAD_INICIAL);
        System.out.println(t.getIdTipoPrioridad());
        System.out.println(t.getNombrePrioridad());
        solicitud.setPrioridadSolicitud(tipoPrioridadFacade.find(PRIORIDAD_INICIAL));
        EstadoSolicitudRequerimiento e = estadoSolicitudFacade.find(ESTADO_INICIAL);
        System.out.println(e.getIdEstadoSolicitudRequerimiento());
        System.out.println(e.getNombreEstadoSolicitud());
        solicitud.setEstadoSolicitud(estadoSolicitudFacade.find(ESTADO_INICIAL));
        solicitud.setCodigoConsulta(generarCodigoConsulta(solicitud));
        solicitudFacade.create(solicitud);
        return solicitud.getCodigoConsulta();
    }

    public void rechazarSolicitud(SolicitudRequerimiento solicitud){
        EstadoSolicitudRequerimiento estado = estadoSolicitudFacade.find(ESTADO_RECHAZADA);
        solicitud.setEstadoSolicitud(estado);
        solicitudFacade.edit(solicitud);
    }
    
    @Override
    public String generarCodigo(long num) {
        String code = "";
        String s = "1qQaAzZ2wWsSxX3eEdDcC4rRfFvV5TtgGbB6yYhHnN7uUjJmM8iIkK9oOlpP0";
        //String s = "01234zxcvbnmasdfghjklqwertyuiop56789ZXCVBNMASDFGHJKLQWERTYUIOP";
        int length = s.length()-1;
        while (num > 0) {
            int n = (int) (num % length);
            code += String.valueOf(s.charAt(n));
            num = num / length;
        }
        return code;
    }
}

