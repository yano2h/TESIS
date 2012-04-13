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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;

@ManagedBean
@SessionScoped
public final class MbUserInfo extends MbGenerico {
    
    @EJB
    private MenuSuperiorEJBBeanLocal sessionBeanLocal = null;
    
    @ManagedProperty(value="#{mbSSOUtils}")
    private MbSSOUtils mbSSOUtilsBean;     
            
    private List<Aplicacion> listaAplicacion = null;
    private List<String> listaAplicacionesLDAP = null;
    private List<String> listaRoles;
    private Submenu subMenuModel;
    private String uriApacheImages = null;

    public MbUserInfo() {
        try {
            setUriApacheImages(Resources.getValue("basicWebParam_path", "URI_APACHE_IMAGES"));
            getListaAplicionesLDAP();
            doListaRoles();
            doSubMenuItemListaAplicacion();
        } catch (NamingException ne) {
            Logger.getLogger(MbUserInfo.class.getName()).log(Level.SEVERE, null, ne);
        }
    }

    public String onClickSalir() {
        JsfUtils.logout();
        return JsfUtils.redirectTo(Resources.getValue("basicWebParam_path", "URI_REDIRECT_LOGOUT"));
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
            List<String> lista = mbSSOUtilsBean.getAtributosFuncionarios().getListaRoles();
            for (String rol : lista) {
                listaAplicacionesLDAP.add(rol.split("-")[0].split("=")[1]);
            }
        }
    }

    private void doListaRoles() {
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            listaRoles = new ArrayList<String>();
            List<String> lista = mbSSOUtilsBean.getAtributosFuncionarios().getListaRoles();
            String codeApp = Resources.getValue("project_info", "projectCode");
            for (String rol : lista) {
                if ((rol.split("-")[0].split("=")[1]).equals(codeApp)) {
                    listaRoles.add(rol.split("-")[1].split(",")[0].trim());
                }
            }
        }
    }

    private void doSubMenuItemListaAplicacion() {
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            List<Aplicacion> listaAplicacionDB = getListaAplicacionDB();

            if (listaAplicacionesLDAP.size() > 1) {
                subMenuModel = new Submenu();  
                setListaAplicacion(new ArrayList<Aplicacion>());
                String projectCode = Resources.getValue("project_info", "projectCode");
                for (Aplicacion aplicacion : listaAplicacionDB) {
                    if (listaAplicacionesLDAP.contains(aplicacion.getCodigoProyecto()) &&
                        !aplicacion.getCodigoProyecto().equalsIgnoreCase(projectCode)) {
                        MenuItem item = new MenuItem();
                        item.setUrl(aplicacion.getUrl());
                        item.setStyleClass("fixItemMenu left iconApp");
                        item.setIcon(getUriApacheImages() + aplicacion.getImagen());
                        item.setValue(aplicacion.getGlosa());
                        subMenuModel.getChildren().add(item);
                    }
                }
            }
        }
    }

    public void setMbSSOUtilsBean(MbSSOUtils mbSSOUtilsBean) {
        this.mbSSOUtilsBean = mbSSOUtilsBean;
    }
    
    public AtributosFuncionario getAtributosFuncionario() {
        return mbSSOUtilsBean.getAtributosFuncionarios();
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

    public Submenu getSubMenuModel() {
        return subMenuModel;
    }

    public void setSubMenuModel(Submenu subMenuModel) {
        this.subMenuModel = subMenuModel;
    }

    public String getUriApacheImages() {
        return uriApacheImages;
    }

    public void setUriApacheImages(String uriApacheImages) {
        this.uriApacheImages = uriApacheImages;
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
