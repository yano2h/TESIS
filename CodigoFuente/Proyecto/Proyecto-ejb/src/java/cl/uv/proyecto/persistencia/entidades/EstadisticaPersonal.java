/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jano
 */
@Entity
@Table(name = "ESTADISTICA_PERSONAL")
@NamedQueries({
    @NamedQuery(name = "EstadisticaPersonal.findAll", query = "SELECT e FROM EstadisticaPersonal e"),
    @NamedQuery(name = "EstadisticaPersonal.findById", query = "SELECT e FROM EstadisticaPersonal e WHERE e.id = :id"),
    @NamedQuery(name = "EstadisticaPersonal.findByFechaMedicion", query = "SELECT e FROM EstadisticaPersonal e WHERE e.fechaMedicion = :fechaMedicion")})
public class EstadisticaPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_medicion")
    @Temporal(TemporalType.DATE)
    private Date fechaMedicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_total_solicitudes_asignadas")
    private long cantidadTotalSolicitudesAsignadas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_solicitudes_pendientes")
    private int cantidadSolicitudesPendientes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_solicitudes_vencidas")
    private int cantidadSolicitudesVencidas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_solicitudes_iniciadas")
    private int cantidadSolicitudesIniciadas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_solicitudes_cerradas")
    private int cantidadSolicitudesCerradas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_proyectos_acargo")
    private int cantidadProyectosAcargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_proyectos_en_que_participa")
    private int cantidadProyectosEnQueParticipa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_tareas_proyecto_asociadas")
    private int cantidadTareasProyectoAsociadas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_tareas_scm_asociadas")
    private int cantidadTareasScmAsociadas;
    @JoinColumn(name = "funcionario", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private FuncionarioDisico funcionarioDisico;

    public EstadisticaPersonal() {
    }

    public EstadisticaPersonal(Long id) {
        this.id = id;
    }

    public EstadisticaPersonal(Long id, Date fechaMedicion, long cantidadTotalSolicitudesAsignadas, int cantidadSolicitudesPendientes, int cantidadSolicitudesVencidas, int cantidadSolicitudesIniciadas, int cantidadSolicitudesCerradas, int cantidadProyectosAcargo, int cantidadProyectosEnQueParticipa, int cantidadTareasProyectoAsociadas, int cantidadTareasScmAsociadas) {
        this.id = id;
        this.fechaMedicion = fechaMedicion;
        this.cantidadTotalSolicitudesAsignadas = cantidadTotalSolicitudesAsignadas;
        this.cantidadSolicitudesPendientes = cantidadSolicitudesPendientes;
        this.cantidadSolicitudesVencidas = cantidadSolicitudesVencidas;
        this.cantidadSolicitudesIniciadas = cantidadSolicitudesIniciadas;
        this.cantidadSolicitudesCerradas = cantidadSolicitudesCerradas;
        this.cantidadProyectosAcargo = cantidadProyectosAcargo;
        this.cantidadProyectosEnQueParticipa = cantidadProyectosEnQueParticipa;
        this.cantidadTareasProyectoAsociadas = cantidadTareasProyectoAsociadas;
        this.cantidadTareasScmAsociadas = cantidadTareasScmAsociadas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaMedicion() {
        return fechaMedicion;
    }

    public void setFechaMedicion(Date fechaMedicion) {
        this.fechaMedicion = fechaMedicion;
    }

    public long getCantidadTotalSolicitudesAsignadas() {
        return cantidadTotalSolicitudesAsignadas;
    }

    public void setCantidadTotalSolicitudesAsignadas(long cantidadTotalSolicitudesAsignadas) {
        this.cantidadTotalSolicitudesAsignadas = cantidadTotalSolicitudesAsignadas;
    }

    public int getCantidadSolicitudesPendientes() {
        return cantidadSolicitudesPendientes;
    }

    public void setCantidadSolicitudesPendientes(int cantidadSolicitudesPendientes) {
        this.cantidadSolicitudesPendientes = cantidadSolicitudesPendientes;
    }

    public int getCantidadSolicitudesVencidas() {
        return cantidadSolicitudesVencidas;
    }

    public void setCantidadSolicitudesVencidas(int cantidadSolicitudesVencidas) {
        this.cantidadSolicitudesVencidas = cantidadSolicitudesVencidas;
    }

    public int getCantidadSolicitudesIniciadas() {
        return cantidadSolicitudesIniciadas;
    }

    public void setCantidadSolicitudesIniciadas(int cantidadSolicitudesIniciadas) {
        this.cantidadSolicitudesIniciadas = cantidadSolicitudesIniciadas;
    }

    public int getCantidadSolicitudesCerradas() {
        return cantidadSolicitudesCerradas;
    }

    public void setCantidadSolicitudesCerradas(int cantidadSolicitudesCerradas) {
        this.cantidadSolicitudesCerradas = cantidadSolicitudesCerradas;
    }

    public int getCantidadProyectosAcargo() {
        return cantidadProyectosAcargo;
    }

    public void setCantidadProyectosAcargo(int cantidadProyectosAcargo) {
        this.cantidadProyectosAcargo = cantidadProyectosAcargo;
    }

    public int getCantidadProyectosEnQueParticipa() {
        return cantidadProyectosEnQueParticipa;
    }

    public void setCantidadProyectosEnQueParticipa(int cantidadProyectosEnQueParticipa) {
        this.cantidadProyectosEnQueParticipa = cantidadProyectosEnQueParticipa;
    }

    public int getCantidadTareasProyectoAsociadas() {
        return cantidadTareasProyectoAsociadas;
    }

    public void setCantidadTareasProyectoAsociadas(int cantidadTareasProyectoAsociadas) {
        this.cantidadTareasProyectoAsociadas = cantidadTareasProyectoAsociadas;
    }

    public int getCantidadTareasScmAsociadas() {
        return cantidadTareasScmAsociadas;
    }

    public void setCantidadTareasScmAsociadas(int cantidadTareasScmAsociadas) {
        this.cantidadTareasScmAsociadas = cantidadTareasScmAsociadas;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadisticaPersonal)) {
            return false;
        }
        EstadisticaPersonal other = (EstadisticaPersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.EstadisticaPersonal[ id=" + id + " ]";
    }
    
}
