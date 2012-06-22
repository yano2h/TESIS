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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<ItemConfiguracion> itemConfiguracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<TareaProyecto> tareaProyectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<EstadisticaPersonal> estadisticaPersonalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<SolicitudRequerimiento> solicitudesRequerimientosAsignadas;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area area;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<TareaScmProyecto> tareaScmProyectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<ParticipanteProyecto> participanteProyectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<FormularioImplementacion> formularioImplementacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico1")
    private List<FormularioImplementacion> formularioImplementacionList1;
    @OneToMany(mappedBy = "funcionarioDisico")
    private List<SolicitudCambio> solicitudCambioList;
    @OneToMany(mappedBy = "funcionarioDisico1")
    private List<SolicitudCambio> solicitudCambioList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico2")
    private List<SolicitudCambio> solicitudCambioList2;

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

    public List<ParticipanteProyecto> getParticipanteProyectoList() {
        return participanteProyectoList;
    }

    public void setParticipanteProyectoList(List<ParticipanteProyecto> participanteProyectoList) {
        this.participanteProyectoList = participanteProyectoList;
    }

    public List<FormularioImplementacion> getFormularioImplementacionList() {
        return formularioImplementacionList;
    }

    public void setFormularioImplementacionList(List<FormularioImplementacion> formularioImplementacionList) {
        this.formularioImplementacionList = formularioImplementacionList;
    }

    public List<FormularioImplementacion> getFormularioImplementacionList1() {
        return formularioImplementacionList1;
    }

    public void setFormularioImplementacionList1(List<FormularioImplementacion> formularioImplementacionList1) {
        this.formularioImplementacionList1 = formularioImplementacionList1;
    }

    public List<SolicitudCambio> getSolicitudCambioList() {
        return solicitudCambioList;
    }

    public void setSolicitudCambioList(List<SolicitudCambio> solicitudCambioList) {
        this.solicitudCambioList = solicitudCambioList;
    }

    public List<SolicitudCambio> getSolicitudCambioList1() {
        return solicitudCambioList1;
    }

    public void setSolicitudCambioList1(List<SolicitudCambio> solicitudCambioList1) {
        this.solicitudCambioList1 = solicitudCambioList1;
    }

    public List<SolicitudCambio> getSolicitudCambioList2() {
        return solicitudCambioList2;
    }

    public void setSolicitudCambioList2(List<SolicitudCambio> solicitudCambioList2) {
        this.solicitudCambioList2 = solicitudCambioList2;
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
