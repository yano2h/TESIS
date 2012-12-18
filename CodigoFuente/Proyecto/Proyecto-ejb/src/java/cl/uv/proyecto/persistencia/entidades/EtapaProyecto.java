/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 *
 * @author Alejandro
 */
@Cacheable
@Entity
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "ETAPA_PROYECTO")
@NamedQueries({
    @NamedQuery(name = "EstapaProyecto.findAll", query = "SELECT e FROM EtapaProyecto e",hints={@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
    @NamedQuery(name = "EstapaProyecto.findByIdEtapaProyecto", query = "SELECT e FROM EtapaProyecto e WHERE e.idEtapaProyecto = :idEtapaProyecto",hints={@QueryHint(name = "org.hibernate.cacheable", value = "true")})})
public class EtapaProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_etapa_proyecto")
    private Short idEtapaProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_etapa_proyecto")
    private String nombreEtapaProyecto;

    public EtapaProyecto() {
    }

    public EtapaProyecto(Short idEtapaProyecto) {
        this.idEtapaProyecto = idEtapaProyecto;
    }

    public Short getIdEtapaProyecto() {
        return idEtapaProyecto;
    }

    public void setIdEtapaProyecto(Short idEtapaProyecto) {
        this.idEtapaProyecto = idEtapaProyecto;
    }

    public String getNombreEtapaProyecto() {
        return nombreEtapaProyecto;
    }

    public void setNombreEtapaProyecto(String nombreEtapaProyecto) {
        this.nombreEtapaProyecto = nombreEtapaProyecto;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEtapaProyecto != null ? idEtapaProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtapaProyecto)) {
            return false;
        }
        EtapaProyecto other = (EtapaProyecto) object;
        if ((this.idEtapaProyecto == null && other.idEtapaProyecto != null) || (this.idEtapaProyecto != null && !this.idEtapaProyecto.equals(other.idEtapaProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.EtapaProyecto[ idEtapaProyecto=" + idEtapaProyecto + " ]";
    }
    
}
