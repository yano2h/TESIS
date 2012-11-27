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
@Table(name = "FUNCIONARIO")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByRut", query = "SELECT f FROM Funcionario f WHERE f.rut = :rut")})
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut")
    private Integer rut;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    @Size(max = 45)
    @Column(name = "correo_uv")
    private String correoUv;
    @Column(name = "fecha_ultimo_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimoAcceso;
    @Column(name = "fecha_primer_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrimerAcceso;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "destinatario", fetch = FetchType.LAZY)
    private List<Notificacion> notificaciones;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "solicitante", fetch = FetchType.LAZY, orphanRemoval=true)
    private List<SolicitudRequerimiento> solicitudesRequerimientoEnviadas;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "autor", fetch = FetchType.LAZY)
    private List<ComentarioSolicitud> comentarioSolicitudList;

    public Funcionario() {
    }

    public Funcionario(Integer rut) {
        this.rut = rut;
    }

    public Funcionario(Integer rut, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
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

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
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

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<SolicitudRequerimiento> getSolicitudesRequerimientoEnviadas() {
        return solicitudesRequerimientoEnviadas;
    }

    public void setSolicitudesRequerimientoEnviadas(List<SolicitudRequerimiento> solicitudesRequerimientoEnviadas) {
        this.solicitudesRequerimientoEnviadas = solicitudesRequerimientoEnviadas;
    }

    public List<ComentarioSolicitud> getComentarioSolicitudList() {
        return comentarioSolicitudList;
    }

    public void setComentarioSolicitudList(List<ComentarioSolicitud> comentarioSolicitudList) {
        this.comentarioSolicitudList = comentarioSolicitudList;
    }

    public String getNombreCompleto(){
        return nombre+" "+apellidoPaterno+" "+apellidoMaterno;
    }
    
    public String getNombreCorto(){
        String inicial = nombre.substring(0,1);
        return inicial+"."+apellidoPaterno;
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
