/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.view.controller.base.jsf.mb.MbBase;
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
            
    public MbMisProyectos() {
    }
}
