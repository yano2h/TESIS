/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.jsf.mb;


import cl.uv.proyecto.persistencia.ejb.UnidadSolicitanteFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.UnidadSolicitante;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alejandro
 */
@ManagedBean(name = "unidadSolicitanteController")
@SessionScoped
public class UnidadSolicitanteController implements Serializable {

    @EJB
    private UnidadSolicitanteFacadeLocal ejbFacade;
    
    public UnidadSolicitanteController() {
    }
    
    private UnidadSolicitanteFacadeLocal getFacade() {
        return ejbFacade;
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtils.getSelectItems(ejbFacade.findAll(), "getNombreUnidadSolicitante",false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtils.getSelectItems(ejbFacade.findAll(), "getNombreUnidadSolicitante",true);
    }
    
    public SelectItem[] getFilterOptions() {
        return JsfUtils.getFilterOptions(ejbFacade.findAll(), "getNombreUnidadSolicitante");
    }
    
    @FacesConverter(forClass = UnidadSolicitante.class)
    public static class UnidadSolicitanteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UnidadSolicitanteController controller = (UnidadSolicitanteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "unidadSolicitanteController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UnidadSolicitante) {
                UnidadSolicitante o = (UnidadSolicitante) object;
                return getStringKey(o.getIdUnidadSolicitante());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UnidadSolicitanteController.class.getName());
            }
        }
    }
}
