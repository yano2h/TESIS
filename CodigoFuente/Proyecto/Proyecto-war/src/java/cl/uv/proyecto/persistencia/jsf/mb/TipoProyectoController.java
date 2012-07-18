package cl.uv.proyecto.persistencia.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.TipoProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.TipoProyecto;
import cl.uv.proyecto.persistencia.jsf.mb.util.JsfUtil;
import cl.uv.proyecto.persistencia.jsf.mb.util.PaginationHelper;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "tipoProyectoController")
@SessionScoped
public class TipoProyectoController implements Serializable {

    private TipoProyecto current;
    private DataModel items = null;
    @EJB
    private TipoProyectoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TipoProyectoController() {
    }

    public TipoProyecto getSelected() {
        if (current == null) {
            current = new TipoProyecto();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoProyectoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (TipoProyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TipoProyecto();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(Resources.getValue("bundle", "TipoProyectoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle", "PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TipoProyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(Resources.getValue("bundle", "TipoProyectoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle", "PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TipoProyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(Resources.getValue("bundle", "TipoProyectoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle", "PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtils.getSelectItems(ejbFacade.findAll(), "getNombreTipoProyecto",false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtils.getSelectItems(ejbFacade.findAll(), "getNombreTipoProyecto",true);
    }

    @FacesConverter(forClass = TipoProyecto.class)
    public static class TipoProyectoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoProyectoController controller = (TipoProyectoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoProyectoController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Short getKey(String value) {
            java.lang.Short key;
            key = Short.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Short value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoProyecto) {
                TipoProyecto o = (TipoProyecto) object;
                return getStringKey(o.getIdTipoProyecto());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoProyectoController.class.getName());
            }
        }
    }
}
