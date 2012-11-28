/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jano
 */
@Entity
@Table(name = "ENTREGABLE")
@NamedQueries({
    @NamedQuery(name = "Entregable.findAll", query = "SELECT e FROM Entregable e"),
    @NamedQuery(name = "Entregable.findByIdEntregable", query = "SELECT e FROM Entregable e WHERE e.idEntregable = :idEntregable")})
public class Entregable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_entregable")
    private Integer idEntregable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_entregable")
    private String nombreEntregable;
    @JoinColumn(name = "tarea_scm", referencedColumnName = "id_tarea_scm")
    @ManyToOne(optional = false)
    private TareaScm tareaScm;

    public Entregable() {
    }

    public Entregable(Integer idEntregable) {
        this.idEntregable = idEntregable;
    }

    public Entregable(Integer idEntregable, String nombreEntregable) {
        this.idEntregable = idEntregable;
        this.nombreEntregable = nombreEntregable;
    }

    public Integer getIdEntregable() {
        return idEntregable;
    }

    public void setIdEntregable(Integer idEntregable) {
        this.idEntregable = idEntregable;
    }

    public String getNombreEntregable() {
        return nombreEntregable;
    }

    public void setNombreEntregable(String nombreEntregable) {
        this.nombreEntregable = nombreEntregable;
    }

    public TareaScm getTareaScm() {
        return tareaScm;
    }

    public void setTareaScm(TareaScm tareaScm) {
        this.tareaScm = tareaScm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntregable != null ? idEntregable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entregable)) {
            return false;
        }
        Entregable other = (Entregable) object;
        if ((this.idEntregable == null && other.idEntregable != null) || (this.idEntregable != null && !this.idEntregable.equals(other.idEntregable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.Entregable[ idEntregable=" + idEntregable + " ]";
    }
    
}
