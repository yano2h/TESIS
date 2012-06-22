/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "FORMULARIO_IMPLEMENTACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormularioImplementacion.findAll", query = "SELECT f FROM FormularioImplementacion f"),
    @NamedQuery(name = "FormularioImplementacion.findByIdFormularioImplementacion", query = "SELECT f FROM FormularioImplementacion f WHERE f.idFormularioImplementacion = :idFormularioImplementacion"),
    @NamedQuery(name = "FormularioImplementacion.findByFechaVerificacion", query = "SELECT f FROM FormularioImplementacion f WHERE f.fechaVerificacion = :fechaVerificacion")})
public class FormularioImplementacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_formulario_implementacion", nullable = false)
    private Integer idFormularioImplementacion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "observaciones", nullable = false)
    private String observaciones;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "fecha_verificacion", nullable = false, length = 10)
    private String fechaVerificacion;
    @JoinColumn(name = "id_solicitud_cambio", referencedColumnName = "id_solicitud_cambio", nullable = false)
    @ManyToOne(optional = false)
    private SolicitudCambio solicitudCambio;
    @JoinColumn(name = "rut_implementador", referencedColumnName = "rut", nullable = false)
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico;
    @JoinColumn(name = "rut_verificador", referencedColumnName = "rut", nullable = false)
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico1;

    public FormularioImplementacion() {
    }

    public FormularioImplementacion(Integer idFormularioImplementacion) {
        this.idFormularioImplementacion = idFormularioImplementacion;
    }

    public FormularioImplementacion(Integer idFormularioImplementacion, String observaciones, String fechaVerificacion) {
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

    public String getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(String fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public SolicitudCambio getSolicitudCambio() {
        return solicitudCambio;
    }

    public void setSolicitudCambio(SolicitudCambio solicitudCambio) {
        this.solicitudCambio = solicitudCambio;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    public FuncionarioDisico getFuncionarioDisico1() {
        return funcionarioDisico1;
    }

    public void setFuncionarioDisico1(FuncionarioDisico funcionarioDisico1) {
        this.funcionarioDisico1 = funcionarioDisico1;
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
