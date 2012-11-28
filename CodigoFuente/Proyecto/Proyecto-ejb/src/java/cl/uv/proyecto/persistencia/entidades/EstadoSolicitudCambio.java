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

/**
 *
 * @author Jano
 */
@Entity
@Table(name = "ESTADO_SOLICITUD_CAMBIO")
@NamedQueries({
    @NamedQuery(name = "EstadoSolicitudCambio.findAll", query = "SELECT e FROM EstadoSolicitudCambio e"),
    @NamedQuery(name = "EstadoSolicitudCambio.findByIdEstadoSolicitudCambio", query = "SELECT e FROM EstadoSolicitudCambio e WHERE e.idEstadoSolicitudCambio = :idEstadoSolicitudCambio")})
public class EstadoSolicitudCambio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estado_solicitud_cambio")
    private Short idEstadoSolicitudCambio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_estado_solicitud")
    private String nombreEstadoSolicitud;
    @Size(max = 255)
    @Column(name = "descripcion_estado")
    private String descripcionEstado;
    
    /* Ver si borrar */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoSolicitud")
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

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

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
