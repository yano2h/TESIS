/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.TareaProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.TareaProyecto;
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
public class MbTareasProyecto implements Serializable {

    @EJB
    private TareaProyectoFacadeLocal tareaProyectoFacade;
    private List<TareaProyecto> tareasProyecto;
    private TareaProyecto tareaSelected;
    private Proyecto proyecto;
    private Integer porcetajeAvance;

    public MbTareasProyecto() {
    }

    @PostConstruct
    private void init() {
        proyecto = (Proyecto) JsfUtils.getParametro("proyecto");
        tareasProyecto = tareaProyectoFacade.buscarTareasPorProyecto(proyecto);
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public List<TareaProyecto> getTareasProyecto() {
        return tareasProyecto;
    }

    public Integer getPorcetajeAvance() {
        porcetajeAvance = 17;
        return porcetajeAvance;
    }

    public void onRowSelect(){
    
    }
    
    public void listener(){
        System.out.println("POOLING");
    }
}
