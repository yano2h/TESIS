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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "ESTADO_SOLICITUD_CAMBIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoSolicitudCambio.findAll", query = "SELECT e FROM EstadoSolicitudCambio e"),
    @NamedQuery(name = "EstadoSolicitudCambio.findByIdEstadoSolicitudCambio", query = "SELECT e FROM EstadoSolicitudCambio e WHERE e.idEstadoSolicitudCambio = :idEstadoSolicitudCambio"),
    @NamedQuery(name = "EstadoSolicitudCambio.findByNombreEstadoSolicitud", query = "SELECT e FROM EstadoSolicitudCambio e WHERE e.nombreEstadoSolicitud = :nombreEstadoSolicitud"),
    @NamedQuery(name = "EstadoSolicitudCambio.findByDescripcion", query = "SELECT e FROM EstadoSolicitudCambio e WHERE e.descripcion = :descripcion")})
public class EstadoSolicitudCambio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estado_solicitud_cambio", nullable = false)
    private Short idEstadoSolicitudCambio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_estado_solicitud", nullable = false, length = 45)
    private String nombreEstadoSolicitud;
    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoSolicitudCambio")
    private List<SolicitudCambio> solicitudCambioList;

    public EstadoSolicitudCambio() {
    }

    public EstadoSolicitudCambio(Short idEstadoSolicitudCambio) {
        this.idEstadoSolicitudCambio = idEstadoSolicitudCambio;
    }

    public EstadoSolicitudCambio(Short idEstadoSolicitudCambio, String nombreEstadoSolicitud) {
        this.idEstadoSolicitudCambio = idEstadoSolicitudCambio;
        this.nombreEstadoSolicitud = nombreEstadoSolicitud;
    }

    public Short getIdEstadoSolicitudCambio() {
        return idEstadoSolicitudCambio;
    }

    public void setIdEstadoSolicitudCambio(Short idEstadoSolicitudCambio) {
        this.idEstadoSolicitudCambio = idEstadoSolicitudCambio;
    }

    public String getNombreEstadoSolicitud() {
        return nombreEstadoSolicitud;
    }

    public void setNombreEstadoSolicitud(String nombreEstadoSolicitud) {
        this.nombreEstadoSolicitud = nombreEstadoSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SolicitudCambio> getSolicitudCambioList() {
        return solicitudCambioList;
    }

    public void setSolicitudCambioList(List<SolicitudCambio> solicitudCambioList) {
        this.solicitudCambioList = solicitudCambioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoSolicitudCambio != null ? idEstadoSolicitudCambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoSolicitudCambio)) {
            return false;
        }
        EstadoSolicitudCambio other = (EstadoSolicitudCambio) object;
        if ((this.idEstadoSolicitudCambio == null && other.idEstadoSolicitudCambio != null) || (this.idEstadoSolicitudCambio != null && !this.idEstadoSolicitudCambio.equals(other.idEstadoSolicitudCambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.EstadoSolicitudCambio[ idEstadoSolicitudCambio=" + idEstadoSolicitudCambio + " ]";
    }
    
}
