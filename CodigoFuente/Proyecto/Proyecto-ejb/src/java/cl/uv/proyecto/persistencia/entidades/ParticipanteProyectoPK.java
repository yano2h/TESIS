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
public class ParticipanteProyectoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut_participante")
    private int rutParticipante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proyecto")
    private int idProyecto;

    public ParticipanteProyectoPK() {
    }

    public ParticipanteProyectoPK(int rutParticipante, int idProyecto) {
        this.rutParticipante = rutParticipante;
        this.idProyecto = idProyecto;
    }

    public int getRutParticipante() {
        return rutParticipante;
    }

    public void setRutParticipante(int rutParticipante) {
        this.rutParticipante = rutParticipante;
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
        hash += (int) rutParticipante;
        hash += (int) idProyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticipanteProyectoPK)) {
            return false;
        }
        ParticipanteProyectoPK other = (ParticipanteProyectoPK) object;
        if (this.rutParticipante != other.rutParticipante) {
            return false;
        }
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ParticipanteProyectoPK[ rutParticipante=" + rutParticipante + ", idProyecto=" + idProyecto + " ]";
    }
    
}
