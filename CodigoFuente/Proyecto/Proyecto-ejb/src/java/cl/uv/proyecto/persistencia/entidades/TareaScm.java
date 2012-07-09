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
@Table(name = "TAREA_SCM")
@NamedQueries({
    @NamedQuery(name = "TareaScm.findAll", query = "SELECT t FROM TareaScm t"),
    @NamedQuery(name = "TareaScm.findByIdTareaScm", query = "SELECT t FROM TareaScm t WHERE t.idTareaScm = :idTareaScm"),
    @NamedQuery(name = "TareaScm.findByNombreTarea", query = "SELECT t FROM TareaScm t WHERE t.nombreTarea = :nombreTarea"),
    @NamedQuery(name = "TareaScm.findByDescripcionTarea", query = "SELECT t FROM TareaScm t WHERE t.descripcionTarea = :descripcionTarea")})
public class TareaScm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tarea_scm")
    private Integer idTareaScm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre_tarea")
    private String nombreTarea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion_tarea")
    private String descripcionTarea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareaScm")
    private List<Entregable> entregableList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareaScm")
    private List<TareaScmProyecto> tareaScmProyectoList;

    public TareaScm() {
    }

    public TareaScm(Integer idTareaScm) {
        this.idTareaScm = idTareaScm;
    }

    public TareaScm(Integer idTareaScm, String nombreTarea, String descripcionTarea) {
        this.idTareaScm = idTareaScm;
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
    }

    public Integer getIdTareaScm() {
        return idTareaScm;
    }

    public void setIdTareaScm(Integer idTareaScm) {
        this.idTareaScm = idTareaScm;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public List<Entregable> getEntregableList() {
        return entregableList;
    }

    public void setEntregableList(List<Entregable> entregableList) {
        this.entregableList = entregableList;
    }

    public List<TareaScmProyecto> getTareaScmProyectoList() {
        return tareaScmProyectoList;
    }

    public void setTareaScmProyectoList(List<TareaScmProyecto> tareaScmProyectoList) {
        this.tareaScmProyectoList = tareaScmProyectoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTareaScm != null ? idTareaScm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaScm)) {
            return false;
        }
        TareaScm other = (TareaScm) object;
        if ((this.idTareaScm == null && other.idTareaScm != null) || (this.idTareaScm != null && !this.idTareaScm.equals(other.idTareaScm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.TareaScm[ idTareaScm=" + idTareaScm + " ]";
    }
    
}
