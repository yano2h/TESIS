/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.TareaProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ParticipanteProyecto;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@ViewScoped
public class MbTareasProyecto extends MbBase implements Serializable {

    @EJB
    private TareaProyectoFacadeLocal tareaProyectoFacade;
    private List<TareaProyecto> tareasProyecto;
    private TareaProyecto tareaSelected;
    private Proyecto proyecto;
    private Integer porcetajeAvance;
    private String porcentajeConFormato;
    
    @PostConstruct
    private void init() {
        proyecto = (Proyecto) getValueOfFlashContext("proyecto");
        tareasProyecto = tareaProyectoFacade.buscarTareas(proyecto);
        porcetajeAvance = tareaProyectoFacade.calcularAvancePromedioTareasPorProyecto(proyecto);
        porcentajeConFormato = porcetajeAvance+"%";
    }
    
    public void updatePorcentajeAvance(){
        porcetajeAvance = tareaProyectoFacade.calcularAvancePromedioTareasPorProyecto(proyecto);
        porcentajeConFormato = porcetajeAvance+"%";
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public List<TareaProyecto> getTareasProyecto() {
        return tareasProyecto;
    }

    public void setTareasProyecto(List<TareaProyecto> tareasProyecto) {
        this.tareasProyecto = tareasProyecto;
    }

    public TareaProyecto getTareaSelected() {
        return tareaSelected;
    }

    public void setTareaSelected(TareaProyecto tareaSelected) {
        this.tareaSelected = tareaSelected;
    }

    public String getPorcentajeConFormato() {
        return porcentajeConFormato;
    }

    public void setPorcentajeConFormato(String porcentajeConFormato) {
        this.porcentajeConFormato = porcentajeConFormato;
    }
    
    public void volverProyecto(){
        putValueOnFlashContext("proyecto", proyecto);
        JsfUtils.performNavigation("detalleProyecto_1", true);
    }
    
    public void onRowSelect(){    
    }
    
    public boolean isUsuarioEsParticipanteProyecto(){
        for (ParticipanteProyecto p : proyecto.getParticipantes()) {
            if (p.getParticipante().equals(getFuncionarioDisico())) {
                return true;
            }
        }
        return false;
    }
}
