/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.EtapaProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.EtapaProyecto;
import cl.uv.proyecto.persistencia.jsf.mb.util.PaginationHelper;
import cl.uv.view.controller.base.utils.JsfUtils;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alejandro
 */
@ManagedBean(name = "etapaProyectoController")
@SessionScoped
public class EtapaProyectoController {

    private EtapaProyecto current;
    private DataModel items = null;
    @EJB
    private EtapaProyectoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    public EtapaProyectoController() {
    }
    
    public EtapaProyecto getSelected() {
        if (current == null) {
            current = new EtapaProyecto();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    private EtapaProyectoFacadeLocal getFacade(){
        return ejbFacade;
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtils.getSelectItems(ejbFacade.findAll(),"getNombreEtapaProyecto",false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtils.getSelectItems(ejbFacade.findAll(),"getNombreEtapaProyecto",true);
    }
    
    public SelectItem[] getFilterOptions() {
        return JsfUtils.getFilterOptions(ejbFacade.findAll(),"getNombreEtapaProyecto");
    }
    
    @FacesConverter(forClass = EtapaProyecto.class)
    public static class EtapaProyectoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EtapaProyectoController controller = (EtapaProyectoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "etapaProyectoController");
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

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EtapaProyecto) {
                EtapaProyecto o = (EtapaProyecto) object;
                return getStringKey(o.getIdEtapaProyecto());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EtapaProyectoController.class.getName());
            }
        }
    }
}
