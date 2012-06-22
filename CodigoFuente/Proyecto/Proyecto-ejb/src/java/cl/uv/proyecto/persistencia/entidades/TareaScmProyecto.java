/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "TAREA_SCM_PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TareaScmProyecto.findAll", query = "SELECT t FROM TareaScmProyecto t"),
    @NamedQuery(name = "TareaScmProyecto.findByIdTareaScmProyecto", query = "SELECT t FROM TareaScmProyecto t WHERE t.idTareaScmProyecto = :idTareaScmProyecto"),
    @NamedQuery(name = "TareaScmProyecto.findByFechaInicio", query = "SELECT t FROM TareaScmProyecto t WHERE t.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "TareaScmProyecto.findByFechaTermino", query = "SELECT t FROM TareaScmProyecto t WHERE t.fechaTermino = :fechaTermino")})
public class TareaScmProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tarea_scm_proyecto", nullable = false)
    private Integer idTareaScmProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "fecha_inicio", nullable = false, length = 10)
    private String fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "fecha_termino", nullable = false, length = 10)
    private String fechaTermino;
    @JoinColumn(name = "id_tarea_scm", referencedColumnName = "id_tarea_scm", nullable = false)
    @ManyToOne(optional = false)
    private TareaScm tareaScm;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", nullable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "rut_responsable", referencedColumnName = "rut", nullable = false)
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico;

    public TareaScmProyecto() {
    }

    public TareaScmProyecto(Integer idTareaScmProyecto) {
        this.idTareaScmProyecto = idTareaScmProyecto;
    }

    public TareaScmProyecto(Integer idTareaScmProyecto, String fechaInicio, String fechaTermino) {
        this.idTareaScmProyecto = idTareaScmProyecto;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
    }

    public Integer getIdTareaScmProyecto() {
        return idTareaScmProyecto;
    }

    public void setIdTareaScmProyecto(Integer idTareaScmProyecto) {
        this.idTareaScmProyecto = idTareaScmProyecto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public TareaScm getTareaScm() {
        return tareaScm;
    }

    public void setTareaScm(TareaScm tareaScm) {
        this.tareaScm = tareaScm;
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
        hash += (idTareaScmProyecto != null ? idTareaScmProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaScmProyecto)) {
            return false;
        }
        TareaScmProyecto other = (TareaScmProyecto) object;
        if ((this.idTareaScmProyecto == null && other.idTareaScmProyecto != null) || (this.idTareaScmProyecto != null && !this.idTareaScmProyecto.equals(other.idTareaScmProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.TareaScmProyecto[ idTareaScmProyecto=" + idTareaScmProyecto + " ]";
    }
    
}
