package cl.uv.view.controller.proyecto.jsf.mb;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import java.io.Serializable;
import java.net.URLDecoder;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name="mbSSOUtils")
@SessionScoped
public class MbSSOUtils implements Serializable{

    @EJB
    private AuthEJBBeanLocal sessionBeanLocal;
    private String tokenCookie = "";

    public MbSSOUtils() {}
    
    private String getTokenCookie() {
        if (tokenCookie.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().
                    getRequest();
            Cookie cookie[] = request.getCookies();

            String iPlanetDirectoryPro = "iPlanetDirectoryPro";
            String nameCookie = null;
            String valueCookie = null;

            if (request.getHeader("user-agent").contains("MSIE")) {
                if (request.getParameter(iPlanetDirectoryPro) != null) {
                    valueCookie = request.getParameter(iPlanetDirectoryPro);
                }
            } else {
                if (cookie != null && cookie.length > 0) {
                    for (int count = 0; count < cookie.length; count++) {
                        nameCookie = cookie[count].getName();
                        System.out.println("Cookie:"+nameCookie);
                        if (nameCookie.equals(iPlanetDirectoryPro)) {
                            valueCookie = cookie[count].getValue();
                            System.out.println("ValueCookie:"+valueCookie);
                        }
                    }
                }
            }
            if (valueCookie != null && !valueCookie.isEmpty()) {
                tokenCookie = URLDecoder.decode(valueCookie);
                System.out.println("Token:"+tokenCookie);
            }
        }
        return tokenCookie;
    }

    public boolean doValidateUser() {
        return getSessionBeanLocal().validateToken(getTokenCookie());
    }

    public AtributosFuncionario getAtributosFuncionarios() {
        return getSessionBeanLocal().getAtributosFuncionarios(getTokenCookie());
    }

    public String getNombreFuncionario() {
        return getAtributosFuncionarios().getCn();
    }

    public AuthEJBBeanLocal getSessionBeanLocal() {
        return sessionBeanLocal;
    }
}
