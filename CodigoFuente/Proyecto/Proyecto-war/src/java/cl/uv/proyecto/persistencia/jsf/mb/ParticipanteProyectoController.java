package cl.uv.proyecto.persistencia.jsf.mb;

import cl.uv.proyecto.persistencia.entidades.ParticipanteProyecto;
import cl.uv.proyecto.persistencia.jsf.mb.util.JsfUtil;
import cl.uv.proyecto.persistencia.jsf.mb.util.PaginationHelper;
import cl.uv.proyecto.persistencia.ejb.ParticipanteProyectoFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
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

@ManagedBean(name = "participanteProyectoController")
@SessionScoped
public class ParticipanteProyectoController implements Serializable {

    private ParticipanteProyecto current;
    private DataModel items = null;
    @EJB
    private cl.uv.proyecto.persistencia.ejb.ParticipanteProyectoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ParticipanteProyectoController() {
    }

    public ParticipanteProyecto getSelected() {
        if (current == null) {
            current = new ParticipanteProyecto();
            current.setParticipanteProyectoPK(new cl.uv.proyecto.persistencia.entidades.ParticipanteProyectoPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ParticipanteProyectoFacade getFacade() {
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
        current = (ParticipanteProyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ParticipanteProyecto();
        current.setParticipanteProyectoPK(new cl.uv.proyecto.persistencia.entidades.ParticipanteProyectoPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParticipanteProyectoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ParticipanteProyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParticipanteProyectoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ParticipanteProyecto) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParticipanteProyectoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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

    @FacesConverter(forClass = ParticipanteProyecto.class)
    public static class ParticipanteProyectoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParticipanteProyectoController controller = (ParticipanteProyectoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "participanteProyectoController");
            return controller.ejbFacade.find(getKey(value));
        }

        cl.uv.proyecto.persistencia.entidades.ParticipanteProyectoPK getKey(String value) {
            cl.uv.proyecto.persistencia.entidades.ParticipanteProyectoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cl.uv.proyecto.persistencia.entidades.ParticipanteProyectoPK();
            key.setRutParticipante(Integer.parseInt(values[0]));
            key.setIdProyecto(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(cl.uv.proyecto.persistencia.entidades.ParticipanteProyectoPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getRutParticipante());
            sb.append(SEPARATOR);
            sb.append(value.getIdProyecto());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ParticipanteProyecto) {
                ParticipanteProyecto o = (ParticipanteProyecto) object;
                return getStringKey(o.getParticipanteProyectoPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ParticipanteProyectoController.class.getName());
            }
        }
    }
}
