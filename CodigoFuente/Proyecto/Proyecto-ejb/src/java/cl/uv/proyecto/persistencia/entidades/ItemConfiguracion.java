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
@Table(name = "ITEM_CONFIGURACION")
@NamedQueries({
    @NamedQuery(name = "ItemConfiguracion.findAll", query = "SELECT i FROM ItemConfiguracion i"),
    @NamedQuery(name = "ItemConfiguracion.findByIdItemConfiguracion", query = "SELECT i FROM ItemConfiguracion i WHERE i.idItemConfiguracion = :idItemConfiguracion"),
    @NamedQuery(name = "ItemConfiguracion.findByCodigoIdentificadorIc", query = "SELECT i FROM ItemConfiguracion i WHERE i.codigoIdentificadorIc = :codigoIdentificadorIc"),
    @NamedQuery(name = "ItemConfiguracion.findByNombreIc", query = "SELECT i FROM ItemConfiguracion i WHERE i.nombreIc = :nombreIc"),
    @NamedQuery(name = "ItemConfiguracion.findByVersion", query = "SELECT i FROM ItemConfiguracion i WHERE i.version = :version"),
    @NamedQuery(name = "ItemConfiguracion.findByUbicacionEnBiblioteca", query = "SELECT i FROM ItemConfiguracion i WHERE i.ubicacionEnBiblioteca = :ubicacionEnBiblioteca"),
    @NamedQuery(name = "ItemConfiguracion.findByFechaUltimaModificacion", query = "SELECT i FROM ItemConfiguracion i WHERE i.fechaUltimaModificacion = :fechaUltimaModificacion")})
public class ItemConfiguracion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_item_configuracion")
    private Integer idItemConfiguracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "codigo_identificador_ic")
    private String codigoIdentificadorIc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_ic")
    private String nombreIc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "version")
    private String version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ubicacion_en_biblioteca")
    private String ubicacionEnBiblioteca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaModificacion;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "rut_responsable_ic", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemConfiguracion")
    private List<SolicitudCambio> solicitudCambioList;

    public ItemConfiguracion() {
    }

    public ItemConfiguracion(Integer idItemConfiguracion) {
        this.idItemConfiguracion = idItemConfiguracion;
    }

    public ItemConfiguracion(Integer idItemConfiguracion, String codigoIdentificadorIc, String nombreIc, String version, String ubicacionEnBiblioteca, Date fechaUltimaModificacion) {
        this.idItemConfiguracion = idItemConfiguracion;
        this.codigoIdentificadorIc = codigoIdentificadorIc;
        this.nombreIc = nombreIc;
        this.version = version;
        this.ubicacionEnBiblioteca = ubicacionEnBiblioteca;
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Integer getIdItemConfiguracion() {
        return idItemConfiguracion;
    }

    public void setIdItemConfiguracion(Integer idItemConfiguracion) {
        this.idItemConfiguracion = idItemConfiguracion;
    }

    public String getCodigoIdentificadorIc() {
        return codigoIdentificadorIc;
    }

    public void setCodigoIdentificadorIc(String codigoIdentificadorIc) {
        this.codigoIdentificadorIc = codigoIdentificadorIc;
    }

    public String getNombreIc() {
        return nombreIc;
    }

    public void setNombreIc(String nombreIc) {
        this.nombreIc = nombreIc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUbicacionEnBiblioteca() {
        return ubicacionEnBiblioteca;
    }

    public void setUbicacionEnBiblioteca(String ubicacionEnBiblioteca) {
        this.ubicacionEnBiblioteca = ubicacionEnBiblioteca;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    public List<SolicitudCambio> getSolicitudCambioList() {
        return solicitudCambioList;
    }

    public void setSolicitudCambioList(List<SolicitudCambio> solicitudCambioList) {
        this.solicitudCambioList = solicitudCambioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItemConfiguracion != null ? idItemConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemConfiguracion)) {
            return false;
        }
        ItemConfiguracion other = (ItemConfiguracion) object;
        if ((this.idItemConfiguracion == null && other.idItemConfiguracion != null) || (this.idItemConfiguracion != null && !this.idItemConfiguracion.equals(other.idItemConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ItemConfiguracion[ idItemConfiguracion=" + idItemConfiguracion + " ]";
    }
    
}
