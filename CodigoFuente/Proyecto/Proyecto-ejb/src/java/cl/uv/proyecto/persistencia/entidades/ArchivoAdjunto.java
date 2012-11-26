/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.InputStream;
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
@Table(name = "archivo_adjunto")
@NamedQueries({
    @NamedQuery(name = "ArchivoAdjunto.findAll", query = "SELECT a FROM ArchivoAdjunto a"),
    @NamedQuery(name = "ArchivoAdjunto.findByIdArchivo", query = "SELECT a FROM ArchivoAdjunto a WHERE a.idArchivo = :idArchivo")})
public class ArchivoAdjunto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_archivo")
    private Long idArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "path_file")
    private String pathFile;
    @Basic(optional = false)
    @NotNull
    @Column(name = "size_file")
    private long sizeFile;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "size_format")
    private String sizeFormat;
    @Size(max = 100)
    @Column(name = "mimetype")
    private String mimetype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_upload")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUpload;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    
    @Transient
    private InputStream inputStream;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "archivoAdjunto")
//    private ArchivoSolicitudRequerimiento archivoSolicitudRequerimiento;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "archivoAdjunto")
//    private ArchivoProyecto archivoProyecto;

    public ArchivoAdjunto() {
        activo = true;
    }

    public ArchivoAdjunto(Long idArchivo) {
        this.idArchivo = idArchivo;
        activo = true;
        fechaUpload = new Date();
    }

    public ArchivoAdjunto(Long idArchivo, String nombre, String pathFile, long sizeFile, String sizeFormat, Date fechaUpload, boolean activo) {
        this.idArchivo = idArchivo;
        this.nombre = nombre;
        this.pathFile = pathFile;
        this.sizeFile = sizeFile;
        this.sizeFormat = sizeFormat;
        this.fechaUpload = fechaUpload;
        this.activo = activo;
    }

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public long getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(long sizeFile) {
        this.sizeFile = sizeFile;
    }

    public String getSizeFormat() {
        return sizeFormat;
    }

    public void setSizeFormat(String sizeFormat) {
        this.sizeFormat = sizeFormat;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public Date getFechaUpload() {
        return fechaUpload;
    }

    public void setFechaUpload(Date fechaUpload) {
        this.fechaUpload = fechaUpload;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

//    public ArchivoSolicitudRequerimiento getArchivoSolicitudRequerimiento() {
//        return archivoSolicitudRequerimiento;
//    }
//
//    public void setArchivoSolicitudRequerimiento(ArchivoSolicitudRequerimiento archivoSolicitudRequerimiento) {
//        this.archivoSolicitudRequerimiento = archivoSolicitudRequerimiento;
//    }
//
//    public ArchivoProyecto getArchivoProyecto() {
//        return archivoProyecto;
//    }
//
//    public void setArchivoProyecto(ArchivoProyecto archivoProyecto) {
//        this.archivoProyecto = archivoProyecto;
//    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivo != null ? idArchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoAdjunto)) {
            return false;
        }
        ArchivoAdjunto other = (ArchivoAdjunto) object;
        if ((this.idArchivo == null && other.idArchivo != null) || (this.idArchivo != null && !this.idArchivo.equals(other.idArchivo)) || (this.idArchivo == null && (!this.nombre.equals(other.nombre) || !this.mimetype.equals(other.mimetype)))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto[ idArchivo=" + idArchivo + " ]";
    }
    
}
