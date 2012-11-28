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
@Table(name = "SOLICITUD_CAMBIO")
@NamedQueries({
    @NamedQuery(name = "SolicitudCambio.findAll", query = "SELECT s FROM SolicitudCambio s"),
    @NamedQuery(name = "SolicitudCambio.findByIdSolicitudCambio", query = "SELECT s FROM SolicitudCambio s WHERE s.idSolicitudCambio = :idSolicitudCambio")})
public class SolicitudCambio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitud_cambio")
    private Integer idSolicitudCambio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion_necesidad_cambio")
    private String descripcionNecesidadCambio;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion_cambio")
    private String descripcionCambio;
    @Column(name = "fecha_analisis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnalisis;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion_impacto_cambio")
    private String descripcionImpactoCambio;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Size(max = 60)
    @Column(name = "modulo_afectado")
    private String moduloAfectado;
    @Size(max = 255)
    @Column(name = "descripcion_resolucion")
    private String descripcionResolucion;
    @Column(name = "aprobada")
    private Boolean aprobada;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "solicitudCambio")
    private FormularioImplementacion formularioImplementacion;
    @JoinColumn(name = "item_configuracion", referencedColumnName = "id_item_configuracion")
    @ManyToOne(optional = false)
    private ItemConfiguracion itemConfiguracion;
    @JoinColumn(name = "evaluador_final", referencedColumnName = "rut")
    @ManyToOne
    private FuncionarioDisico evaluadorFinal;
    @JoinColumn(name = "evaluador_impacto", referencedColumnName = "rut")
    @ManyToOne
    private FuncionarioDisico evaluadorImpacto;
    @JoinColumn(name = "estado_solicitud", referencedColumnName = "id_estado_solicitud_cambio")
    @ManyToOne(optional = false)
    private EstadoSolicitudCambio estadoSolicitud;
    @JoinColumn(name = "prioridad_solicitud", referencedColumnName = "id_tipo_prioridad")
    @ManyToOne(optional = false)
    private TipoPrioridad prioridadSolicitud;
    @JoinColumn(name = "solicitante", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico solicitante;
    @JoinColumn(name = "proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    

    public SolicitudCambio() {
    }

    public SolicitudCambio(Integer idSolicitudCambio) {
        this.idSolicitudCambio = idSolicitudCambio;
    }

    public SolicitudCambio(Integer idSolicitudCambio, String titulo, Date fechaEnvio, String descripcionNecesidadCambio) {
        this.idSolicitudCambio = idSolicitudCambio;
        this.titulo = titulo;
        this.fechaEnvio = fechaEnvio;
        this.descripcionNecesidadCambio = descripcionNecesidadCambio;
    }

    public Integer getIdSolicitudCambio() {
        return idSolicitudCambio;
    }

    public void setIdSolicitudCambio(Integer idSolicitudCambio) {
        this.idSolicitudCambio = idSolicitudCambio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getDescripcionNecesidadCambio() {
        return descripcionNecesidadCambio;
    }

    public void setDescripcionNecesidadCambio(String descripcionNecesidadCambio) {
        this.descripcionNecesidadCambio = descripcionNecesidadCambio;
    }

    public String getDescripcionCambio() {
        return descripcionCambio;
    }

    public void setDescripcionCambio(String descripcionCambio) {
        this.descripcionCambio = descripcionCambio;
    }

    public Date getFechaAnalisis() {
        return fechaAnalisis;
    }

    public void setFechaAnalisis(Date fechaAnalisis) {
        this.fechaAnalisis = fechaAnalisis;
    }

    public String getDescripcionImpactoCambio() {
        return descripcionImpactoCambio;
    }

    public void setDescripcionImpactoCambio(String descripcionImpactoCambio) {
        this.descripcionImpactoCambio = descripcionImpactoCambio;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getModuloAfectado() {
        return moduloAfectado;
    }

    public void setModuloAfectado(String moduloAfectado) {
        this.moduloAfectado = moduloAfectado;
    }

    public String getDescripcionResolucion() {
        return descripcionResolucion;
    }

    public void setDescripcionResolucion(String descripcionResolucion) {
        this.descripcionResolucion = descripcionResolucion;
    }

    public Boolean getAprobada() {
        return aprobada;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public FormularioImplementacion getFormularioImplementacion() {
        return formularioImplementacion;
    }

    public void setFormularioImplementacion(FormularioImplementacion formularioImplementacion) {
        this.formularioImplementacion = formularioImplementacion;
    }

    public ItemConfiguracion getItemConfiguracion() {
        return itemConfiguracion;
    }

    public void setItemConfiguracion(ItemConfiguracion itemConfiguracion) {
        this.itemConfiguracion = itemConfiguracion;
    }

    public FuncionarioDisico getEvaluadorFinal() {
        return evaluadorFinal;
    }

    public void setEvaluadorFinal(FuncionarioDisico evaluadorFinal) {
        this.evaluadorFinal = evaluadorFinal;
    }

    public FuncionarioDisico getEvaluadorImpacto() {
        return evaluadorImpacto;
    }

    public void setEvaluadorImpacto(FuncionarioDisico evaluadorImpacto) {
        this.evaluadorImpacto = evaluadorImpacto;
    }

    public EstadoSolicitudCambio getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitudCambio estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public TipoPrioridad getPrioridadSolicitud() {
        return prioridadSolicitud;
    }

    public void setPrioridadSolicitud(TipoPrioridad prioridadSolicitud) {
        this.prioridadSolicitud = prioridadSolicitud;
    }

    public FuncionarioDisico getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(FuncionarioDisico solicitante) {
        this.solicitante = solicitante;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudCambio != null ? idSolicitudCambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudCambio)) {
            return false;
        }
        SolicitudCambio other = (SolicitudCambio) object;
        if ((this.idSolicitudCambio == null && other.idSolicitudCambio != null) || (this.idSolicitudCambio != null && !this.idSolicitudCambio.equals(other.idSolicitudCambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.SolicitudCambio[ idSolicitudCambio=" + idSolicitudCambio + " ]";
    }
    
}
