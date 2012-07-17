/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.EstadoSolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.TipoPrioridad;
import cl.uv.proyecto.persistencia.entidades.TipoSolicitudRequerimiento;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbBusquedaSolicitud implements Serializable {

    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    
    private List<SolicitudRequerimiento> resultadosBusqueda;
    private SolicitudRequerimiento prototipoSolicitud;
    
    private String codigoConsulta;
    private String asunto;
    private String mensaje;
    
    private Area areaResponsable;
    private TipoPrioridad prioridad;
    private EstadoSolicitudRequerimiento estadoSolicitudRequerimiento;
    private TipoSolicitudRequerimiento tipoSolicitudRequerimiento;
    
    private Date maxFechaEnvio;
    private Date minFechaEnvio;

    
    public MbBusquedaSolicitud() {
        prototipoSolicitud = new SolicitudRequerimiento();
        codigoConsulta = "";
        asunto = "";
        mensaje = "";
    }
    
    @PostConstruct
    private void init(){
        resultadosBusqueda = solicitudFacade.findAll();
    }

    public List<SolicitudRequerimiento> getResultadosBusqueda() {
        return resultadosBusqueda;
    }

    public void setResultadosBusqueda(List<SolicitudRequerimiento> resultadosBusqueda) {
        this.resultadosBusqueda = resultadosBusqueda;
    }

    public Area getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(Area areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    public void setCodigoConsulta(String codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }

    public EstadoSolicitudRequerimiento getEstadoSolicitudRequerimiento() {
        return estadoSolicitudRequerimiento;
    }

    public void setEstadoSolicitudRequerimiento(EstadoSolicitudRequerimiento estadoSolicitudRequerimiento) {
        this.estadoSolicitudRequerimiento = estadoSolicitudRequerimiento;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TipoPrioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(TipoPrioridad prioridad) {
        this.prioridad = prioridad;
    }

    public TipoSolicitudRequerimiento getTipoSolicitudRequerimiento() {
        return tipoSolicitudRequerimiento;
    }

    public void setTipoSolicitudRequerimiento(TipoSolicitudRequerimiento tipoSolicitudRequerimiento) {
        this.tipoSolicitudRequerimiento = tipoSolicitudRequerimiento;
    }

    public SolicitudRequerimiento getPrototipoSolicitud() {
        return prototipoSolicitud;
    }

    public void setPrototipoSolicitud(SolicitudRequerimiento prototipoSolicitud) {
        this.prototipoSolicitud = prototipoSolicitud;
    }

    public Date getMaxFechaEnvio() {
        return maxFechaEnvio;
    }

    public void setMaxFechaEnvio(Date maxFechaEnvio) {
        this.maxFechaEnvio = maxFechaEnvio;
    }

    public Date getMinFechaEnvio() {
        return minFechaEnvio;
    }

    public void setMinFechaEnvio(Date minFechaEnvio) {
        this.minFechaEnvio = minFechaEnvio;
    }
    
    public void filtrar(){
        prototipoSolicitud.setCodigoConsulta(codigoConsulta);
        prototipoSolicitud.setAsunto(asunto);
        prototipoSolicitud.setMensaje(mensaje);
        prototipoSolicitud.setAreaResponsable(areaResponsable);
        prototipoSolicitud.setEstadoSolicitud(estadoSolicitudRequerimiento);
        prototipoSolicitud.setTipoSolicitud(tipoSolicitudRequerimiento);
        prototipoSolicitud.setPrioridadSolicitud(prioridad);
        maxFechaEnvio = (maxFechaEnvio!=null)? new Date(maxFechaEnvio.getTime()+86400000):null;
        System.out.println(maxFechaEnvio);
        resultadosBusqueda = solicitudFacade.buscarSolicitudPorFiltros(prototipoSolicitud, minFechaEnvio, maxFechaEnvio);
    }
    
    public Date getMaxDateCalendarFechaEnvio(){
        return new Date();
    }
    
    public Date getMinDateCalendarFechaEnvio(){

            return minFechaEnvio;

    }
    
}
