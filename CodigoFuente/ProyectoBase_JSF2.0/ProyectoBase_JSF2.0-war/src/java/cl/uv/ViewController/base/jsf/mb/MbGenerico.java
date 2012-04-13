package cl.uv.ViewController.base.jsf.mb;

import cl.uv.ViewController.base.jsf.core.ServiceLocator;
import cl.uv.ViewController.base.jsf.core.ServiceLocatorException;
import cl.uv.ViewController.base.utils.JsfUtils;
import javax.faces.bean.ManagedProperty;
import javax.naming.NamingException;

public abstract class MbGenerico {

    protected String sortColumnName;
    protected boolean ascending;
    protected String oldSort;
    protected boolean oldAscending;
    
    @ManagedProperty(value="#{message}")
    private MbMessagePopUp mbMessagePopUp;
    
    @ManagedProperty(value="#{MbInfo}")
    private MbInfo mbInfo;

    @ManagedProperty(value="#{MbSessionController}")
    private MbSessionController sc;
    
    public MbGenerico() {
        sortColumnName = getDefaultSortColumn();
        ascending = isDefaultAscending(sortColumnName);
        oldSort = sortColumnName;
        oldAscending = !ascending;
    }

    /** 
     * En J2EE 6 se puede acceder a los EJB locales atraves de una vista sin interfaz 
     * o utilizando lo anotacion @EJB por lo que estos metodos ya no deviesen necesitarse
     
    public Object getSessionBeanEJBLocal(Class<?> clase) throws NamingException {
        return this.getServiceLocatorLocal(clase.getSimpleName());
    }

    public Object getSessionBeanEJBLocal(String jndi) throws NamingException {
        return this.getServiceLocatorLocal(jndi);
    }

    private Object getServiceLocatorLocal(String jndi) throws NamingException {
        try {
            return ServiceLocator.getInstance().getLocalInterface(jndi);
        } catch (ServiceLocatorException ex) {
            throw new NamingException("No es posible Localizar el EJB");
        }
    }

    **/
    
    public Object getSessionBeanEJBRemote(Class<?> clazz) throws NamingException {
        return this.getServiceLocatorRemote(clazz.getSimpleName(), clazz);
    }

    public Object getSessionBeanEJBRemote(String jndi, Class<?> clazz) throws NamingException {
        return this.getServiceLocatorRemote(jndi, clazz);
    }

    private Object getServiceLocatorRemote(String jndi, Class<?> clazz) throws NamingException {
        try {
            return ServiceLocator.getInstance().getRemoteInterface(jndi, clazz);
        } catch (ServiceLocatorException ex) {
            throw new NamingException("No es posible Localizar el EJB");
        }
    }

    public void goMbInfo(int severidad, String mensaje, String navegacion, String e) {
        mbInfo.createMessage(severidad, mensaje, navegacion, e);
    }

    public void goMsjPupUp(String message) {
        mbMessagePopUp.setMensaje(message);
        mbMessagePopUp.setPopupMessage(true);
    }

    public void goPage(String pagina) {
        sc.navigate(pagina);
    }

    protected abstract void sort();

    protected abstract boolean isDefaultAscending(String sortColumn);

    public String getSortColumnName() {
        return sortColumnName;
    }

    public void setSortColumnName(String sortColumnName) {
        oldSort = this.sortColumnName;
        this.sortColumnName = sortColumnName;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        oldAscending = this.ascending;
        this.ascending = ascending;
    }
    
    public void setMbInfo(MbInfo mbInfo) {
        this.mbInfo = mbInfo;
    }

    public void setMbMessagePopUp(MbMessagePopUp mbMessagePopUp) {
        this.mbMessagePopUp = mbMessagePopUp;
    }

    public void setSc(MbSessionController sc) {
        this.sc = sc;
    }

    public abstract String getDefaultSortColumn();
}

