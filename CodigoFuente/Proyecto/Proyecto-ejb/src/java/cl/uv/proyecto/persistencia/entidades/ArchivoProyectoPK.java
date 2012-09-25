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
public class ArchivoProyectoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proyecto")
    private int idProyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_archivo")
    private long idArchivo;

    public ArchivoProyectoPK() {
    }

    public ArchivoProyectoPK(int idProyecto, long idArchivo) {
        this.idProyecto = idProyecto;
        this.idArchivo = idArchivo;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
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
        hash += (int) idProyecto;
        hash += (int) idArchivo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoProyectoPK)) {
            return false;
        }
        ArchivoProyectoPK other = (ArchivoProyectoPK) object;
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        if (this.idArchivo != other.idArchivo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ArchivoProyectoPK[ idProyecto=" + idProyecto + ", idArchivo=" + idArchivo + " ]";
    }
    
}
