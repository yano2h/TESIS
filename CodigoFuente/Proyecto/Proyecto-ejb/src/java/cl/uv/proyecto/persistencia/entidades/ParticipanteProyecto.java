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
@Table(name = "PARTICIPANTE_PROYECTO")
@NamedQueries({
    @NamedQuery(name = "ParticipanteProyecto.findAll", query = "SELECT p FROM ParticipanteProyecto p"),
    @NamedQuery(name = "ParticipanteProyecto.findByRutParticipante", query = "SELECT p FROM ParticipanteProyecto p WHERE p.participanteProyectoPK.rutParticipante = :rutParticipante"),
    @NamedQuery(name = "ParticipanteProyecto.findByIdProyecto", query = "SELECT p FROM ParticipanteProyecto p WHERE p.participanteProyectoPK.idProyecto = :idProyecto")})
public class ParticipanteProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParticipanteProyectoPK participanteProyectoPK;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne(optional = false)
    private RolProyecto rolProyecto;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "rut_participante", referencedColumnName = "rut", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico;

    public ParticipanteProyecto() {
    }

    public ParticipanteProyecto(ParticipanteProyectoPK participanteProyectoPK) {
        this.participanteProyectoPK = participanteProyectoPK;
    }

    public ParticipanteProyecto(int rutParticipante, int idProyecto) {
        this.participanteProyectoPK = new ParticipanteProyectoPK(rutParticipante, idProyecto);
    }

    public ParticipanteProyectoPK getParticipanteProyectoPK() {
        return participanteProyectoPK;
    }

    public void setParticipanteProyectoPK(ParticipanteProyectoPK participanteProyectoPK) {
        this.participanteProyectoPK = participanteProyectoPK;
    }

    public RolProyecto getRolProyecto() {
        return rolProyecto;
    }

    public void setRolProyecto(RolProyecto rolProyecto) {
        this.rolProyecto = rolProyecto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (participanteProyectoPK != null ? participanteProyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticipanteProyecto)) {
            return false;
        }
        ParticipanteProyecto other = (ParticipanteProyecto) object;
        if ((this.participanteProyectoPK == null && other.participanteProyectoPK != null) || (this.participanteProyectoPK != null && !this.participanteProyectoPK.equals(other.participanteProyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ParticipanteProyecto[ participanteProyectoPK=" + participanteProyectoPK + " ]";
    }
    
}
