package cl.uv.ViewController.base.jsf.mb;

import cl.uv.ViewController.base.utils.JsfUtils;
import cl.uv.ViewController.base.utils.Resources;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class MbInfo {

    public static final int INFORMATION_TYPE_MESSAGE = 1;
    public static final int WARNING_TYPE_MESSAGE = 2;
    public static final int ERROR_TYPE_MESSAGE = 3;
    
    private int typeExeption;
    private String message;
    private String source;  //origen;
    private String exception;
    private String basename;

    private boolean renderedPanelInfo = false;

    public void createMessage(int type, String msg, String goBack, String ex) {
        switch (type) {
            case INFORMATION_TYPE_MESSAGE:
                basename = Resources.getPropertiesPath("informationMessages");
                renderedPanelInfo = false;
                break;
            case WARNING_TYPE_MESSAGE:
                basename = Resources.getPropertiesPath("warningMessages");
                renderedPanelInfo    = true;
                break;
            case ERROR_TYPE_MESSAGE:
                basename = Resources.getPropertiesPath("errorMessages");
                renderedPanelInfo    = true;
                break;
            default:
        }
        
        typeExeption = type;
        message   = msg;
        exception = ex;
        source    = goBack.isEmpty() ? "MainPage" : goBack;
        
        redirectTo("InfoPage");
    }

    protected void redirectTo(String page) {
        JsfUtils.handleNavigation(page);
        JsfUtils.getFacesContext().renderResponse();
        JsfUtils.getFacesContext().responseComplete();
    }

    public void onClickVolver(ActionEvent ae) {
        if (source.equals("goLogin")) {
            JsfUtils.logout();
            JsfUtils.redirect(Resources.getValue("basicWebParam_path", "URI_REDIRECT_LOGOUT"));
        } else {
            redirectTo(source);
        }
    }

    public void onClickViewPanelInfo(ActionEvent ae) {
        this.renderedPanelInfo = !this.renderedPanelInfo;
    }

    public boolean isRenderedPanelInfo() {
        return renderedPanelInfo;
    }
    
    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }
  
    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getTypeExeption() {
        return typeExeption;
    }

    public void setTypeExeption(int typeExeption) {
        this.typeExeption = typeExeption;
    }    
}