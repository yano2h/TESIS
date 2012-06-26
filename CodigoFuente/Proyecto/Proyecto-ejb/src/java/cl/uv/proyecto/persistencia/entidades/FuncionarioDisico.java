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
 * @author Alejandro
 */
@Entity
@Table(name = "FUNCIONARIO_DISICO")
@PrimaryKeyJoinColumn(name="rut")
@NamedQueries({
    @NamedQuery(name = "FuncionarioDisico.findAll", query = "SELECT f FROM FuncionarioDisico f"),
    @NamedQuery(name = "FuncionarioDisico.findByRut", query = "SELECT f FROM FuncionarioDisico f WHERE f.rut = :rut"),
    @NamedQuery(name = "FuncionarioDisico.findByCargo", query = "SELECT f FROM FuncionarioDisico f WHERE f.cargo = :cargo"),
    @NamedQuery(name = "FuncionarioDisico.findByAnexo", query = "SELECT f FROM FuncionarioDisico f WHERE f.anexo = :anexo")})
public class FuncionarioDisico extends Funcionario {
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 5)
    @Column(name = "anexo")
    private String anexo;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area area; 
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<ItemConfiguracion> itemConfiguracionList; //lista de items de configuracion de los que es responsable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<TareaProyecto> tareaProyectoList; //lista de las tareas de proyecto de las que es responsable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<EstadisticaPersonal> estadisticaPersonalList; 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<SolicitudRequerimiento> solicitudesRequerimientosAsignadas; //lista de solicitudes que le han sido asignadas
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsableTarea")
    private List<TareaScmProyecto> tareaScmProyectoList; //tareas de scm de las que es responsable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<ParticipanteProyecto> proyectosEnQueParticipa; //proyectos en los que ha trabajado
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "implementador")
    private List<FormularioImplementacion> formulariosImplementacionImplementador; // Lista de formularios en los que aparece como implementador
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "verificador")
    private List<FormularioImplementacion> formulariosImplementacionVerificador; // Lista de formularios en los que aparece como verificador
    @OneToMany(mappedBy = "evaluadorFinalSolicitud")
    private List<SolicitudCambio> solicitudesCambioEvaluadorFinal; // Lista de solicitudes de cambio en las que aparece como evaluador final
    @OneToMany(mappedBy = "evaluadorImpacto")
    private List<SolicitudCambio> solicitudesCambioEvaluadorImpacto; // Lista de solicitudes de cambio en las que aparece como evaluador del impacto
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitante")
    private List<SolicitudCambio> solicitudesCambioEnviadas;  // Lista de solicitudes de cambio que ha enviado

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

    public List<ItemConfiguracion> getItemConfiguracionList() {
        return itemConfiguracionList;
    }

    public void setItemConfiguracionList(List<ItemConfiguracion> itemConfiguracionList) {
        this.itemConfiguracionList = itemConfiguracionList;
    }

    public List<TareaProyecto> getTareaProyectoList() {
        return tareaProyectoList;
    }

    public void setTareaProyectoList(List<TareaProyecto> tareaProyectoList) {
        this.tareaProyectoList = tareaProyectoList;
    }

    public List<EstadisticaPersonal> getEstadisticaPersonalList() {
        return estadisticaPersonalList;
    }

    public void setEstadisticaPersonalList(List<EstadisticaPersonal> estadisticaPersonalList) {
        this.estadisticaPersonalList = estadisticaPersonalList;
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

    public List<TareaScmProyecto> getTareaScmProyectoList() {
        return tareaScmProyectoList;
    }

    public void setTareaScmProyectoList(List<TareaScmProyecto> tareaScmProyectoList) {
        this.tareaScmProyectoList = tareaScmProyectoList;
    }

    public List<ParticipanteProyecto> getProyectosEnQueParticipa() {
        return proyectosEnQueParticipa;
    }

    public void setProyectosEnQueParticipa(List<ParticipanteProyecto> proyectosEnQueParticipa) {
        this.proyectosEnQueParticipa = proyectosEnQueParticipa;
    }

    public List<FormularioImplementacion> getFormulariosImplementacionImplementador() {
        return formulariosImplementacionImplementador;
    }

    public void setFormulariosImplementacionImplementador(List<FormularioImplementacion> formulariosImplementacionImplementador) {
        this.formulariosImplementacionImplementador = formulariosImplementacionImplementador;
    }

    public List<FormularioImplementacion> getFormulariosImplementacionVerificador() {
        return formulariosImplementacionVerificador;
    }

    public void setFormulariosImplementacionVerificador(List<FormularioImplementacion> formulariosImplementacionVerificador) {
        this.formulariosImplementacionVerificador = formulariosImplementacionVerificador;
    }

    public List<SolicitudCambio> getSolicitudesCambioEvaluadorFinal() {
        return solicitudesCambioEvaluadorFinal;
    }

    public void setSolicitudesCambioEvaluadorFinal(List<SolicitudCambio> solicitudesCambioEvaluadorFinal) {
        this.solicitudesCambioEvaluadorFinal = solicitudesCambioEvaluadorFinal;
    }

    public List<SolicitudCambio> getSoolicitudesCambioEvaluadorImpacto() {
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
