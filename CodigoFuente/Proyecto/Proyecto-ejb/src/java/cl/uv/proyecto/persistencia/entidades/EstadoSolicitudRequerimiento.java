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
 * @author Alejandro
 */
@Entity
@Table(name = "ESTADO_SOLICITUD_REQ")
@NamedQueries({
    @NamedQuery(name = "EstadoSolicitudRequerimiento.findAll", query = "SELECT e FROM EstadoSolicitudRequerimiento e"),
    @NamedQuery(name = "EstadoSolicitudRequerimiento.findByIdEstadoSolicitudReq", query = "SELECT e FROM EstadoSolicitudRequerimiento e WHERE e.idEstadoSolicitudReq = :idEstadoSolicitudReq"),
    @NamedQuery(name = "EstadoSolicitudRequerimiento.findByNombreEstadoSolicitud", query = "SELECT e FROM EstadoSolicitudRequerimiento e WHERE e.nombreEstadoSolicitud = :nombreEstadoSolicitud"),
    @NamedQuery(name = "EstadoSolicitudRequerimiento.findByDescripcionEstado", query = "SELECT e FROM EstadoSolicitudRequerimiento e WHERE e.descripcionEstado = :descripcionEstado")})
public class EstadoSolicitudRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estado_solicitud_req")
    private Short idEstadoSolicitudReq;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_estado_solicitud")
    private String nombreEstadoSolicitud;
    @Size(max = 255)
    @Column(name = "descripcion_estado")
    private String descripcionEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoSolicitudRequerimiento")
    private List<SolicitudRequerimiento> solicitudRequerimientoList;

    public EstadoSolicitudRequerimiento() {
    }

    public EstadoSolicitudRequerimiento(Short idEstadoSolicitudReq) {
        this.idEstadoSolicitudReq = idEstadoSolicitudReq;
    }

    public EstadoSolicitudRequerimiento(Short idEstadoSolicitudReq, String nombreEstadoSolicitud) {
        this.idEstadoSolicitudReq = idEstadoSolicitudReq;
        this.nombreEstadoSolicitud = nombreEstadoSolicitud;
    }

    public Short getIdEstadoSolicitudReq() {
        return idEstadoSolicitudReq;
    }

    public void setIdEstadoSolicitudReq(Short idEstadoSolicitudReq) {
        this.idEstadoSolicitudReq = idEstadoSolicitudReq;
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
        hash += (idEstadoSolicitudReq != null ? idEstadoSolicitudReq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoSolicitudRequerimiento)) {
            return false;
        }
        EstadoSolicitudRequerimiento other = (EstadoSolicitudRequerimiento) object;
        if ((this.idEstadoSolicitudReq == null && other.idEstadoSolicitudReq != null) || (this.idEstadoSolicitudReq != null && !this.idEstadoSolicitudReq.equals(other.idEstadoSolicitudReq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.EstadoSolicitudRequerimiento[ idEstadoSolicitudReq=" + idEstadoSolicitudReq + " ]";
    }
    
}
