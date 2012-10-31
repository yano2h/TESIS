/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import cl.uv.proyecto.persistencia.ejb.NotificacionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import cl.uv.security.openam.OpenAMUserDetails;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Jano
 */
@ManagedBean
@SessionScoped
public class MbUserInfo implements Serializable {
    @EJB
    private AuthEJBBeanLocal authEJBBean;

    @EJB
    private NotificacionFacadeLocal notificacionFacade;
    
    @ManagedProperty(value = "#{mbUser.funcionario}")
    private Funcionario funcionario;

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    
    public List<Notificacion> getNotificaciones() {
        funcionario.setNotificaciones(notificacionFacade.buscarNotificacionPorDestinatario(funcionario));
        return funcionario.getNotificaciones();
    }

    public void marcarComoRevisada(Notificacion notificacion) {
        if (notificacion != null && !notificacion.getRevisada()) {
            notificacion.setRevisada(true);
            notificacionFacade.edit(notificacion);
        }
    }
    
    public void logout(){
        //JsfUtils.redirect(JsfUtils.getExternalContext().getRequestContextPath()+"/j_spring_security_logout");
        System.out.println("LOGOUT");
        OpenAMUserDetails user = (OpenAMUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authEJBBean.logout(user.getPassword());
    }
    
}
