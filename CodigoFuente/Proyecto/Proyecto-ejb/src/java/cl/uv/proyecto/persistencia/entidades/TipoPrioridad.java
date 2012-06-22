/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "TIPO_PRIORIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPrioridad.findAll", query = "SELECT t FROM TipoPrioridad t"),
    @NamedQuery(name = "TipoPrioridad.findByIdTipoPrioridad", query = "SELECT t FROM TipoPrioridad t WHERE t.idTipoPrioridad = :idTipoPrioridad"),
    @NamedQuery(name = "TipoPrioridad.findByNombrePrioridad", query = "SELECT t FROM TipoPrioridad t WHERE t.nombrePrioridad = :nombrePrioridad")})
public class TipoPrioridad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_prioridad", nullable = false)
    private Short idTipoPrioridad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_prioridad", nullable = false, length = 45)
    private String nombrePrioridad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPrioridad")
    private List<SolicitudRequerimiento> solicitudRequerimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPrioridad")
    private List<SolicitudCambio> solicitudCambioList;

    public TipoPrioridad() {
    }

    public TipoPrioridad(Short idTipoPrioridad) {
        this.idTipoPrioridad = idTipoPrioridad;
    }

    public TipoPrioridad(Short idTipoPrioridad, String nombrePrioridad) {
        this.idTipoPrioridad = idTipoPrioridad;
        this.nombrePrioridad = nombrePrioridad;
    }

    public Short getIdTipoPrioridad() {
        return idTipoPrioridad;
    }

    public void setIdTipoPrioridad(Short idTipoPrioridad) {
        this.idTipoPrioridad = idTipoPrioridad;
    }

    public String getNombrePrioridad() {
        return nombrePrioridad;
    }

    public void setNombrePrioridad(String nombrePrioridad) {
        this.nombrePrioridad = nombrePrioridad;
    }

    @XmlTransient
    public List<SolicitudRequerimiento> getSolicitudRequerimientoList() {
        return solicitudRequerimientoList;
    }

    public void setSolicitudRequerimientoList(List<SolicitudRequerimiento> solicitudRequerimientoList) {
        this.solicitudRequerimientoList = solicitudRequerimientoList;
    }

    @XmlTransient
    public List<SolicitudCambio> getSolicitudCambioList() {
        return solicitudCambioList;
    }

    public void setSolicitudCambioList(List<SolicitudCambio> solicitudCambioList) {
        this.solicitudCambioList = solicitudCambioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPrioridad != null ? idTipoPrioridad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPrioridad)) {
            return false;
        }
        TipoPrioridad other = (TipoPrioridad) object;
        if ((this.idTipoPrioridad == null && other.idTipoPrioridad != null) || (this.idTipoPrioridad != null && !this.idTipoPrioridad.equals(other.idTipoPrioridad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.TipoPrioridad[ idTipoPrioridad=" + idTipoPrioridad + " ]";
    }
    
}
