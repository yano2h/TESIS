/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "archivo_solicitud_requerimiento")
@NamedQueries({
    @NamedQuery(name = "ArchivoSolicitudRequerimiento.findAll", query = "SELECT a FROM ArchivoSolicitudRequerimiento a"),
    @NamedQuery(name = "ArchivoSolicitudRequerimiento.findByIdSolicitud", query = "SELECT a FROM ArchivoSolicitudRequerimiento a WHERE a.archivoSolicitudRequerimientoPK.idSolicitud = :idSolicitud"),
    @NamedQuery(name = "ArchivoSolicitudRequerimiento.findByIdArchivo", query = "SELECT a FROM ArchivoSolicitudRequerimiento a WHERE a.archivoSolicitudRequerimientoPK.idArchivo = :idArchivo")})
public class ArchivoSolicitudRequerimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArchivoSolicitudRequerimientoPK archivoSolicitudRequerimientoPK;
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud_req", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SolicitudRequerimiento solicitudRequerimiento;
    @JoinColumn(name = "id_archivo", referencedColumnName = "id_archivo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ArchivoAdjunto archivoAdjunto;

    public ArchivoSolicitudRequerimiento() {
    }

    public ArchivoSolicitudRequerimiento(ArchivoSolicitudRequerimientoPK archivoSolicitudRequerimientoPK) {
        this.archivoSolicitudRequerimientoPK = archivoSolicitudRequerimientoPK;
    }

    public ArchivoSolicitudRequerimiento(long idSolicitud, long idArchivo) {
        this.archivoSolicitudRequerimientoPK = new ArchivoSolicitudRequerimientoPK(idSolicitud, idArchivo);
    }

    public ArchivoSolicitudRequerimiento(SolicitudRequerimiento solicitudRequerimiento, ArchivoAdjunto archivoAdjunto) {
        this.archivoSolicitudRequerimientoPK = new ArchivoSolicitudRequerimientoPK(
                                               solicitudRequerimiento.getIdSolicitudRequerimiento(),
                                               archivoAdjunto.getIdArchivo());
        this.solicitudRequerimiento = solicitudRequerimiento;
        this.archivoAdjunto = archivoAdjunto;
    }

    public ArchivoSolicitudRequerimientoPK getArchivoSolicitudRequerimientoPK() {
        return archivoSolicitudRequerimientoPK;
    }

    public void setArchivoSolicitudRequerimientoPK(ArchivoSolicitudRequerimientoPK archivoSolicitudRequerimientoPK) {
        this.archivoSolicitudRequerimientoPK = archivoSolicitudRequerimientoPK;
    }

    public SolicitudRequerimiento getSolicitudRequerimiento() {
        return solicitudRequerimiento;
    }

    public void setSolicitudRequerimiento(SolicitudRequerimiento solicitudRequerimiento) {
        this.solicitudRequerimiento = solicitudRequerimiento;
    }

    public ArchivoAdjunto getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (archivoSolicitudRequerimientoPK != null ? archivoSolicitudRequerimientoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoSolicitudRequerimiento)) {
            return false;
        }
        ArchivoSolicitudRequerimiento other = (ArchivoSolicitudRequerimiento) object;
        if ((this.archivoSolicitudRequerimientoPK == null && other.archivoSolicitudRequerimientoPK != null) || (this.archivoSolicitudRequerimientoPK != null && !this.archivoSolicitudRequerimientoPK.equals(other.archivoSolicitudRequerimientoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimiento[ archivoSolicitudRequerimientoPK=" + archivoSolicitudRequerimientoPK + " ]";
    }
}
