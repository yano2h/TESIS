package cl.uv.proyecto.persistencia.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.jsf.mb.util.JsfUtil;
import cl.uv.proyecto.persistencia.jsf.mb.util.PaginationHelper;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import java.util.List;
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

@ManagedBean(name = "funcionarioDisicoController")
@SessionScoped
public class FuncionarioDisicoController implements Serializable {

    private FuncionarioDisico current;
    private DataModel items = null;
    @EJB
    private FuncionarioDisicoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public FuncionarioDisicoController() {
    }

    public FuncionarioDisico getSelected() {
        if (current == null) {
            current = new FuncionarioDisico();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FuncionarioDisicoFacadeLocal getFacade() {
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
        current = (FuncionarioDisico) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new FuncionarioDisico();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(Resources.getValue("bundle", "FuncionarioDisicoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle", "PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (FuncionarioDisico) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(Resources.getValue("bundle", "FuncionarioDisicoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Resources.getValue("bundle", "PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (FuncionarioDisico) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(Resources.getValue("bundle", "FuncionarioDisicoDeleted"));
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
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectManyNombreCompleto() {
        List<FuncionarioDisico> entities = ejbFacade.findAll();
        SelectItem[] listaItems = new SelectItem[entities.size()];
        int i = 0;
        for (FuncionarioDisico f : entities) {
            listaItems[i++] = new SelectItem(f, f.getNombre()+" "+f.getApellidoPaterno()+" "+f.getApellidoMaterno());
        }
        return listaItems;
    }

    public SelectItem[] getItemsAvailableSelectOneNombreCompleto() {
        List<FuncionarioDisico> entities = ejbFacade.findAll();
        SelectItem[] listaItems = new SelectItem[entities.size()];
        int i = 0;
        listaItems[i++] = new SelectItem("","---");
        for (FuncionarioDisico f : entities) {
            listaItems[i++] = new SelectItem(f, f.getNombre()+" "+f.getApellidoPaterno()+" "+f.getApellidoMaterno());
        }
        return listaItems;
    }
    
    @FacesConverter(forClass = FuncionarioDisico.class)
    public static class FuncionarioDisicoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FuncionarioDisicoController controller = (FuncionarioDisicoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "funcionarioDisicoController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FuncionarioDisico) {
                FuncionarioDisico o = (FuncionarioDisico) object;
                return getStringKey(o.getRut());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FuncionarioDisicoController.class.getName());
            }
        }
    }
}
