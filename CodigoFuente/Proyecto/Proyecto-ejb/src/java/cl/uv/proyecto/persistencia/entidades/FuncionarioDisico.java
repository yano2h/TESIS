/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jano
 */
@Entity
@Table(name = "FUNCIONARIO_DISICO")
@PrimaryKeyJoinColumn(name="rut")
@NamedQueries({
    @NamedQuery(name = "FuncionarioDisico.findAll", query = "SELECT f FROM FuncionarioDisico f"),
    @NamedQuery(name = "FuncionarioDisico.findByRut", query = "SELECT f FROM FuncionarioDisico f WHERE f.rut = :rut")})
public class FuncionarioDisico extends Funcionario {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 5)
    @Column(name = "anexo")
    private String anexo;
    @JoinColumn(name = "area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area area;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "responsableItem", fetch= FetchType.LAZY)
    private List<ItemConfiguracion> itemsConfiguracionAcargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsableTarea", fetch= FetchType.LAZY)
    private List<TareaProyecto> tareasProyectoAgendadas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico", fetch= FetchType.LAZY)
    private List<EstadisticaPersonal> estadisticasPersonales;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsable", fetch= FetchType.LAZY)
    private List<SolicitudRequerimiento> solicitudesRequerimientosAsignadas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsable", fetch= FetchType.LAZY)
    private List<TareaScmProyecto> tareasScmAsignadas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante", fetch= FetchType.LAZY)
    private List<ParticipanteProyecto> participacionesProyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "implementador", fetch= FetchType.LAZY)
    private List<FormularioImplementacion> implementadorForlumarios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "verificador", fetch= FetchType.LAZY)
    private List<FormularioImplementacion> verificadorFormularios;
    @OneToMany(mappedBy = "evaluadorFinal", fetch= FetchType.LAZY)
    private List<SolicitudCambio> solicitudesCambioEvaluadorFinal;
    @OneToMany(mappedBy = "evaluadorImpacto", fetch= FetchType.LAZY)
    private List<SolicitudCambio> solicitudesCambioEvaluadorImpacto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitante", fetch= FetchType.LAZY)
    private List<SolicitudCambio> solicitudesCambioEnviadas;
    
    /* Atributos no persistidos */
    @Transient
    private Integer cantidadDeSolicitudesAsignadas;
    @Transient
    private Integer cantidadDeSolicitudesPendientes;
    @Transient
    private Integer cantidadDeSolicitudesIniciadas;
    @Transient
    private Integer cantidadDeSolicitudesVencidas;

    public FuncionarioDisico() {
    }

    public FuncionarioDisico(Integer rut, String cargo) {
        super.setRut(rut);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public List<ItemConfiguracion> getItemsConfiguracionAcargo() {
        return itemsConfiguracionAcargo;
    }

    public void setItemsConfiguracionAcargo(List<ItemConfiguracion> itemsConfiguracionAcargo) {
        this.itemsConfiguracionAcargo = itemsConfiguracionAcargo;
    }

    public List<TareaProyecto> getTareasProyectoAgendadas() {
        return tareasProyectoAgendadas;
    }

    public void setTareasProyectoAgendadas(List<TareaProyecto> tareasProyectoAgendadas) {
        this.tareasProyectoAgendadas = tareasProyectoAgendadas;
    }

    public List<EstadisticaPersonal> getEstadisticasPersonales() {
        return estadisticasPersonales;
    }

    public void setEstadisticasPersonales(List<EstadisticaPersonal> estadisticasPersonales) {
        this.estadisticasPersonales = estadisticasPersonales;
    }

    public List<SolicitudRequerimiento> getSolicitudesRequerimientosAsignadas() {
        return solicitudesRequerimientosAsignadas;
    }

    public void setSolicitudesRequerimientosAsignadas(List<SolicitudRequerimiento> solicitudesRequerimientosAsignadas) {
        this.solicitudesRequerimientosAsignadas = solicitudesRequerimientosAsignadas;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<TareaScmProyecto> getTareasScmAsignadas() {
        return tareasScmAsignadas;
    }

    public void setTareasScmAsignadas(List<TareaScmProyecto> tareasScmAsignadas) {
        this.tareasScmAsignadas = tareasScmAsignadas;
    }

    public List<ParticipanteProyecto> getParticipacionesProyecto() {
        return participacionesProyecto;
    }

    public void setParticipacionesProyecto(List<ParticipanteProyecto> participacionesProyecto) {
        this.participacionesProyecto = participacionesProyecto;
    }

    public List<FormularioImplementacion> getImplementadorForlumarios() {
        return implementadorForlumarios;
    }

    public void setImplementadorForlumarios(List<FormularioImplementacion> implementadorForlumarios) {
        this.implementadorForlumarios = implementadorForlumarios;
    }

    public List<FormularioImplementacion> getVerificadorFormularios() {
        return verificadorFormularios;
    }

    public void setVerificadorFormularios(List<FormularioImplementacion> verificadorFormularios) {
        this.verificadorFormularios = verificadorFormularios;
    }

    public List<SolicitudCambio> getSolicitudesCambioEvaluadorFinal() {
        return solicitudesCambioEvaluadorFinal;
    }

    public void setSolicitudesCambioEvaluadorFinal(List<SolicitudCambio> solicitudesCambioEvaluadorFinal) {
        this.solicitudesCambioEvaluadorFinal = solicitudesCambioEvaluadorFinal;
    }

    public List<SolicitudCambio> getSolicitudesCambioEvaluadorImpacto() {
        return solicitudesCambioEvaluadorImpacto;
    }

    public void setSolicitudesCambioEvaluadorImpacto(List<SolicitudCambio> solicitudesCambioEvaluadorImpacto) {
        this.solicitudesCambioEvaluadorImpacto = solicitudesCambioEvaluadorImpacto;
    }

    public List<SolicitudCambio> getSolicitudesCambioEnviadas() {
        return solicitudesCambioEnviadas;
    }

    public void setSolicitudesCambioEnviadas(List<SolicitudCambio> solicitudesCambioEnviadas) {
        this.solicitudesCambioEnviadas = solicitudesCambioEnviadas;
    }

    public Integer getCantidadDeSolicitudesAsignadas() {
        return cantidadDeSolicitudesAsignadas;
    }

    public void setCantidadDeSolicitudesAsignadas(Integer cantidadDeSolicitudesAsignadas) {
        this.cantidadDeSolicitudesAsignadas = cantidadDeSolicitudesAsignadas;
    }

    public Integer getCantidadDeSolicitudesIniciadas() {
        return cantidadDeSolicitudesIniciadas;
    }

    public void setCantidadDeSolicitudesIniciadas(Integer cantidadDeSolicitudesIniciadas) {
        this.cantidadDeSolicitudesIniciadas = cantidadDeSolicitudesIniciadas;
    }

    public Integer getCantidadDeSolicitudesPendientes() {
        return cantidadDeSolicitudesPendientes;
    }

    public void setCantidadDeSolicitudesPendientes(Integer cantidadDeSolicitudesPendientes) {
        this.cantidadDeSolicitudesPendientes = cantidadDeSolicitudesPendientes;
    }

    public Integer getCantidadDeSolicitudesVencidas() {
        return cantidadDeSolicitudesVencidas;
    }

    public void setCantidadDeSolicitudesVencidas(Integer cantidadDeSolicitudesVencidas) {
        this.cantidadDeSolicitudesVencidas = cantidadDeSolicitudesVencidas;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncionarioDisico)) {
            return false;
        }
        FuncionarioDisico other = (FuncionarioDisico) object;
        if ((this.getRut() == null && other.getRut() != null) || (this.getRut() != null && !this.getRut().equals(other.getRut()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash += (this.area != null ? this.area.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.FuncionarioDisico[ rut=" + getRut() + " ]";
    }

  
}
