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
 * @author Alejandro
 */
@Entity
@Table(name = "SOLICITUD_CAMBIO")
@NamedQueries({
    @NamedQuery(name = "SolicitudCambio.findAll", query = "SELECT s FROM SolicitudCambio s"),
    @NamedQuery(name = "SolicitudCambio.findByIdSolicitudCambio", query = "SELECT s FROM SolicitudCambio s WHERE s.idSolicitudCambio = :idSolicitudCambio"),
    @NamedQuery(name = "SolicitudCambio.findByTitulo", query = "SELECT s FROM SolicitudCambio s WHERE s.titulo = :titulo"),
    @NamedQuery(name = "SolicitudCambio.findByFechaEnvio", query = "SELECT s FROM SolicitudCambio s WHERE s.fechaEnvio = :fechaEnvio"),
    @NamedQuery(name = "SolicitudCambio.findByFechaAnalisis", query = "SELECT s FROM SolicitudCambio s WHERE s.fechaAnalisis = :fechaAnalisis"),
    @NamedQuery(name = "SolicitudCambio.findByFechaCierre", query = "SELECT s FROM SolicitudCambio s WHERE s.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "SolicitudCambio.findByModuloAfectado", query = "SELECT s FROM SolicitudCambio s WHERE s.moduloAfectado = :moduloAfectado"),
    @NamedQuery(name = "SolicitudCambio.findByDescripcionResolucuion", query = "SELECT s FROM SolicitudCambio s WHERE s.descripcionResolucuion = :descripcionResolucuion")})
public class SolicitudCambio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitud_cambio")
    private Integer idSolicitudCambio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "descripcion_necesidad_cambio")
    private String descripcionNecesidadCambio;
    @Lob
    @Column(name = "descripcion_cambio")
    private String descripcionCambio;
    @Column(name = "fecha_analisis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnalisis;
    @Lob
    @Column(name = "descripcion_impacto_cambio")
    private String descripcionImpactoCambio;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Size(max = 60)
    @Column(name = "modulo_afectado")
    private String moduloAfectado;
    @Size(max = 255)
    @Column(name = "descripcion_resolucuion")
    private String descripcionResolucuion;
    @JoinColumn(name = "id_tipo_prioridad", referencedColumnName = "id_tipo_prioridad")
    @ManyToOne(optional = false)
    private TipoPrioridad tipoPrioridad;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "id_item_configuracion", referencedColumnName = "id_item_configuracion")
    @ManyToOne(optional = false)
    private ItemConfiguracion itemConfiguracion;
    @JoinColumn(name = "rut_solicitante", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico solicitante;
    @JoinColumn(name = "rut_evaluador_impacto", referencedColumnName = "rut")
    @ManyToOne
    private FuncionarioDisico evaluadorImpacto;
    @JoinColumn(name = "rut_evaluador_final", referencedColumnName = "rut")
    @ManyToOne
    private FuncionarioDisico evaluadorFinalSolicitud;
    @JoinColumn(name = "id_estado_solicitud_cambio", referencedColumnName = "id_estado_solicitud_cambio")
    @ManyToOne(optional = false)
    private EstadoSolicitudCambio estadoSolicitudCambio;
    
    @OneToOne(fetch= FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "solicitudCambio")
    private FormularioImplementacion formularioImplementacion;
   
    public SolicitudCambio() {
    }

    public SolicitudCambio(Integer idSolicitudCambio) {
        this.idSolicitudCambio = idSolicitudCambio;
    }

    public SolicitudCambio(Integer idSolicitudCambio, String titulo, Date fechaSolicitud, String descripcionNecesidadCambio) {
        this.idSolicitudCambio = idSolicitudCambio;
        this.titulo = titulo;
        this.fechaEnvio = fechaSolicitud;
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

    public String getDescripcionResolucuion() {
        return descripcionResolucuion;
    }

    public void setDescripcionResolucuion(String descripcionResolucuion) {
        this.descripcionResolucuion = descripcionResolucuion;
    }

    public FormularioImplementacion getFormularioImplementacion() {
        return formularioImplementacion;
    }

    public void setFormularioImplementacion(FormularioImplementacion formularioImplementacion) {
        this.formularioImplementacion = formularioImplementacion;
    }

    public TipoPrioridad getTipoPrioridad() {
        return tipoPrioridad;
    }

    public void setTipoPrioridad(TipoPrioridad tipoPrioridad) {
        this.tipoPrioridad = tipoPrioridad;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public ItemConfiguracion getItemConfiguracion() {
        return itemConfiguracion;
    }

    public void setItemConfiguracion(ItemConfiguracion itemConfiguracion) {
        this.itemConfiguracion = itemConfiguracion;
    }

    public FuncionarioDisico getEvaluadorFinalSolicitud() {
        return evaluadorFinalSolicitud;
    }

    public void setEvaluadorFinalSolicitud(FuncionarioDisico evaluadorFinalSolicitud) {
        this.evaluadorFinalSolicitud = evaluadorFinalSolicitud;
    }

    public FuncionarioDisico getEvaluadorImpacto() {
        return evaluadorImpacto;
    }

    public void setEvaluadorImpacto(FuncionarioDisico evaluadorImpacto) {
        this.evaluadorImpacto = evaluadorImpacto;
    }

    public FuncionarioDisico getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(FuncionarioDisico solicitante) {
        this.solicitante = solicitante;
    }

    public EstadoSolicitudCambio getEstadoSolicitudCambio() {
        return estadoSolicitudCambio;
    }

    public void setEstadoSolicitudCambio(EstadoSolicitudCambio estadoSolicitudCambio) {
        this.estadoSolicitudCambio = estadoSolicitudCambio;
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
