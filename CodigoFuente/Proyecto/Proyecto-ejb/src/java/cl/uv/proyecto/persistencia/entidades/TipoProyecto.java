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
@Table(name = "TIPO_PROYECTO")
@NamedQueries({
    @NamedQuery(name = "TipoProyecto.findAll", query = "SELECT t FROM TipoProyecto t"),
    @NamedQuery(name = "TipoProyecto.findByIdTipoProyecto", query = "SELECT t FROM TipoProyecto t WHERE t.idTipoProyecto = :idTipoProyecto")})
public class TipoProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_proyecto")
    private Short idTipoProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_proyecto")
    private String nombreTipoProyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProyecto")
    private List<Proyecto> proyectoList;

    public TipoProyecto() {
    }

    public TipoProyecto(Short idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
    }

    public TipoProyecto(Short idTipoProyecto, String nombreTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
        this.nombreTipoProyecto = nombreTipoProyecto;
    }

    public Short getIdTipoProyecto() {
        return idTipoProyecto;
    }

    public void setIdTipoProyecto(Short idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
    }

    public String getNombreTipoProyecto() {
        return nombreTipoProyecto;
    }

    public void setNombreTipoProyecto(String nombreTipoProyecto) {
        this.nombreTipoProyecto = nombreTipoProyecto;
    }

    public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoProyecto != null ? idTipoProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProyecto)) {
            return false;
        }
        TipoProyecto other = (TipoProyecto) object;
        if ((this.idTipoProyecto == null && other.idTipoProyecto != null) || (this.idTipoProyecto != null && !this.idTipoProyecto.equals(other.idTipoProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.TipoProyecto[ idTipoProyecto=" + idTipoProyecto + " ]";
    }
    
}
