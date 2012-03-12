package cl.uv.ViewController.base.utils;

import java.io.IOException;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

public class JsfUtils {

    public static Context getInitialContext() throws NamingException {
        return new InitialContext();
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext getExternalContext() {
        return JsfUtils.getFacesContext().getExternalContext();
    }
    
    /**
     * En JSF 2 se usa la anotacion @ManagedProperty
     * @param el
     * @return
     * @deprecated
     */
    @Deprecated
    public static Object getValue(String el) {
        FacesContext ctx = getFacesContext();
        return ctx.getApplication().createValueBinding(el).getValue(ctx);
    }

    public static void handleNavigation(String action) {
        FacesContext ctx = getFacesContext();
        NavigationHandler nh = ctx.getApplication().getNavigationHandler();
        nh.handleNavigation(ctx, null, action);
    }

    public static void logout() {
        HttpSession session =
                (HttpSession) getExternalContext().getSession(false);
        session.invalidate();
    }
    
    public static void redirect(String url) {
        try {
            getFacesContext().getExternalContext().redirect(url);
        } catch (IOException ex) {
        }
    }

    /**
     * Metodo para logout, invalida la sesion
     * @param page
     * @return El string page concatenado con ?faces-redirect=true para 
     * redireccionar correctamente a otra pagina
     */
    public static String logout(String page) {
        getExternalContext().invalidateSession();
        return redirectTo(page);
    }

    /**
     * Añade a page la cadena ?faces-redirect=true para utilizar 
     * Page Redirection de JSF
     * @param page
     * @return 
     */
    public static String redirectTo(String page) {
        if (!page.endsWith("?faces-redirect=true")) {
            page += "?faces-redirect=true";
        }
        return page;
    }

    public String getViewId() {
        UIViewRoot viewRoot = getFacesContext().getViewRoot();
        return viewRoot.getViewId();
    }
    
    public static UIComponent findComponent(String id) {
        return getFacesContext().getViewRoot().findComponent(id);
    }

}
