/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "NOTIFICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacion.findAll", query = "SELECT n FROM Notificacion n"),
    @NamedQuery(name = "Notificacion.findByIdNotificacion", query = "SELECT n FROM Notificacion n WHERE n.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "Notificacion.findByFecha", query = "SELECT n FROM Notificacion n WHERE n.fecha = :fecha"),
    @NamedQuery(name = "Notificacion.findByMensajeNotificacion", query = "SELECT n FROM Notificacion n WHERE n.mensajeNotificacion = :mensajeNotificacion"),
    @NamedQuery(name = "Notificacion.findByRevisada", query = "SELECT n FROM Notificacion n WHERE n.revisada = :revisada")})
public class Notificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_notificacion", nullable = false)
    private Long idNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "mensaje_notificacion", nullable = false, length = 90)
    private String mensajeNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "revisada", nullable = false)
    private boolean revisada;
    @JoinColumn(name = "rut_destinatario", referencedColumnName = "rut", nullable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    public Notificacion() {
    }

    public Notificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Notificacion(Long idNotificacion, Date fecha, String mensajeNotificacion, boolean revisada) {
        this.idNotificacion = idNotificacion;
        this.fecha = fecha;
        this.mensajeNotificacion = mensajeNotificacion;
        this.revisada = revisada;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensajeNotificacion() {
        return mensajeNotificacion;
    }

    public void setMensajeNotificacion(String mensajeNotificacion) {
        this.mensajeNotificacion = mensajeNotificacion;
    }

    public boolean getRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacion)) {
            return false;
        }
        Notificacion other = (Notificacion) object;
        if ((this.idNotificacion == null && other.idNotificacion != null) || (this.idNotificacion != null && !this.idNotificacion.equals(other.idNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.Notificacion[ idNotificacion=" + idNotificacion + " ]";
    }
    
}
