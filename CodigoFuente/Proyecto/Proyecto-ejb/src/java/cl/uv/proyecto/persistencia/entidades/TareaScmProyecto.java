/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jano
 */
@Entity
@Table(name = "TAREA_SCM_PROYECTO")
@NamedQueries({
    @NamedQuery(name = "TareaScmProyecto.findAll", query = "SELECT t FROM TareaScmProyecto t"),
    @NamedQuery(name = "TareaScmProyecto.findByIdTareaScm", query = "SELECT t FROM TareaScmProyecto t WHERE t.tareaScmProyectoPK.idTareaScm = :idTareaScm"),
    @NamedQuery(name = "TareaScmProyecto.findByIdProyecto", query = "SELECT t FROM TareaScmProyecto t WHERE t.tareaScmProyectoPK.idProyecto = :idProyecto")})
public class TareaScmProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TareaScmProyectoPK tareaScmProyectoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_termino")
    @Temporal(TemporalType.DATE)
    private Date fechaTermino;
    @JoinColumn(name = "responsable", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico responsable;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "id_tarea_scm", referencedColumnName = "id_tarea_scm", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TareaScm tareaScm;

    public TareaScmProyecto() {
    }

    public TareaScmProyecto(TareaScmProyectoPK tareaScmProyectoPK) {
        this.tareaScmProyectoPK = tareaScmProyectoPK;
    }

    public TareaScmProyecto(TareaScmProyectoPK tareaScmProyectoPK, Date fechaInicio, Date fechaTermino) {
        this.tareaScmProyectoPK = tareaScmProyectoPK;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
    }

    public TareaScmProyecto(int idTareaScm, int idProyecto) {
        this.tareaScmProyectoPK = new TareaScmProyectoPK(idTareaScm, idProyecto);
    }

    public TareaScmProyectoPK getTareaScmProyectoPK() {
        return tareaScmProyectoPK;
    }

    public void setTareaScmProyectoPK(TareaScmProyectoPK tareaScmProyectoPK) {
        this.tareaScmProyectoPK = tareaScmProyectoPK;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public FuncionarioDisico getResponsable() {
        return responsable;
    }

    public void setResponsable(FuncionarioDisico responsable) {
        this.responsable = responsable;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public TareaScm getTareaScm() {
        return tareaScm;
    }

    public void setTareaScm(TareaScm tareaScm) {
        this.tareaScm = tareaScm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tareaScmProyectoPK != null ? tareaScmProyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaScmProyecto)) {
            return false;
        }
        TareaScmProyecto other = (TareaScmProyecto) object;
        if ((this.tareaScmProyectoPK == null && other.tareaScmProyectoPK != null) || (this.tareaScmProyectoPK != null && !this.tareaScmProyectoPK.equals(other.tareaScmProyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.percistencia.entidades.TareaScmProyecto[ tareaScmProyectoPK=" + tareaScmProyectoPK + " ]";
    }
    
}
