/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.NotificacionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;


/**
 *
 * @author Jano
 */
@ManagedBean
@SessionScoped
public class MbUserInfo implements Serializable{
    
    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;
    @EJB
    private NotificacionFacadeLocal notificacionFacade;
    
    private Funcionario funcionario;
    
    public MbUserInfo() {
    }
    
    @PostConstruct
    public void init(){
        funcionario = funcionarioFacade.find(new Integer(16775578));
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    public List<Notificacion> getNotificaciones(){
        funcionario.setNotificaciones(notificacionFacade.buscarNotificacionPorDestinatario(funcionario));
        return funcionario.getNotificaciones();
    }
    
    public void marcarComoRevisada(AjaxBehaviorEvent e){
       // Notificacion n = (Notificacion) JsfUtils.getFacesContext().getAttributes().get("notificacion");

        //Object o = e.getSource();
        System.out.println("REVISADA: ");
    }
}
