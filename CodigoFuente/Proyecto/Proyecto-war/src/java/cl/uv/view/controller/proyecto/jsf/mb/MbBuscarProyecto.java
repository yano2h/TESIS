/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.EstadoProyecto;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TipoProyecto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbBuscarProyecto implements Serializable{

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    private String codigo;
    private String nombre;
    private String descripcion;
    private TipoProyecto tipoProyecto;
    private EstadoProyecto estadoProyecto;
    private Date minFechaInicio;
    private Date maxFechaInicio;
    
    private List<Proyecto> resultadosBusqueda;
    private Proyecto prototipoProyecto;
    
    public MbBuscarProyecto() {
        prototipoProyecto = new Proyecto();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Date getMaxFechaInicio() {
        return maxFechaInicio;
    }

    public void setMaxFechaInicio(Date maxFechaInicio) {
        this.maxFechaInicio = maxFechaInicio;
    }

    public Date getMinFechaInicio() {
        return minFechaInicio;
    }

    public void setMinFechaInicio(Date minFechaInicio) {
        this.minFechaInicio = minFechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public List<Proyecto> getResultadosBusqueda() {
        return resultadosBusqueda;
    }
    
     public void filtrar(){
        prototipoProyecto.setCodigoInterno(codigo);
        prototipoProyecto.setNombre(nombre);
        prototipoProyecto.setDescripcion(descripcion);
        prototipoProyecto.setEstadoProyecto(estadoProyecto);
        prototipoProyecto.setTipoProyecto(tipoProyecto);
        maxFechaInicio = (maxFechaInicio!=null)? new Date(maxFechaInicio.getTime()+86400000-1):null;
        System.out.println(maxFechaInicio);
        resultadosBusqueda = proyectoFacade.buscarProyectoPorFiltros(prototipoProyecto, minFechaInicio, maxFechaInicio);
    }
    
}
