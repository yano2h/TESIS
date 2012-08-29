/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbMisProyectos extends MbBase{

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
            
    private List<Proyecto> listaProyectos;
    private Proyecto proyectoSelected;
    
    public MbMisProyectos() {
    }
    
    @PostConstruct
    private void init(){
        listaProyectos = proyectoFacade.buscarProyectosPorParticipante(getFuncionarioDisico());
    }

    public List<Proyecto> getListaProyectos() {
        return listaProyectos;
    }

    public Proyecto getProyectoSelected() {
        return proyectoSelected;
    }

    public void setProyectoSelected(Proyecto proyectoSelected) {
        this.proyectoSelected = proyectoSelected;
    }
    
    public void onRowSelect(){
        JsfUtils.performNavigation("detalleProyecto?faces-redirect=true&idProyecto="+proyectoSelected.getIdProyecto(), false);
    }
}
