package cl.uv.ViewController.base.jsf.mb;

import cl.uv.ViewController.base.utils.JsfUtils;
import cl.uv.ViewController.base.utils.Resources;
import cl.uv.model.base.core.beans.Aplicacion;
import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.MenuSuperiorEJBBeanLocal;
import cl.uv.model.nombreProyecto.data.Dao.DAOException;
import com.icesoft.faces.component.menubar.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;

@EJB(name = "MenuSuperiorEJBBeanLocal", beanInterface = MenuSuperiorEJBBeanLocal.class)
public final class MbUserInfo extends MbGenerico {

    private MenuSuperiorEJBBeanLocal sessionBeanLocal = null;
    private List<Aplicacion> listaAplicacion = null;
    private List<String> listaAplicacionesLDAP = null;
    private List<String> listaRoles;
    private List menuModel;
    private String uriApacheImages = null;

    public MbUserInfo() {
        try {
            setSessionBeanLocal((MenuSuperiorEJBBeanLocal) getSessionBeanEJBLocal(MenuSuperiorEJBBeanLocal.class));
            setUriApacheImages(Resources.getValue("basicWebParam_path", "URI_APACHE_IMAGES"));
            getListaAplicionesLDAP();
            doListaRoles();
            doMenuItemListaAplicacion();
        } catch (NamingException ne) {
            Logger.getLogger(MbUserInfo.class.getName()).log(Level.SEVERE, null, ne);
        }
    }

    public void onClickSalir(ActionEvent ev) {
        Map map = JsfUtils.getExternalContext().getSessionMap();
        map.remove("MbGenerico");
        map.remove("MbInfo");
        map.remove("MbLeftMenuController");
        map.remove("MbMessagePopUp");
        map.remove("MbSSOUtils");
        map.remove("MbSessionController");
        map.remove("MbUserInfo");
        Map mapRequest = JsfUtils.getExternalContext().getRequestMap();
        mapRequest.remove("MbAlumno");
        JsfUtils.logout();
        JsfUtils.redirect(Resources.getValue("basicWebParam_path", "URI_REDIRECT_LOGOUT"));
    }

    private List<Aplicacion> getListaAplicacionDB() {
        try {
            return getSessionBeanLocal().findAllAplicacion();
        } catch (DAOException ex) {
            Logger.getLogger(MbUserInfo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean userInApp() {
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            return (listaAplicacionesLDAP.contains(Resources.getValue("project_info", "projectCode")));
        }
        return true;
    }

    private void getListaAplicionesLDAP() {
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            listaAplicacionesLDAP = new ArrayList<String>();
            List<String> lista = ((MbSSOUtils) JsfUtils.getValue("#{MbSSOUtils}")).getAtributosFuncionarios().getListaRoles();
            for (String rol : lista) {
                listaAplicacionesLDAP.add(rol.split("-")[0].split("=")[1]);
            }
        }
    }

    private void doListaRoles() {
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            listaRoles = new ArrayList<String>();
            List<String> lista = ((MbSSOUtils) JsfUtils.getValue("#{MbSSOUtils}")).getAtributosFuncionarios().getListaRoles();
            String codeApp = Resources.getValue("project_info", "projectCode");
            for (String rol : lista) {
                if ((rol.split("-")[0].split("=")[1]).equals(codeApp)) {
                    listaRoles.add(rol.split("-")[1].split(",")[0].trim());
                }
            }
        }
    }

    private void doMenuItemListaAplicacion() {
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            List<Aplicacion> listaAplicacionDB = getListaAplicacionDB();
            MenuItem menuItem = null;

            if (listaAplicacionesLDAP.size() > 1) {
                setMenuModel(new ArrayList());
                setListaAplicacion(new ArrayList<Aplicacion>());
                String projectCode = Resources.getValue("project_info", "projectCode");
                for (Aplicacion aplicacion : listaAplicacionDB) {
                    if (listaAplicacionesLDAP.contains(aplicacion.getCodigoProyecto()) &&
                        !aplicacion.getCodigoProyecto().equalsIgnoreCase(projectCode)) {
                        menuItem = new MenuItem();
                        menuItem.setLink(aplicacion.getUrl());
                        menuItem.setStyleClass("fixItemMenu left iconApp");
                        menuItem.setTitle(aplicacion.getGlosa());
                        menuItem.setIcon(getUriApacheImages() + aplicacion.getImagen());
                        menuItem.setValue(aplicacion.getGlosa());
                        getMenuModel().add(menuItem);
                    }
                }
            }
        }
    }

    public AtributosFuncionario getAtributosFuncionario() {
        return ((MbSSOUtils) JsfUtils.getValue("#{MbSSOUtils}")).getAtributosFuncionarios();
    }

    public MenuSuperiorEJBBeanLocal getSessionBeanLocal() {
        return sessionBeanLocal;
    }

    public void setSessionBeanLocal(MenuSuperiorEJBBeanLocal sessionBeanLocal) {
        this.sessionBeanLocal = sessionBeanLocal;
    }

    public List<Aplicacion> getListaAplicacion() {
        return listaAplicacion;
    }

    public void setListaAplicacion(List<Aplicacion> listaAplicacion) {
        this.listaAplicacion = listaAplicacion;
    }

    public List getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(ArrayList menuModel) {
        this.menuModel = menuModel;
    }

    public String getUriApacheImages() {
        return uriApacheImages;
    }

    public void setUriApacheImages(String uriApacheImages) {
        this.uriApacheImages = uriApacheImages;
    }

    /*Solo para test de pagina. puede ser eliminada*/
    public List<String> getListaBeanTest() {
        List lista = new ArrayList<String>();
        lista.add("Cadena de texto 1");
        lista.add("Cadena de texto 2");
        lista.add("Cadena de texto 3");
        lista.add("Cadena de texto 4");
        lista.add("Cadena de texto 5");
        return lista;
    }

    public List<String> getListaCabecera() {
        List lista = new ArrayList<String>();
        lista.add("Cabecera 1");
        lista.add("Cabecera 2");
        lista.add("Cabecera 3");
        return lista;
    }

    public List<String> getListaContenido() {
        List lista = new ArrayList<String>();
        lista.add("Contenido 1");
        lista.add("Contenido 2");
        lista.add("Contenido 3");
        lista.add("Contenido 4");
        lista.add("Contenido 5");
        return lista;
    }

    public List<String> getListaRoles() {
        return listaRoles;
    }

    @Override
    protected void sort() {
    }

    @Override
    protected boolean isDefaultAscending(String sortColumn) {
        return false;
    }

    @Override
    public String getDefaultSortColumn() {
        return null;
    }
}
