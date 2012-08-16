/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.NotificacionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import cl.uv.security.openam.OpenAMUserDetails;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.security.core.GrantedAuthority;
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
    private FuncionarioFacadeLocal funcionarioFacade;
    @EJB
    private NotificacionFacadeLocal notificacionFacade;
    private Funcionario funcionario;

    public MbUserInfo() {
    }

    @PostConstruct
    public void init() {
        funcionario = funcionarioFacade.find(new Integer(16775578));
        if(funcionario.getFechaPrimerAcceso()==null){
            funcionario.setFechaPrimerAcceso(new Date());
        }
        funcionario.setFechaUltimoAcceso(new Date());
        funcionarioFacade.edit(funcionario);
        
        OpenAMUserDetails user = (OpenAMUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        for (GrantedAuthority a : user.getAuthorities()) {
            String tempRol = a.getAuthority();
        }
        
    }

    public Funcionario getFuncionario() {
        return funcionario;
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
       // JsfUtils.redirect(JsfUtils.getExternalContext().getRequestContextPath()+"/j_spring_security_logout");
        System.out.println("LOGOUT");
        OpenAMUserDetails user = (OpenAMUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authEJBBean.logout(user.getPassword());
    }
    
}
