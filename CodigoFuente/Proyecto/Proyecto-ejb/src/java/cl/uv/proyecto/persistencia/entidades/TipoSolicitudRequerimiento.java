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
@Table(name = "TIPO_SOLICITUD_REQ")
@NamedQueries({
    @NamedQuery(name = "TipoSolicitudRequerimiento.findAll", query = "SELECT t FROM TipoSolicitudRequerimiento t"),
    @NamedQuery(name = "TipoSolicitudRequerimiento.findByIdTipoSolicitudReq", query = "SELECT t FROM TipoSolicitudRequerimiento t WHERE t.idTipoSolicitudReq = :idTipoSolicitudReq"),
    @NamedQuery(name = "TipoSolicitudRequerimiento.findByNombreTipoSolicitud", query = "SELECT t FROM TipoSolicitudRequerimiento t WHERE t.nombreTipoSolicitud = :nombreTipoSolicitud"),
    @NamedQuery(name = "TipoSolicitudRequerimiento.findByDescripcionTipo", query = "SELECT t FROM TipoSolicitudRequerimiento t WHERE t.descripcionTipo = :descripcionTipo")})
public class TipoSolicitudRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_solicitud_req")
    private Short idTipoSolicitudReq;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_solicitud")
    private String nombreTipoSolicitud;
    @Size(max = 255)
    @Column(name = "descripcion_tipo")
    private String descripcionTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoSolicitudRequerimiento")
    private List<SolicitudRequerimiento> solicitudRequerimientoList;

    public TipoSolicitudRequerimiento() {
    }

    public TipoSolicitudRequerimiento(Short idTipoSolicitudReq) {
        this.idTipoSolicitudReq = idTipoSolicitudReq;
    }

    public TipoSolicitudRequerimiento(Short idTipoSolicitudReq, String nombreTipoSolicitud) {
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
        if (!(object instanceof TipoSolicitudRequerimiento)) {
            return false;
        }
        TipoSolicitudRequerimiento other = (TipoSolicitudRequerimiento) object;
        if ((this.idTipoSolicitudReq == null && other.idTipoSolicitudReq != null) || (this.idTipoSolicitudReq != null && !this.idTipoSolicitudReq.equals(other.idTipoSolicitudReq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.TipoSolicitudRequerimiento[ idTipoSolicitudReq=" + idTipoSolicitudReq + " ]";
    }
    
}
