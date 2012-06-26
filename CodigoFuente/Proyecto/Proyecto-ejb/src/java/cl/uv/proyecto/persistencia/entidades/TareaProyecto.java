/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "TAREA_PROYECTO")
@NamedQueries({
    @NamedQuery(name = "TareaProyecto.findAll", query = "SELECT t FROM TareaProyecto t"),
    @NamedQuery(name = "TareaProyecto.findByIdTareaProyecto", query = "SELECT t FROM TareaProyecto t WHERE t.idTareaProyecto = :idTareaProyecto"),
    @NamedQuery(name = "TareaProyecto.findByTarea", query = "SELECT t FROM TareaProyecto t WHERE t.tarea = :tarea"),
    @NamedQuery(name = "TareaProyecto.findByFechaCreacion", query = "SELECT t FROM TareaProyecto t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "TareaProyecto.findByFechaInicioPropuesta", query = "SELECT t FROM TareaProyecto t WHERE t.fechaInicioPropuesta = :fechaInicioPropuesta"),
    @NamedQuery(name = "TareaProyecto.findByFechaInicioReal", query = "SELECT t FROM TareaProyecto t WHERE t.fechaInicioReal = :fechaInicioReal"),
    @NamedQuery(name = "TareaProyecto.findByFechaTerminoPropuesta", query = "SELECT t FROM TareaProyecto t WHERE t.fechaTerminoPropuesta = :fechaTerminoPropuesta"),
    @NamedQuery(name = "TareaProyecto.findByFechaTerminoReal", query = "SELECT t FROM TareaProyecto t WHERE t.fechaTerminoReal = :fechaTerminoReal"),
    @NamedQuery(name = "TareaProyecto.findByNivelAvance", query = "SELECT t FROM TareaProyecto t WHERE t.nivelAvance = :nivelAvance"),
    @NamedQuery(name = "TareaProyecto.findByVisible", query = "SELECT t FROM TareaProyecto t WHERE t.visible = :visible")})
public class TareaProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tarea_proyecto")
    private Integer idTareaProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "tarea")
    private String tarea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "fecha_creacion")
    private String fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio_propuesta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioPropuesta;
    @Column(name = "fecha_inicio_real")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioReal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_termino_propuesta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminoPropuesta;
    @Column(name = "fecha_termino_real")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminoReal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel_avance")
    private short nivelAvance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "visible")
    private boolean visible;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "rut_responsable", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico;

    public TareaProyecto() {
    }

    public TareaProyecto(Integer idTareaProyecto) {
        this.idTareaProyecto = idTareaProyecto;
    }

    public TareaProyecto(Integer idTareaProyecto, String tarea, String fechaCreacion, Date fechaInicioPropuesta, Date fechaTerminoPropuesta, short nivelAvance, boolean visible) {
        this.idTareaProyecto = idTareaProyecto;
        this.tarea = tarea;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicioPropuesta = fechaInicioPropuesta;
        this.fechaTerminoPropuesta = fechaTerminoPropuesta;
        this.nivelAvance = nivelAvance;
        this.visible = visible;
    }

    public Integer getIdTareaProyecto() {
        return idTareaProyecto;
    }

    public void setIdTareaProyecto(Integer idTareaProyecto) {
        this.idTareaProyecto = idTareaProyecto;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaInicioPropuesta() {
        return fechaInicioPropuesta;
    }

    public void setFechaInicioPropuesta(Date fechaInicioPropuesta) {
        this.fechaInicioPropuesta = fechaInicioPropuesta;
    }

    public Date getFechaInicioReal() {
        return fechaInicioReal;
    }

    public void setFechaInicioReal(Date fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }

    public Date getFechaTerminoPropuesta() {
        return fechaTerminoPropuesta;
    }

    public void setFechaTerminoPropuesta(Date fechaTerminoPropuesta) {
        this.fechaTerminoPropuesta = fechaTerminoPropuesta;
    }

    public Date getFechaTerminoReal() {
        return fechaTerminoReal;
    }

    public void setFechaTerminoReal(Date fechaTerminoReal) {
        this.fechaTerminoReal = fechaTerminoReal;
    }

    public short getNivelAvance() {
        return nivelAvance;
    }

    public void setNivelAvance(short nivelAvance) {
        this.nivelAvance = nivelAvance;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTareaProyecto != null ? idTareaProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaProyecto)) {
            return false;
        }
        TareaProyecto other = (TareaProyecto) object;
        if ((this.idTareaProyecto == null && other.idTareaProyecto != null) || (this.idTareaProyecto != null && !this.idTareaProyecto.equals(other.idTareaProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.TareaProyecto[ idTareaProyecto=" + idTareaProyecto + " ]";
    }
    
}
