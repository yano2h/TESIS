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
 * @author Jano
 */
@Embeddable
public class TareaScmProyectoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tarea_scm")
    private int idTareaScm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proyecto")
    private int idProyecto;

    public TareaScmProyectoPK() {
    }

    public TareaScmProyectoPK(int idTareaScm, int idProyecto) {
        this.idTareaScm = idTareaScm;
        this.idProyecto = idProyecto;
    }

    public int getIdTareaScm() {
        return idTareaScm;
    }

    public void setIdTareaScm(int idTareaScm) {
        this.idTareaScm = idTareaScm;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTareaScm;
        hash += (int) idProyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaScmProyectoPK)) {
            return false;
        }
        TareaScmProyectoPK other = (TareaScmProyectoPK) object;
        if (this.idTareaScm != other.idTareaScm) {
            return false;
        }
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.percistencia.entidades.TareaScmProyectoPK[ idTareaScm=" + idTareaScm + ", idProyecto=" + idProyecto + " ]";
    }
    
}
