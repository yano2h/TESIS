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
@Table(name = "archivo_proyecto")
@NamedQueries({
    @NamedQuery(name = "ArchivoProyecto.findAll", query = "SELECT a FROM ArchivoProyecto a"),
    @NamedQuery(name = "ArchivoProyecto.findByIdProyecto", query = "SELECT a FROM ArchivoProyecto a WHERE a.archivoProyectoPK.idProyecto = :idProyecto"),
    @NamedQuery(name = "ArchivoProyecto.findByIdArchivo", query = "SELECT a FROM ArchivoProyecto a WHERE a.archivoProyectoPK.idArchivo = :idArchivo")})
public class ArchivoProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArchivoProyectoPK archivoProyectoPK;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "id_archivo", referencedColumnName = "id_archivo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ArchivoAdjunto archivoAdjunto;

    public ArchivoProyecto() {
    }

    public ArchivoProyecto(ArchivoProyectoPK archivoProyectoPK) {
        this.archivoProyectoPK = archivoProyectoPK;
    }

    public ArchivoProyecto(int idProyecto, long idArchivo) {
        this.archivoProyectoPK = new ArchivoProyectoPK(idProyecto, idArchivo);
    }

    public ArchivoProyectoPK getArchivoProyectoPK() {
        return archivoProyectoPK;
    }

    public void setArchivoProyectoPK(ArchivoProyectoPK archivoProyectoPK) {
        this.archivoProyectoPK = archivoProyectoPK;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
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
        hash += (archivoProyectoPK != null ? archivoProyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoProyecto)) {
            return false;
        }
        ArchivoProyecto other = (ArchivoProyecto) object;
        if ((this.archivoProyectoPK == null && other.archivoProyectoPK != null) || (this.archivoProyectoPK != null && !this.archivoProyectoPK.equals(other.archivoProyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ArchivoProyecto[ archivoProyectoPK=" + archivoProyectoPK + " ]";
    }
    
}
