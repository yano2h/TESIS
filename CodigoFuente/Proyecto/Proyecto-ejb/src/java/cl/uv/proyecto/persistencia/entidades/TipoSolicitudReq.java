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
@Table(name = "TIPO_SOLICITUD_REQ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoSolicitudReq.findAll", query = "SELECT t FROM TipoSolicitudReq t"),
    @NamedQuery(name = "TipoSolicitudReq.findByIdTipoSolicitudReq", query = "SELECT t FROM TipoSolicitudReq t WHERE t.idTipoSolicitudReq = :idTipoSolicitudReq"),
    @NamedQuery(name = "TipoSolicitudReq.findByNombreTipoSolicitud", query = "SELECT t FROM TipoSolicitudReq t WHERE t.nombreTipoSolicitud = :nombreTipoSolicitud"),
    @NamedQuery(name = "TipoSolicitudReq.findByDescripcionTipo", query = "SELECT t FROM TipoSolicitudReq t WHERE t.descripcionTipo = :descripcionTipo")})
public class TipoSolicitudReq implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_solicitud_req", nullable = false)
    private Short idTipoSolicitudReq;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_solicitud", nullable = false, length = 45)
    private String nombreTipoSolicitud;
    @Size(max = 255)
    @Column(name = "descripcion_tipo", length = 255)
    private String descripcionTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoSolicitudReq")
    private List<SolicitudRequerimiento> solicitudRequerimientoList;

    public TipoSolicitudReq() {
    }

    public TipoSolicitudReq(Short idTipoSolicitudReq) {
        this.idTipoSolicitudReq = idTipoSolicitudReq;
    }

    public TipoSolicitudReq(Short idTipoSolicitudReq, String nombreTipoSolicitud) {
        this.idTipoSolicitudReq = idTipoSolicitudReq;
        this.nombreTipoSolicitud = nombreTipoSolicitud;
    }

    public Short getIdTipoSolicitudReq() {
        return idTipoSolicitudReq;
    }

    public void setIdTipoSolicitudReq(Short idTipoSolicitudReq) {
        this.idTipoSolicitudReq = idTipoSolicitudReq;
    }

    public String getNombreTipoSolicitud() {
        return nombreTipoSolicitud;
    }

    public void setNombreTipoSolicitud(String nombreTipoSolicitud) {
        this.nombreTipoSolicitud = nombreTipoSolicitud;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    @XmlTransient
    public List<SolicitudRequerimiento> getSolicitudRequerimientoList() {
        return solicitudRequerimientoList;
    }

    public void setSolicitudRequerimientoList(List<SolicitudRequerimiento> solicitudRequerimientoList) {
        this.solicitudRequerimientoList = solicitudRequerimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSolicitudReq != null ? idTipoSolicitudReq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSolicitudReq)) {
            return false;
        }
        TipoSolicitudReq other = (TipoSolicitudReq) object;
        if ((this.idTipoSolicitudReq == null && other.idTipoSolicitudReq != null) || (this.idTipoSolicitudReq != null && !this.idTipoSolicitudReq.equals(other.idTipoSolicitudReq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.TipoSolicitudReq[ idTipoSolicitudReq=" + idTipoSolicitudReq + " ]";
    }
    
}
