
package cl.uv.view.controller.scm.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ItemConfiguracionFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.TareaScmProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.view.controller.base.jsf.mb.MbBase;
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
public class MbDetalleScmProyecto extends MbBase {
    
    @EJB 
    private ItemConfiguracionFacadeLocal itemConfiguracionFacade;
    @EJB
    private TareaScmProyectoFacadeLocal tareaScmProyectoFacade;
    
    private Proyecto proyecto;
    
    @PostConstruct
    private void init(){
        proyecto = (Proyecto) getValueOfFlashContext("proyecto");
        if (proyecto != null) {
            System.out.println("CARGA");
            proyecto.setItemsDeConfiguracion(itemConfiguracionFacade.buscarItemsPorProyecto(proyecto));
            System.out.println("IC:"+proyecto.getItemsDeConfiguracion());
            proyecto.setTareasScmProyecto(tareaScmProyectoFacade.buscarTareasSCMPorIdProyecto(proyecto.getIdProyecto()));
            System.out.println("TAREAS:"+proyecto.getTareasScmProyecto());
        }else{
            System.out.println("PROYECTO NULL");
        }
        
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public String volverProyecto(){
        putValueOnFlashContext("proyecto", proyecto);
        return "detalleProyecto_1?faces-redirect=true";
    }
}
