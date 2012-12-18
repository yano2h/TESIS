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
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 *
 * @author Jano
 */
@Cacheable
@Entity
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "AREA")
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a",hints={@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
    @NamedQuery(name = "Area.findByIdArea", query = "SELECT a FROM Area a WHERE a.idArea = :idArea",hints={@QueryHint(name = "org.hibernate.cacheable", value = "true")})})
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_area")
    private Short idArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_area")
    private String nombreArea;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion_area")
    private String descripcionArea;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "areaResponsable", fetch = FetchType.LAZY, orphanRemoval=true)
    private List<SolicitudRequerimiento> solicitudRequerimientoList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "area",fetch = FetchType.LAZY, orphanRemoval=true)
    private List<FuncionarioDisico> funcionarioDisicoList;

    public Area() {
    }

    public Area(Short idArea) {
        this.idArea = idArea;
    }

    public Area(Short idArea, String nombreArea) {
        this.idArea = idArea;
        this.nombreArea = nombreArea;
    }

    public Short getIdArea() {
        return idArea;
    }

    public void setIdArea(Short idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getDescripcionArea() {
        return descripcionArea;
    }

    public void setDescripcionArea(String descripcionArea) {
        this.descripcionArea = descripcionArea;
    }

    public List<SolicitudRequerimiento> getSolicitudRequerimientoList() {
        return solicitudRequerimientoList;
    }

    public void setSolicitudRequerimientoList(List<SolicitudRequerimiento> solicitudRequerimientoList) {
        this.solicitudRequerimientoList = solicitudRequerimientoList;
    }

    public List<FuncionarioDisico> getFuncionarioDisicoList() {
        return funcionarioDisicoList;
    }

    public void setFuncionarioDisicoList(List<FuncionarioDisico> funcionarioDisicoList) {
        this.funcionarioDisicoList = funcionarioDisicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.Area[ idArea=" + idArea + " ]";
    }
    
}
