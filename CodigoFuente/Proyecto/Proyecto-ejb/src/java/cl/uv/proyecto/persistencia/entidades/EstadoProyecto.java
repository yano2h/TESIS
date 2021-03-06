/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Jano
 */
@Cacheable
@Entity
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "ESTADO_PROYECTO")
@NamedQueries({
    @NamedQuery(name = "EstadoProyecto.findAll", query = "SELECT e FROM EstadoProyecto e",hints={@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
    @NamedQuery(name = "EstadoProyecto.findByIdEstadoProyecto", query = "SELECT e FROM EstadoProyecto e WHERE e.idEstadoProyecto = :idEstadoProyecto",hints={@QueryHint(name = "org.hibernate.cacheable", value = "true")})})
public class EstadoProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estado_proyecto")
    private Short idEstadoProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_estado_proyecto")
    private String nombreEstadoProyecto;
    
    /* Ver si borrar */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoProyecto")
    private List<Proyecto> proyectoList;

    public EstadoProyecto() {
    }

    public EstadoProyecto(Short idEstadoProyecto) {
        this.idEstadoProyecto = idEstadoProyecto;
    }

    public EstadoProyecto(Short idEstadoProyecto, String nombreEstadoProyecto) {
        this.idEstadoProyecto = idEstadoProyecto;
        this.nombreEstadoProyecto = nombreEstadoProyecto;
    }

    public Short getIdEstadoProyecto() {
        return idEstadoProyecto;
    }

    public void setIdEstadoProyecto(Short idEstadoProyecto) {
        this.idEstadoProyecto = idEstadoProyecto;
    }

    public String getNombreEstadoProyecto() {
        return nombreEstadoProyecto;
    }

    public void setNombreEstadoProyecto(String nombreEstadoProyecto) {
        this.nombreEstadoProyecto = nombreEstadoProyecto;
    }

    public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoProyecto != null ? idEstadoProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoProyecto)) {
            return false;
        }
        EstadoProyecto other = (EstadoProyecto) object;
        if ((this.idEstadoProyecto == null && other.idEstadoProyecto != null) || (this.idEstadoProyecto != null && !this.idEstadoProyecto.equals(other.idEstadoProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.EstadoProyecto[ idEstadoProyecto=" + idEstadoProyecto + " ]";
    }
    
}
