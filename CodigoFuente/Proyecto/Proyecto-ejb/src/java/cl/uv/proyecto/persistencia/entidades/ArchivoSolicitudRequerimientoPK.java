/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alejandro
 */
@Embeddable
public class ArchivoSolicitudRequerimientoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitud")
    private long idSolicitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_archivo")
    private long idArchivo;

    public ArchivoSolicitudRequerimientoPK() {
    }

    public ArchivoSolicitudRequerimientoPK(long idSolicitud, long idArchivo) {
        this.idSolicitud = idSolicitud;
        this.idArchivo = idArchivo;
    }

    public long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(long idArchivo) {
        this.idArchivo = idArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSolicitud;
        hash += (int) idArchivo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoSolicitudRequerimientoPK)) {
            return false;
        }
        ArchivoSolicitudRequerimientoPK other = (ArchivoSolicitudRequerimientoPK) object;
        if (this.idSolicitud != other.idSolicitud) {
            return false;
        }
        if (this.idArchivo != other.idArchivo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimientoPK[ idSolicitud=" + idSolicitud + ", idArchivo=" + idArchivo + " ]";
    }
    
}
