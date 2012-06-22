/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.entidades;

import java.io.Serializable;
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
@Table(name = "FUNCIONARIO_DISICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuncionarioDisico.findAll", query = "SELECT f FROM FuncionarioDisico f"),
    @NamedQuery(name = "FuncionarioDisico.findByRut", query = "SELECT f FROM FuncionarioDisico f WHERE f.rut = :rut"),
    @NamedQuery(name = "FuncionarioDisico.findByCargo", query = "SELECT f FROM FuncionarioDisico f WHERE f.cargo = :cargo"),
    @NamedQuery(name = "FuncionarioDisico.findByAnexo", query = "SELECT f FROM FuncionarioDisico f WHERE f.anexo = :anexo")})
public class FuncionarioDisico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut", nullable = false)
    private Integer rut;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cargo", nullable = false, length = 45)
    private String cargo;
    @Size(max = 5)
    @Column(name = "anexo", length = 5)
    private String anexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<ItemConfiguracion> itemConfiguracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<TareaProyecto> tareaProyectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<EstadisticaPersonal> estadisticaPersonalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioDisico")
    private List<SolicitudRequerimiento> solicitudRequerimientoList;
    @JoinColumn(name = "rut", referencedColumnName = "rut", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Funcionario funcionario;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area", nullable = false)
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

    public FuncionarioDisico(Integer rut) {
        this.rut = rut;
    }

    public FuncionarioDisico(Integer rut, String cargo) {
        this.rut = rut;
        this.cargo = cargo;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
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

    @XmlTransient
    public List<ItemConfiguracion> getItemConfiguracionList() {
        return itemConfiguracionList;
    }

    public void setItemConfiguracionList(List<ItemConfiguracion> itemConfiguracionList) {
        this.itemConfiguracionList = itemConfiguracionList;
    }

    @XmlTransient
    public List<TareaProyecto> getTareaProyectoList() {
        return tareaProyectoList;
    }

    public void setTareaProyectoList(List<TareaProyecto> tareaProyectoList) {
        this.tareaProyectoList = tareaProyectoList;
    }

    @XmlTransient
    public List<EstadisticaPersonal> getEstadisticaPersonalList() {
        return estadisticaPersonalList;
    }

    public void setEstadisticaPersonalList(List<EstadisticaPersonal> estadisticaPersonalList) {
        this.estadisticaPersonalList = estadisticaPersonalList;
    }

    @XmlTransient
    public List<SolicitudRequerimiento> getSolicitudRequerimientoList() {
        return solicitudRequerimientoList;
    }

    public void setSolicitudRequerimientoList(List<SolicitudRequerimiento> solicitudRequerimientoList) {
        this.solicitudRequerimientoList = solicitudRequerimientoList;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @XmlTransient
    public List<TareaScmProyecto> getTareaScmProyectoList() {
        return tareaScmProyectoList;
    }

    public void setTareaScmProyectoList(List<TareaScmProyecto> tareaScmProyectoList) {
        this.tareaScmProyectoList = tareaScmProyectoList;
    }

    @XmlTransient
    public List<ParticipanteProyecto> getParticipanteProyectoList() {
        return participanteProyectoList;
    }

    public void setParticipanteProyectoList(List<ParticipanteProyecto> participanteProyectoList) {
        this.participanteProyectoList = participanteProyectoList;
    }

    @XmlTransient
    public List<FormularioImplementacion> getFormularioImplementacionList() {
        return formularioImplementacionList;
    }

    public void setFormularioImplementacionList(List<FormularioImplementacion> formularioImplementacionList) {
        this.formularioImplementacionList = formularioImplementacionList;
    }

    @XmlTransient
    public List<FormularioImplementacion> getFormularioImplementacionList1() {
        return formularioImplementacionList1;
    }

    public void setFormularioImplementacionList1(List<FormularioImplementacion> formularioImplementacionList1) {
        this.formularioImplementacionList1 = formularioImplementacionList1;
    }

    @XmlTransient
    public List<SolicitudCambio> getSolicitudCambioList() {
        return solicitudCambioList;
    }

    public void setSolicitudCambioList(List<SolicitudCambio> solicitudCambioList) {
        this.solicitudCambioList = solicitudCambioList;
    }

    @XmlTransient
    public List<SolicitudCambio> getSolicitudCambioList1() {
        return solicitudCambioList1;
    }

    public void setSolicitudCambioList1(List<SolicitudCambio> solicitudCambioList1) {
        this.solicitudCambioList1 = solicitudCambioList1;
    }

    @XmlTransient
    public List<SolicitudCambio> getSolicitudCambioList2() {
        return solicitudCambioList2;
    }

    public void setSolicitudCambioList2(List<SolicitudCambio> solicitudCambioList2) {
        this.solicitudCambioList2 = solicitudCambioList2;
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
        if (!(object instanceof FuncionarioDisico)) {
            return false;
        }
        FuncionarioDisico other = (FuncionarioDisico) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.uv.proyecto.persistencia.entidades.FuncionarioDisico[ rut=" + rut + " ]";
    }
    
}
