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
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "ESTADO_SOLICITUD_REQ")
@NamedQueries({
    @NamedQuery(name = "EstadoSolicitudRequerimiento.findAll", query = "SELECT e FROM EstadoSolicitudRequerimiento e"),
    @NamedQuery(name = "EstadoSolicitudRequerimiento.findByIdEstadoSolicitudRequerimiento", query = "SELECT e FROM EstadoSolicitudRequerimiento e WHERE e.idEstadoSolicitudRequerimiento = :idEstadoSolicitudRequerimiento")})
public class EstadoSolicitudRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estado_solicitud_req")
    private Short idEstadoSolicitudRequerimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_estado_solicitud")
    private String nombreEstadoSolicitud;
    @Size(max = 255)
    @Column(name = "descripcion_estado")
    private String descripcionEstado;
    
    /* Ver si borrar */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoSolicitud", orphanRemoval=true)
    private List<SolicitudRequerimiento> solicitudRequerimientoList;

    public EstadoSolicitudRequerimiento() {
    }

    public EstadoSolicitudRequerimiento(Short idEstadoSolicitudRequerimiento) {
        this.idEstadoSolicitudRequerimiento = idEstadoSolicitudRequerimiento;
    }

    public EstadoSolicitudRequerimiento(Short idEstadoSolicitudRequerimiento, String nombreEstadoSolicitud) {
        this.idEstadoSolicitudRequerimiento = idEstadoSolicitudRequerimiento;
        this.nombreEstadoSolicitud = nombreEstadoSolicitud;
    }

    public Short getIdEstadoSolicitudRequerimiento() {
        return idEstadoSolicitudRequerimiento;
    }

    public void setIdEstadoSolicitudRequerimiento(Short idEstadoSolicitudRequerimiento) {
        this.idEstadoSolicitudRequerimiento = idEstadoSolicitudRequerimiento;
    }

    public String getNombreEstadoSolicitud() {
        return nombreEstadoSolicitud;
    }

    public void setNombreEstadoSolicitud(String nombreEstadoSolicitud) {
        this.nombreEstadoSolicitud = nombreEstadoSolicitud;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public List<SolicitudRequerimiento> getSolicitudRequerimientoList() {
        return solicitudRequerimientoList;
    }

    public void setSolicitudRequerimientoList(List<SolicitudRequerimiento> solicitudRequerimientoList) {
        this.solicitudRequerimientoList = solicitudRequerimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoSolicitudRequerimiento != null ? idEstadoSolicitudRequerimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoSolicitudRequerimiento)) {
            return false;
        }
        EstadoSolicitudRequerimiento other = (EstadoSolicitudRequerimiento) object;
        if ((this.idEstadoSolicitudRequerimiento == null && other.idEstadoSolicitudRequerimiento != null) || (this.idEstadoSolicitudRequerimiento != null && !this.idEstadoSolicitudRequerimiento.equals(other.idEstadoSolicitudRequerimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.EstadoSolicitudRequerimiento[ idEstadoSolicitudRequerimiento=" + idEstadoSolicitudRequerimiento + " ]";
    }
    
}
