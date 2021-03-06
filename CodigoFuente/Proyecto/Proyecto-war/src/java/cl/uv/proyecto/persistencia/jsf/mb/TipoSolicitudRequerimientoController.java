package cl.uv.proyecto.persistencia.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.TipoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.TipoSolicitudRequerimiento;
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

@ManagedBean(name = "tipoSolicitudRequerimientoController")
@SessionScoped
public class TipoSolicitudRequerimientoController implements Serializable {

    private TipoSolicitudRequerimiento current;
    private DataModel items = null;
    @EJB
    private TipoSolicitudRequerimientoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TipoSolicitudRequerimientoController() {
    }

    public TipoSolicitudRequerimiento getSelected() {
        if (current == null) {
            current = new TipoSolicitudRequerimiento();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoSolicitudRequerimientoFacadeLocal getFacade() {
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
        current = (TipoSolicitudRequerimiento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TipoSolicitudRequerimiento();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(Resources.getValue("bundle","TipoSolicitudRequerimientoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle","PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TipoSolicitudRequerimiento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(Resources.getValue("bundle","TipoSolicitudRequerimientoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle","PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TipoSolicitudRequerimiento) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(Resources.getValue("bundle","TipoSolicitudRequerimientoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle","PersistenceErrorOccured"));
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
        return JsfUtils.getSelectItems(ejbFacade.findAll(), "getNombreTipoSolicitud",false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtils.getSelectItems(ejbFacade.findAll(), "getNombreTipoSolicitud",true);
    }
    
    public SelectItem[] getFilterOptions() {
        return JsfUtils.getFilterOptions(ejbFacade.findAll(), "getNombreTipoSolicitud");
    }
    
    @FacesConverter(forClass = TipoSolicitudRequerimiento.class)
    public static class TipoSolicitudRequerimientoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoSolicitudRequerimientoController controller = (TipoSolicitudRequerimientoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoSolicitudRequerimientoController");
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
            if (object instanceof TipoSolicitudRequerimiento) {
                TipoSolicitudRequerimiento o = (TipoSolicitudRequerimiento) object;
                return getStringKey(o.getIdTipoSolicitudRequerimiento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoSolicitudRequerimientoController.class.getName());
            }
        }
    }
}
