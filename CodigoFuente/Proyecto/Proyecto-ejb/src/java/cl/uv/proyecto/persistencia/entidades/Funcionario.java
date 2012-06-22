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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "FUNCIONARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByRut", query = "SELECT f FROM Funcionario f WHERE f.rut = :rut"),
    @NamedQuery(name = "Funcionario.findByNombre", query = "SELECT f FROM Funcionario f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Funcionario.findByApellidoPaterno", query = "SELECT f FROM Funcionario f WHERE f.apellidoPaterno = :apellidoPaterno"),
    @NamedQuery(name = "Funcionario.findByApellidoM", query = "SELECT f FROM Funcionario f WHERE f.apellidoM = :apellidoM"),
    @NamedQuery(name = "Funcionario.findByCorreoUv", query = "SELECT f FROM Funcionario f WHERE f.correoUv = :correoUv"),
    @NamedQuery(name = "Funcionario.findByFechaUltimoAcceso", query = "SELECT f FROM Funcionario f WHERE f.fechaUltimoAcceso = :fechaUltimoAcceso"),
    @NamedQuery(name = "Funcionario.findByFechaPrimerAcceso", query = "SELECT f FROM Funcionario f WHERE f.fechaPrimerAcceso = :fechaPrimerAcceso")})
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut", nullable = false)
    private Integer rut;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "apellido_paterno", nullable = false, length = 25)
    private String apellidoPaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "apellido_m", nullable = false, length = 25)
    private String apellidoM;
    @Size(max = 45)
    @Column(name = "correo_uv", length = 45)
    private String correoUv;
    @Column(name = "fecha_ultimo_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimoAcceso;
    @Column(name = "fecha_primer_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrimerAcceso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private List<Notificacion> notificacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private List<SolicitudRequerimiento> solicitudRequerimientoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private FuncionarioDisico funcionarioDisico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private List<ComentarioSolicitud> comentarioSolicitudList;

    public Funcionario() {
    }

    public Funcionario(Integer rut) {
        this.rut = rut;
    }

    public Funcionario(Integer rut, String nombre, String apellidoPaterno, String apellidoM) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoM = apellidoM;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getCorreoUv() {
        return correoUv;
    }

    public void setCorreoUv(String correoUv) {
        this.correoUv = correoUv;
    }

    public Date getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public Date getFechaPrimerAcceso() {
        return fechaPrimerAcceso;
    }

    public void setFechaPrimerAcceso(Date fechaPrimerAcceso) {
        this.fechaPrimerAcceso = fechaPrimerAcceso;
    }

    @XmlTransient
    public List<Notificacion> getNotificacionList() {
        return notificacionList;
    }

    public void setNotificacionList(List<Notificacion> notificacionList) {
        this.notificacionList = notificacionList;
    }

    @XmlTransient
    public List<SolicitudRequerimiento> getSolicitudRequerimientoList() {
        return solicitudRequerimientoList;
    }

    public void setSolicitudRequerimientoList(List<SolicitudRequerimiento> solicitudRequerimientoList) {
        this.solicitudRequerimientoList = solicitudRequerimientoList;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    @XmlTransient
    public List<ComentarioSolicitud> getComentarioSolicitudList() {
        return comentarioSolicitudList;
    }

    public void setComentarioSolicitudList(List<ComentarioSolicitud> comentarioSolicitudList) {
        this.comentarioSolicitudList = comentarioSolicitudList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.Funcionario[ rut=" + rut + " ]";
    }
    
}
