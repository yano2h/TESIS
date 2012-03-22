package cl.uv.ViewController.base.jsf.mb;

import cl.uv.ViewController.base.jsf.core.ServiceLocator;
import cl.uv.ViewController.base.jsf.core.ServiceLocatorException;
import cl.uv.ViewController.base.utils.Resources;
import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import java.net.URLDecoder;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;

@EJB(name = "AuthEJBBeanLocal", beanInterface = AuthEJBBeanLocal.class)
public class MbSSOUtils {

    private AuthEJBBeanLocal sessionBeanLocal = null;
    private String tokenCookie = "";

    public MbSSOUtils() {
        try {
            if (!(sessionBeanLocal instanceof AuthEJBBeanLocal)) {
                sessionBeanLocal = (AuthEJBBeanLocal) getSessionBeanEJBLocal(AuthEJBBeanLocal.class);
            }
        } catch (NamingException ne) {
        }
    }

    private static Object getSessionBeanEJBLocal(Class<?> clase) throws NamingException {
        return getServiceLocatorLocal(clase.getSimpleName());
    }

    private static Object getServiceLocatorLocal(String jndi) throws NamingException {
        try {
            return ServiceLocator.getInstance().getLocalInterface(jndi);
        } catch (ServiceLocatorException sle) {
            return null;
        }
    }

    private String getTokenCookie() {
        if (tokenCookie.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().
                    getRequest();
            Cookie cookie[] = request.getCookies();

            String iPlanetDirectoryPro = Resources.getValue("basicWebParam_path", "nombreCookie");
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
                        if (nameCookie.equals(iPlanetDirectoryPro)) {
                            valueCookie = cookie[count].getValue();
                        }
                    }
                }
            }
            if (valueCookie != null && !valueCookie.isEmpty()) {
                tokenCookie = URLDecoder.decode(valueCookie);
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
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            return getAtributosFuncionarios().getCn();
        } else {
            return Resources.getValue("basicWebParam_path", "noLDAPConexion");
        }
    }

    public AuthEJBBeanLocal getSessionBeanLocal() {
        return sessionBeanLocal;
    }
}
