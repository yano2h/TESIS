/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "COMENTARIO_SOLICITUD")
@NamedQueries({
    @NamedQuery(name = "ComentarioSolicitud.findAll", query = "SELECT c FROM ComentarioSolicitud c"),
    @NamedQuery(name = "ComentarioSolicitud.findByIdComentario", query = "SELECT c FROM ComentarioSolicitud c WHERE c.idComentario = :idComentario"),
    @NamedQuery(name = "ComentarioSolicitud.findByFecha", query = "SELECT c FROM ComentarioSolicitud c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ComentarioSolicitud.findByVisible", query = "SELECT c FROM ComentarioSolicitud c WHERE c.visible = :visible")})
public class ComentarioSolicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_comentario")
    private Long idComentario;
    @Basic(optional = false)
    @NotNull
    @Lob
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
    @JoinColumn(name = "id_solicitud_req", referencedColumnName = "id_solicitud_req")
    @ManyToOne(optional = false)
    private SolicitudRequerimiento solicitudRequerimiento;
    @JoinColumn(name = "rut_autor", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Funcionario funcionario;

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

    public SolicitudRequerimiento getSolicitudRequerimiento() {
        return solicitudRequerimiento;
    }

    public void setSolicitudRequerimiento(SolicitudRequerimiento solicitudRequerimiento) {
        this.solicitudRequerimiento = solicitudRequerimiento;
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
