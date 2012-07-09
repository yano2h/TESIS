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
@Table(name = "ROL_PROYECTO")
@NamedQueries({
    @NamedQuery(name = "RolProyecto.findAll", query = "SELECT r FROM RolProyecto r"),
    @NamedQuery(name = "RolProyecto.findByIdRol", query = "SELECT r FROM RolProyecto r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "RolProyecto.findByNombreRol", query = "SELECT r FROM RolProyecto r WHERE r.nombreRol = :nombreRol")})
public class RolProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol")
    private Short idRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_rol")
    private String nombreRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private List<ParticipanteProyecto> participanteProyectoList;

    public RolProyecto() {
    }

    public RolProyecto(Short idRol) {
        this.idRol = idRol;
    }

    public RolProyecto(Short idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public Short getIdRol() {
        return idRol;
    }

    public void setIdRol(Short idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public List<ParticipanteProyecto> getParticipanteProyectoList() {
        return participanteProyectoList;
    }

    public void setParticipanteProyectoList(List<ParticipanteProyecto> participanteProyectoList) {
        this.participanteProyectoList = participanteProyectoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolProyecto)) {
            return false;
        }
        RolProyecto other = (RolProyecto) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.RolProyecto[ idRol=" + idRol + " ]";
    }
    
}
