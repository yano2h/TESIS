/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.RegistroBitacoraFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.RegistroBitacora;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class MbBitacoraProyecto extends MbBase implements Serializable {

    @EJB
    private RegistroBitacoraFacadeLocal registroBitacoraFacade;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    
    private Proyecto proyecto;
    private RegistroBitacora nuevoRegistroBitacora;
    private Date fechaUltimoRegistroBitacora;
    
    @PostConstruct
    private void init(){
        proyecto = (Proyecto) getValueOfFlashContext("proyecto");
        proyecto.setBitacora(registroBitacoraFacade.buscarBitacoraProyecto(proyecto));
        if (proyecto.getBitacora()!=null && !proyecto.getBitacora().isEmpty()) {
            fechaUltimoRegistroBitacora = registroBitacoraFacade.getFechaUltimoRegistro(proyecto);
        }
        
        crearNuevoRegistro();
    }
    
    public String volver(){
        putValueOnFlashContext("proyecto", proyecto);
        return "detalleProyecto_1?faces-redirect=true";
    }

    public List<String> complete(String query) {  
        List<String> coincidencias = registroBitacoraFacade.buscarContraparte(query); 
        System.out.println("Complete:"+coincidencias);
        return coincidencias;
    }  
    
    public void agregarRegistroBitacora(){
        registroBitacoraFacade.create(nuevoRegistroBitacora);
        proyecto.setEstadoProyecto(nuevoRegistroBitacora.getEstadoProyecto());
        if (proyecto.getBitacora()==null) {
            proyecto.setBitacora(new ArrayList<RegistroBitacora>());
        }
        proyecto.getBitacora().add(nuevoRegistroBitacora);
        proyectoFacade.edit(proyecto);
    }
            
    public void crearNuevoRegistro(){
        nuevoRegistroBitacora = new RegistroBitacora(new Date(), proyecto);
        nuevoRegistroBitacora.setEstadoProyecto(proyecto.getEstadoProyecto());
        nuevoRegistroBitacora.setFuncionarioResponsable(getFuncionarioDisico());
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public RegistroBitacora getNuevoRegistroBitacora() {
        return nuevoRegistroBitacora;
    }

    public void setNuevoRegistroBitacora(RegistroBitacora nuevoRegistroBitacora) {
        this.nuevoRegistroBitacora = nuevoRegistroBitacora;
    }

    public Date getFechaUltimoRegistroBitacora() {
        return fechaUltimoRegistroBitacora;
    }

    public void setFechaUltimoRegistroBitacora(Date fechaUltimoRegistroBitacora) {
        this.fechaUltimoRegistroBitacora = fechaUltimoRegistroBitacora;
    }

}
