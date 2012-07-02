/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jano
 */
@Entity
@Table(name = "SOLICITUD_REQUERIMIENTO", uniqueConstraints=@UniqueConstraint(columnNames="codigo_consulta"))
@NamedQueries({
    @NamedQuery(name = "SolicitudRequerimiento.findAll", query = "SELECT s FROM SolicitudRequerimiento s"),
    @NamedQuery(name = "SolicitudRequerimiento.findByIdSolicitudRequerimiento", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.idSolicitudRequerimiento = :idSolicitudRequerimiento"),
    @NamedQuery(name = "SolicitudRequerimiento.findByCodigoConsulta", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.codigoConsulta = :codigoConsulta"),
    @NamedQuery(name = "SolicitudRequerimiento.findByAsunto", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.asunto = :asunto"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaEnvio", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaEnvio = :fechaEnvio"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaCierre", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaVencimiento", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaUltimaActualizacion", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaUltimaActualizacion = :fechaUltimaActualizacion"),
    @NamedQuery(name = "SolicitudRequerimiento.findByJustificacionTrasnferencia", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.justificacionTrasnferencia = :justificacionTrasnferencia")})
public class SolicitudRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitud_req")
    private Long idSolicitudRequerimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "codigo_consulta")
    private String codigoConsulta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "mensaje")
    private String mensaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ultima_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaActualizacion;
    @Size(max = 255)
    @Column(name = "justificacion_trasnferencia")
    private String justificacionTrasnferencia;
    @Lob
    @Size(max = 65535)
    @Column(name = "respuesta")
    private String respuesta;
    @JoinColumn(name = "prioridad_solicitud", referencedColumnName = "id_tipo_prioridad")
    @ManyToOne(optional = false)
    private TipoPrioridad prioridadSolicitud;
    @JoinColumn(name = "solicitante", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Funcionario solicitante;
    @JoinColumn(name = "responsable", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico responsable;
    @JoinColumn(name = "area_responsable", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area areaResponsable;
    @JoinColumn(name = "estado_solicitud", referencedColumnName = "id_estado_solicitud_req")
    @ManyToOne(optional = false)
    private EstadoSolicitudRequerimiento estadoSolicitud;
    @JoinColumn(name = "tipo_solicitud", referencedColumnName = "id_tipo_solicitud_req")
    @ManyToOne(optional = false)
    private TipoSolicitudRequerimiento tipoSolicitud;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudRequerimiento")
    private List<ComentarioSolicitud> comentarios;

    public SolicitudRequerimiento() {
    }

    public SolicitudRequerimiento(Long idSolicitudReq) {
        this.idSolicitudRequerimiento = idSolicitudReq;
    }

    public SolicitudRequerimiento(Long idSolicitudReq, String codigoConsulta, String asunto, String mensaje, Date fechaEnvio, Date fechaUltimaActualizacion) {
        this.idSolicitudRequerimiento = idSolicitudReq;
        this.codigoConsulta = codigoConsulta;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public Long getIdSolicitudRequerimiento() {
        return idSolicitudRequerimiento;
    }

    public void setIdSolicitudRequerimiento(Long idSolicitudRequerimiento) {
        this.idSolicitudRequerimiento = idSolicitudRequerimiento;
    }

    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    public void setCodigoConsulta(String codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public String getJustificacionTrasnferencia() {
        return justificacionTrasnferencia;
    }

    public void setJustificacionTrasnferencia(String justificacionTrasnferencia) {
        this.justificacionTrasnferencia = justificacionTrasnferencia;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public TipoPrioridad getPrioridadSolicitud() {
        return prioridadSolicitud;
    }

    public void setPrioridadSolicitud(TipoPrioridad prioridadSolicitud) {
        this.prioridadSolicitud = prioridadSolicitud;
    }

    public Funcionario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Funcionario solicitante) {
        this.solicitante = solicitante;
    }

    public FuncionarioDisico getResponsable() {
        return responsable;
    }

    public void setResponsable(FuncionarioDisico responsable) {
        this.responsable = responsable;
    }

    public Area getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(Area areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public EstadoSolicitudRequerimiento getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitudRequerimiento estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public TipoSolicitudRequerimiento getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitudRequerimiento tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<ComentarioSolicitud> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioSolicitud> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudRequerimiento != null ? idSolicitudRequerimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudRequerimiento)) {
            return false;
        }
        SolicitudRequerimiento other = (SolicitudRequerimiento) object;
        if ((this.idSolicitudRequerimiento == null && other.idSolicitudRequerimiento != null) || (this.idSolicitudRequerimiento != null && !this.idSolicitudRequerimiento.equals(other.idSolicitudRequerimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento[ idSolicitudRequerimiento=" + idSolicitudRequerimiento + " ]";
    }
    
}
