/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.authentication;

import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.url.utils.UrlResolver;
import cl.uv.security.openam.OpenAMUserDetails;
import cl.uv.security.openid.controller.GmailSession;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@SessionScoped
public class MbSSO implements Serializable{

    private OpenAMUserDetails user;
    
    @PostConstruct
    private void init(){
        user = (OpenAMUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public void redirectHomePage(){
        String paramsUrl = JsfUtils.getExternalContext().getRequestParameterMap().get("url");
        System.out.println("URL redirectHomePage:"+paramsUrl);
        String navegation = "";
        if (JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_JAREA")) 
            || JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_JDEPTO")) 
            || JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_FDISICO")) 
            || JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_ADM")) ) {
            
            navegation = (paramsUrl!=null && !paramsUrl.isEmpty())?
                          UrlResolver.buildRedirectResource(paramsUrl, false):
                          Resources.getValue("pages", "home_page_funcionario");
        }else if(JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_SOLICITANTE"))){
            navegation = (paramsUrl!=null && !paramsUrl.isEmpty())?
                          UrlResolver.buildRedirectResource(paramsUrl, true):
                          Resources.getValue("pages", "home_page_solicitante");
        }
        System.out.println("NAVEGATION:"+navegation);
        JsfUtils.performNavigation(navegation , true);
    }

    public OpenAMUserDetails getUser() {
        return user;
    }
    
    public void logout(){
        GmailSession gmailSession = (GmailSession) JsfUtils.getValue("gmailSession");
        gmailSession.closeSession();
        JsfUtils.redirect(JsfUtils.getExternalContext().getRequestContextPath()+"/j_spring_security_logout");
    }
    
}
