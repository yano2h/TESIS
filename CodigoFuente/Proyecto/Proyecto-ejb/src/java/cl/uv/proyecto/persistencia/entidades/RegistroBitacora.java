/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "REGISTRO_BITACORA")
@NamedQueries({
    @NamedQuery(name = "RegistroBitacora.findAll", query = "SELECT r FROM RegistroBitacora r"),
    @NamedQuery(name = "RegistroBitacora.findByIdRegistroBitacora", query = "SELECT r FROM RegistroBitacora r WHERE r.idRegistroBitacora = :idRegistroBitacora"),
    @NamedQuery(name = "RegistroBitacora.findByProyecto", query = "SELECT r FROM RegistroBitacora r WHERE r.proyecto = :proyecto")})

public class RegistroBitacora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name ="id_registro_bitacora")
    private Long idRegistroBitacora;
    @Column(name ="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name ="descripcion")
    private String descripcion;
    @Column(name ="contraparte_responsable")
    private String contraparteResponsable;
    @JoinColumn(name = "funcionario_responsable", referencedColumnName = "rut")
    @ManyToOne(optional = true)
    private FuncionarioDisico funcionarioResponsable;
    @JoinColumn(name = "estado_proyecto", referencedColumnName = "id_estado_proyecto")
    @ManyToOne(optional = false)
    private EstadoProyecto estadoProyecto;
    @JoinColumn(name = "proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyecto;

    public Long getIdRegistroBitacora() {
        return idRegistroBitacora;
    }

    public void setIdRegistroBitacora(Long idRegistroBitacora) {
        this.idRegistroBitacora = idRegistroBitacora;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContraparteResponsable() {
        return contraparteResponsable;
    }

    public void setContraparteResponsable(String contraparteResponsable) {
        this.contraparteResponsable = contraparteResponsable;
    }

    public FuncionarioDisico getFuncionarioResponsable() {
        return funcionarioResponsable;
    }

    public void setFuncionarioResponsable(FuncionarioDisico funcionarioResponsable) {
        this.funcionarioResponsable = funcionarioResponsable;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroBitacora != null ? idRegistroBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroBitacora)) {
            return false;
        }
        RegistroBitacora other = (RegistroBitacora) object;
        if ((this.idRegistroBitacora == null && other.idRegistroBitacora != null) || (this.idRegistroBitacora != null && !this.idRegistroBitacora.equals(other.idRegistroBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.RegistroBitacora[ idRegistroBitacora=" + idRegistroBitacora + " ]";
    }
    
}
