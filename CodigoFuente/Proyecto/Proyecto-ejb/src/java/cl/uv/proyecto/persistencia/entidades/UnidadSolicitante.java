/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "UNIDAD_SOLICITANTE")
@NamedQueries({
    @NamedQuery(name = "UnidadSolicitante.findAll", query = "SELECT u FROM UnidadSolicitante u"),
    @NamedQuery(name = "UnidadSolicitante.findByIdUnidadSolicitante", query = "SELECT u FROM UnidadSolicitante u WHERE u.idUnidadSolicitante = :idUnidadSolicitante")})
public class UnidadSolicitante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_unidad_solicitante")
    private Integer idUnidadSolicitante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre_unidad_solicitante")
    private String nombreUnidadSolicitante;

    public Integer getIdUnidadSolicitante() {
        return idUnidadSolicitante;
    }

    public void setIdUnidadSolicitante(Integer idUnidadSolicitante) {
        this.idUnidadSolicitante = idUnidadSolicitante;
    }

    public String getNombreUnidadSolicitante() {
        return nombreUnidadSolicitante;
    }

    public void setNombreUnidadSolicitante(String nombreUnidadSolicitante) {
        this.nombreUnidadSolicitante = nombreUnidadSolicitante;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadSolicitante != null ? idUnidadSolicitante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadSolicitante)) {
            return false;
        }
        UnidadSolicitante other = (UnidadSolicitante) object;
        if ((this.idUnidadSolicitante == null && other.idUnidadSolicitante != null) || (this.idUnidadSolicitante != null && !this.idUnidadSolicitante.equals(other.idUnidadSolicitante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.UnidadSolicitante[ idUnidadSolicitante=" + idUnidadSolicitante + " ]";
    }
    
}
