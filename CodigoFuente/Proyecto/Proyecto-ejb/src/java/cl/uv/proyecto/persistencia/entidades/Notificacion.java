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

/**
 *
 * @author Jano
 */
@Entity
@Table(name = "NOTIFICACION")
@NamedQueries({
    @NamedQuery(name = "Notificacion.findAll", query = "SELECT n FROM Notificacion n"),
    @NamedQuery(name = "Notificacion.findByIdNotificacion", query = "SELECT n FROM Notificacion n WHERE n.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "Notificacion.findByFecha", query = "SELECT n FROM Notificacion n WHERE n.fecha = :fecha")})
public class Notificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion")
    private Long idNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "mensaje_notificacion")
    private String mensajeNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "revisada")
    private boolean revisada;
    @JoinColumn(name = "destinatario", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Funcionario destinatario;

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

    public Notificacion(String mensajeNotificacion, Funcionario destinatario) {
        this.mensajeNotificacion = mensajeNotificacion;
        this.destinatario = destinatario;
        this.revisada = false;
        this.fecha = new Date();
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

    public Funcionario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Funcionario destinatario) {
        this.destinatario = destinatario;
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
