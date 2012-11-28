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
@Table(name = "COMENTARIO_SOLICITUD")
@org.hibernate.annotations.Entity(dynamicUpdate=true)
@NamedQueries({
    @NamedQuery(name = "ComentarioSolicitud.findAll", query = "SELECT c FROM ComentarioSolicitud c"),
    @NamedQuery(name = "ComentarioSolicitud.findByIdComentario", query = "SELECT c FROM ComentarioSolicitud c WHERE c.idComentario = :idComentario"),
    @NamedQuery(name = "ComentarioSolicitud.findByIdSolicitud", query = "SELECT c FROM ComentarioSolicitud c WHERE c.solicitudRequerimiento.idSolicitudRequerimiento = :id ORDER BY c.fecha DESC")})
public class ComentarioSolicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comentario")
    private Long idComentario;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "visible")
    private boolean visible;
    
    @JoinColumn(name = "autor", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Funcionario autor;
    @JoinColumn(name = "solicitud_requerimiento", referencedColumnName = "id_solicitud_req")
    @ManyToOne(optional = false)
    private SolicitudRequerimiento solicitudRequerimiento;

    public ComentarioSolicitud() {
    }

    public ComentarioSolicitud(Long idComentario) {
        this.idComentario = idComentario;
    }

    public ComentarioSolicitud(Long idComentario, String comentario, Date fecha, boolean visible) {
        this.idComentario = idComentario;
        this.comentario = comentario;
        this.fecha = fecha;
        this.visible = visible;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Funcionario getAutor() {
        return autor;
    }

    public void setAutor(Funcionario autor) {
        this.autor = autor;
    }

    public SolicitudRequerimiento getSolicitudRequerimiento() {
        return solicitudRequerimiento;
    }

    public void setSolicitudRequerimiento(SolicitudRequerimiento solicitudRequerimiento) {
        this.solicitudRequerimiento = solicitudRequerimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentario != null ? idComentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComentarioSolicitud)) {
            return false;
        }
        ComentarioSolicitud other = (ComentarioSolicitud) object;
        if ((this.idComentario == null && other.idComentario != null) || (this.idComentario != null && !this.idComentario.equals(other.idComentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud[ idComentario=" + idComentario + " ]";
    }
    
}
