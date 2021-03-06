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
@Table(name = "PROYECTO")
@org.hibernate.annotations.Entity(dynamicUpdate=true)
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByIdProyecto", query = "SELECT p FROM Proyecto p WHERE p.idProyecto = :idProyecto"),
    @NamedQuery(name = "Proyecto.findByCodigoInterno", query = "SELECT p FROM Proyecto p WHERE p.codigoInterno = :codigoInterno"),
    @NamedQuery(name = "Proyecto.findByArea", query="SELECT p FROM Proyecto p WHERE p.areaResponsable = :areaResponsable ORDER BY p.fechaInicio DESC")})
public class Proyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codigo_interno")
    private String codigoInterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fecha_termino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTermino;
    @JoinColumn(name = "estado_proyecto", referencedColumnName = "id_estado_proyecto")
    @ManyToOne(optional = false)
    private EstadoProyecto estadoProyecto;
    @JoinColumn(name = "etapa_proyecto", referencedColumnName = "id_etapa_proyecto")
    @ManyToOne(optional = false)
    private EtapaProyecto etapaProyecto;
    @JoinColumn(name = "tipo_proyecto", referencedColumnName = "id_tipo_proyecto")
    @ManyToOne(optional = false)
    private TipoProyecto tipoProyecto;    
    @JoinColumn(name = "area_responsable", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area areaResponsable;
    @JoinColumn(name = "unidad_solicitante", referencedColumnName = "id_unidad_solicitante")
    @ManyToOne(optional = false)
    private UnidadSolicitante unidadSolicitante;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "proyecto")
    private List<ItemConfiguracion> itemsDeConfiguracion;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "proyecto")
    private List<TareaProyecto> tareasAgendadas;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "proyecto")
    private List<TareaScmProyecto> tareasScmProyecto;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "proyecto")
    private List<ParticipanteProyecto> participantes;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "proyecto")
    private List<SolicitudCambio> solicitudesDeCambio;
    @OneToMany(cascade = {CascadeType.REMOVE,CascadeType.MERGE}, mappedBy = "proyecto", orphanRemoval=true)
    private List<ArchivoProyecto> archivoProyectoList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "proyecto")
    private List<RegistroBitacora> bitacora;

    public Proyecto() {
    }

    public Proyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Proyecto(Integer idProyecto, String codigoInterno, String nombre, String descripcion, Date fechaInicio) {
        this.idProyecto = idProyecto;
        this.codigoInterno = codigoInterno;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public List<ItemConfiguracion> getItemsDeConfiguracion() {
        return itemsDeConfiguracion;
    }

    public void setItemsDeConfiguracion(List<ItemConfiguracion> itemsDeConfiguracion) {
        this.itemsDeConfiguracion = itemsDeConfiguracion;
    }

    public List<TareaProyecto> getTareasAgendadas() {
        return tareasAgendadas;
    }

    public void setTareasAgendadas(List<TareaProyecto> tareasAgendadas) {
        this.tareasAgendadas = tareasAgendadas;
    }

    public List<TareaScmProyecto> getTareasScmProyecto() {
        return tareasScmProyecto;
    }

    public void setTareasScmProyecto(List<TareaScmProyecto> tareasScmProyecto) {
        this.tareasScmProyecto = tareasScmProyecto;
    }

    public List<ParticipanteProyecto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteProyecto> participantes) {
        this.participantes = participantes;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public EtapaProyecto getEtapaProyecto() {
        return etapaProyecto;
    }

    public void setEtapaProyecto(EtapaProyecto etapaProyecto) {
        this.etapaProyecto = etapaProyecto;
    }

    public TipoProyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public Area getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(Area areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public List<SolicitudCambio> getSolicitudesDeCambio() {
        return solicitudesDeCambio;
    }

    public void setSolicitudesDeCambio(List<SolicitudCambio> solicitudesDeCambio) {
        this.solicitudesDeCambio = solicitudesDeCambio;
    }

    public List<ArchivoProyecto> getArchivoProyectoList() {
        return archivoProyectoList;
    }

    public void setArchivoProyectoList(List<ArchivoProyecto> archivoProyectoList) {
        this.archivoProyectoList = archivoProyectoList;
    }

    public UnidadSolicitante getUnidadSolicitante() {
        return unidadSolicitante;
    }

    public void setUnidadSolicitante(UnidadSolicitante unidadSolicitante) {
        this.unidadSolicitante = unidadSolicitante;
    }

    public List<RegistroBitacora> getBitacora() {
        return bitacora;
    }

    public void setBitacora(List<RegistroBitacora> bitacora) {
        this.bitacora = bitacora;
    }
    
    public String getFullNameProyecto(){
        return codigoInterno+" - "+nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.Proyecto[ idProyecto=" + idProyecto + " ]";
    }
    
}
