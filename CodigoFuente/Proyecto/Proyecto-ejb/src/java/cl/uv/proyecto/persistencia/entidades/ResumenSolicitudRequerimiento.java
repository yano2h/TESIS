/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "resumen_solicitud_requerimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResumenSolicitudRequerimiento.findAll", query = "SELECT r FROM ResumenSolicitudRequerimiento r"),
    @NamedQuery(name = "ResumenSolicitudRequerimiento.findByIdSolicitudReq", query = "SELECT r FROM ResumenSolicitudRequerimiento r WHERE r.idSolicitudReq = :idSolicitudReq"),
    @NamedQuery(name = "ResumenSolicitudRequerimiento.findByCodigoConsulta", query = "SELECT r FROM ResumenSolicitudRequerimiento r WHERE r.codigoConsulta = :codigoConsulta"),
    @NamedQuery(name = "ResumenSolicitudRequerimiento.findByResponsable", query = "SELECT r FROM ResumenSolicitudRequerimiento r WHERE r.responsable = :responsable"),
    @NamedQuery(name = "ResumenSolicitudRequerimiento.findByAreaResponsable", query = "SELECT r FROM ResumenSolicitudRequerimiento r WHERE r.areaResponsable = :areaResponsable")})
public class ResumenSolicitudRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitud_req")
    private BigInteger idSolicitudReq;
    @Column(name = "codigo_consulta", length = 9)
    private String codigoConsulta;
    @Column(name = "asunto", length = 45)
    private String asunto;
    @Column(name = "responsable")
    private Integer responsable;
    @Column(name = "nombrecorto", length = 2147483647)
    private String nombreCorto;
    @JoinColumn(name = "estado_solicitud", referencedColumnName = "id_estado_solicitud_req")
    @ManyToOne(optional = false)
    private EstadoSolicitudRequerimiento estadoSolicitud;
    @JoinColumn(name = "tipo_solicitud", referencedColumnName = "id_tipo_solicitud_req")
    @ManyToOne(optional = false)
    private TipoSolicitudRequerimiento tipoSolicitud;
    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @JoinColumn(name = "area_responsable", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area areaResponsable;

    public ResumenSolicitudRequerimiento() {
    }

    public BigInteger getIdSolicitudReq() {
        return idSolicitudReq;
    }

    public void setIdSolicitudReq(BigInteger idSolicitudReq) {
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

    public Integer getResponsable() {
        return responsable;
    }

    public void setResponsable(Integer responsable) {
        this.responsable = responsable;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombrecorto) {
        this.nombreCorto = nombrecorto;
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

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Area getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(Area areaResponsable) {
        this.areaResponsable = areaResponsable;
    }
    
}
