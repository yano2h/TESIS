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
 * @author Alejandro
 */
@Entity
@Table(name = "SOLICITUD_REQUERIMIENTO")
@NamedQueries({
    @NamedQuery(name = "SolicitudRequerimiento.findAll", query = "SELECT s FROM SolicitudRequerimiento s"),
    @NamedQuery(name = "SolicitudRequerimiento.findByIdSolicitudReq", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.idSolicitudReq = :idSolicitudReq"),
    @NamedQuery(name = "SolicitudRequerimiento.findByCodigoConsulta", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.codigoConsulta = :codigoConsulta"),
    @NamedQuery(name = "SolicitudRequerimiento.findByAsunto", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.asunto = :asunto"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaEnvio", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaEnvio = :fechaEnvio"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaCierre", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaVencimiento", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "SolicitudRequerimiento.findByFechaUltimaActualizacion", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.fechaUltimaActualizacion = :fechaUltimaActualizacion"),
    @NamedQuery(name = "SolicitudRequerimiento.findByJustificacionTrasnferencia", query = "SELECT s FROM SolicitudRequerimiento s WHERE s.justificacionTrasnferencia = :justificacionTrasnferencia")})
public class SolicitudRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitud_req")
    private Long idSolicitudReq;
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
    @Column(name = "respuesta")
    private String respuesta;
    @JoinColumn(name = "id_tipo_solicitud_req", referencedColumnName = "id_tipo_solicitud_req")
    @ManyToOne(optional = false)
    private TipoSolicitudRequerimiento tipoSolicitudRequerimiento;
    @JoinColumn(name = "id_tipo_prioridad", referencedColumnName = "id_tipo_prioridad")
    @ManyToOne(optional = false)
    private TipoPrioridad tipoPrioridad;
    @JoinColumn(name = "rut_responsable", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico;
    @JoinColumn(name = "rut_solicitante", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Funcionario funcionario;
    @JoinColumn(name = "id_estado_solicitud_req", referencedColumnName = "id_estado_solicitud_req")
    @ManyToOne(optional = false)
    private EstadoSolicitudRequerimiento estadoSolicitudRequerimiento;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area area;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudRequerimiento")
    private List<ComentarioSolicitud> comentarioSolicitudList;

    public SolicitudRequerimiento() {
    }

    public SolicitudRequerimiento(Long idSolicitudReq) {
        this.idSolicitudReq = idSolicitudReq;
    }

    public SolicitudRequerimiento(Long idSolicitudReq, String codigoConsulta, String asunto, String mensaje, Date fechaEnvio, Date fechaUltimaActualizacion) {
        this.idSolicitudReq = idSolicitudReq;
        this.codigoConsulta = codigoConsulta;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public Long getIdSolicitudReq() {
        return idSolicitudReq;
    }

    public void setIdSolicitudReq(Long idSolicitudReq) {
        this.idSolicitudReq = idSolicitudReq;
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

    public TipoSolicitudRequerimiento getTipoSolicitudRequerimiento() {
        return tipoSolicitudRequerimiento;
    }

    public void setTipoSolicitudRequerimiento(TipoSolicitudRequerimiento tipoSolicitudRequerimiento) {
        this.tipoSolicitudRequerimiento = tipoSolicitudRequerimiento;
    }

    public TipoPrioridad getTipoPrioridad() {
        return tipoPrioridad;
    }

    public void setTipoPrioridad(TipoPrioridad tipoPrioridad) {
        this.tipoPrioridad = tipoPrioridad;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public EstadoSolicitudRequerimiento getEstadoSolicitudRequerimiento() {
        return estadoSolicitudRequerimiento;
    }

    public void setEstadoSolicitudRequerimiento(EstadoSolicitudRequerimiento estadoSolicitudRequerimiento) {
        this.estadoSolicitudRequerimiento = estadoSolicitudRequerimiento;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<ComentarioSolicitud> getComentarioSolicitudList() {
        return comentarioSolicitudList;
    }

    public void setComentarioSolicitudList(List<ComentarioSolicitud> comentarioSolicitudList) {
        this.comentarioSolicitudList = comentarioSolicitudList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudReq != null ? idSolicitudReq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudRequerimiento)) {
            return false;
        }
        SolicitudRequerimiento other = (SolicitudRequerimiento) object;
        if ((this.idSolicitudReq == null && other.idSolicitudReq != null) || (this.idSolicitudReq != null && !this.idSolicitudReq.equals(other.idSolicitudReq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento[ idSolicitudReq=" + idSolicitudReq + " ]";
    }
    
}
