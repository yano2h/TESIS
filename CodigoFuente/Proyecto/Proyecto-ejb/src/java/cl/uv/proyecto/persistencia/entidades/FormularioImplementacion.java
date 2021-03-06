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
@Table(name = "FORMULARIO_IMPLEMENTACION")
@NamedQueries({
    @NamedQuery(name = "FormularioImplementacion.findAll", query = "SELECT f FROM FormularioImplementacion f"),
    @NamedQuery(name = "FormularioImplementacion.findByIdFormularioImplementacion", query = "SELECT f FROM FormularioImplementacion f WHERE f.idFormularioImplementacion = :idFormularioImplementacion")})
public class FormularioImplementacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_formulario_implementacion")
    private Integer idFormularioImplementacion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaVerificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nueva_version")
    private String nuevaVersion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nuevo_identificador")
    private String nuevoIdentificador;
    @JoinColumn(name = "id_formulario_implementacion", referencedColumnName = "id_solicitud_cambio", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private SolicitudCambio solicitudCambio;
    @JoinColumn(name = "implementador", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico implementador;
    @JoinColumn(name = "verificador", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico verificador;

    public FormularioImplementacion() {
    }

    public FormularioImplementacion(Integer idFormularioImplementacion) {
        this.idFormularioImplementacion = idFormularioImplementacion;
    }

    public FormularioImplementacion(Integer idFormularioImplementacion, String observaciones, Date fechaVerificacion) {
        this.idFormularioImplementacion = idFormularioImplementacion;
        this.observaciones = observaciones;
        this.fechaVerificacion = fechaVerificacion;
    }

    public Integer getIdFormularioImplementacion() {
        return idFormularioImplementacion;
    }

    public void setIdFormularioImplementacion(Integer idFormularioImplementacion) {
        this.idFormularioImplementacion = idFormularioImplementacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public String getNuevaVersion() {
        return nuevaVersion;
    }

    public void setNuevaVersion(String nuevaVersion) {
        this.nuevaVersion = nuevaVersion;
    }

    public String getNuevoIdentificador() {
        return nuevoIdentificador;
    }

    public void setNuevoIdentificador(String nuevoIdentificador) {
        this.nuevoIdentificador = nuevoIdentificador;
    }

    public SolicitudCambio getSolicitudCambio() {
        return solicitudCambio;
    }

    public void setSolicitudCambio(SolicitudCambio solicitudCambio) {
        this.solicitudCambio = solicitudCambio;
    }

    public FuncionarioDisico getImplementador() {
        return implementador;
    }

    public void setImplementador(FuncionarioDisico implementador) {
        this.implementador = implementador;
    }

    public FuncionarioDisico getVerificador() {
        return verificador;
    }

    public void setVerificador(FuncionarioDisico verificador) {
        this.verificador = verificador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormularioImplementacion != null ? idFormularioImplementacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioImplementacion)) {
            return false;
        }
        FormularioImplementacion other = (FormularioImplementacion) object;
        if ((this.idFormularioImplementacion == null && other.idFormularioImplementacion != null) || (this.idFormularioImplementacion != null && !this.idFormularioImplementacion.equals(other.idFormularioImplementacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.FormularioImplementacion[ idFormularioImplementacion=" + idFormularioImplementacion + " ]";
    }
    
}
