/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Alejandro
 */
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

    public static Object getValue(String el) {
        FacesContext ctx = getFacesContext();
        //return ctx.getELContext().getELResolver().getValue(ctx.getELContext(), null, beanName);
        // return ctx.getApplication().createValueBinding(el).getValue(ctx);
        return getFacesContext().getApplication().getELResolver().getValue(ctx.getELContext(), null, el);
    }

    public static void handleNavigation(String action) {
        FacesContext ctx = getFacesContext();
        NavigationHandler nh = ctx.getApplication().getNavigationHandler();
        nh.handleNavigation(ctx, null, action);
    }

    public static void performNavigation(String action, Boolean addRedirect) {
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) getFacesContext().getApplication().getNavigationHandler();
        if (addRedirect) {
            handler.performNavigation(redirectTo(action));
        } else {
            handler.performNavigation(action);
        }
    }

    public static void addParametro(String key, Object v) {
        getExternalContext().getSessionMap().put(key, v);
    }

    public static Object getParametro(String key) {
        return getExternalContext().getSessionMap().get(key);
    }

    public static Object removerParametro(String key) {
        return getExternalContext().getSessionMap().remove(key);
    }

    public static void logout() {
        getExternalContext().invalidateSession();
    }

    public static void redirect(String url) {
        try {
            getFacesContext().getExternalContext().redirect(url);
        } catch (IOException ex) {
        }
    }

    public static void addMessage(Severity severity, String summary, String detail) {
        getFacesContext().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    /**
     * Metodo para logout, invalida la sesion
     *
     * @param page
     * @return El string page concatenado con ?faces-redirect=true para
     * redireccionar correctamente a otra pagina
     */
    public static String logout(String page) {
        getExternalContext().invalidateSession();
        return redirectTo(page);
    }

    /**
     * Añade a page la cadena ?faces-redirect=true para utilizar Page
     * Redirection de JSF
     *
     * @param page
     * @return
     */
    public static String redirectTo(String page) {
        if (!page.endsWith("?faces-redirect=true")) {
            if(page.contains("?")){
                page += "&faces-redirect=true";
            }else{
                page += "?faces-redirect=true";
            }
            
        }
        return page;
    }

    public String getViewId() {
        return getFacesContext().getViewRoot().getViewId();
    }

    public static UIComponent findComponent(String id) {
        return getFacesContext().getViewRoot().findComponent(id);
    }

    public static SelectItem[] getSelectItems(List<?> entities, String nombreMetodo, boolean selectOne) {

        int size = selectOne ? entities.size() + 1 : entities.size();

        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[i++] = new SelectItem("", "---");
        }
        try {
            if (entities.size() > 0) {
                Class clase = entities.get(0).getClass();
                Method getLabel = clase.getMethod(nombreMetodo, null);
                for (Object o : entities) {
                    Object labelSelectItem = getLabel.invoke(o, null);
                    items[i++] = new SelectItem(o, labelSelectItem.toString());
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "No se pudo acceder al metodo " + nombreMetodo + " mediante reflexion", ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "Los argumentos especificados no coinciden con los del metodo: " + nombreMetodo + "", ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "El metodo " + nombreMetodo + " no pudo ser localizado", ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return items;
        }
    }

    public static SelectItem[] getSelectItems(List<?> entities, String nombreMetodo, String mensajeInicial) {

        int size = entities.size() + 1;
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        items[i++] = new SelectItem("", mensajeInicial);
        try {
            if (entities.size() > 0) {
                Class clase = entities.get(0).getClass();
                Method getLabel = clase.getMethod(nombreMetodo, null);
                for (Object o : entities) {
                    Object labelSelectItem = getLabel.invoke(o, null);
                    items[i++] = new SelectItem(o, labelSelectItem.toString());
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "No se pudo acceder al metodo " + nombreMetodo + " mediante reflexion", ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "Los argumentos especificados no coinciden con los del metodo: " + nombreMetodo + "", ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "El metodo " + nombreMetodo + " no pudo ser localizado", ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return items;
        }

    }

    public static SelectItem[] getFilterOptions(List<?> entities, String nombreMetodo) {

        SelectItem[] items = new SelectItem[entities.size() + 1];
        int i = 0;
        items[i++] = new SelectItem("", "---");

        try {
            Class clase = entities.get(0).getClass();
            Method getLabel = clase.getMethod(nombreMetodo, null);
            for (Object o : entities) {
                Object labelSelectItem = getLabel.invoke(o, null);
                items[i++] = new SelectItem(labelSelectItem.toString(), labelSelectItem.toString());
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "No se pudo acceder al metodo " + nombreMetodo + " mediante reflexion", ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "Los argumentos especificados no coinciden con los del metodo: " + nombreMetodo + "", ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, "El metodo " + nombreMetodo + " no pudo ser localizado", ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JsfUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return items;
        }

    }
}
