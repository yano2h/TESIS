package cl.uv.ViewController.base.jsf.mb;

import cl.uv.ViewController.base.utils.Resources;
import cl.uv.ViewController.base.utils.JsfUtils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name="MbInfo")
@SessionScoped
public class MbInfo {

    public static final int INFORMATION_TYPE_MESSAGE = 1;
    public static final int WARNING_TYPE_MESSAGE = 2;
    public static final int ERROR_TYPE_MESSAGE = 3;
    private int tipoExepcion;
    private String imagen;
    private String mensaje;
    private String head;
    private String titleIntructions;
    private String titleExceptionDetail;
    private String origen;
    private String exception;
    private String contacto;
    private boolean renderedPanelInfo = false;

    public void createMessage(int tipo, String msg, String volver, String ex) {
        switch (tipo) {
            case INFORMATION_TYPE_MESSAGE:
                imagen = Resources.getValue("basicWebParam_path", "informationMessageImage");
                head = Resources.getValue("basicWebParam_path", "informationMessageHead");
                renderedPanelInfo = false;
                break;
            case WARNING_TYPE_MESSAGE:
                imagen = Resources.getValue("basicWebParam_path", "warningMessageImage");
                head = Resources.getValue("basicWebParam_path", "warningMessageHead");
                titleIntructions = Resources.getValue("basicWebParam_path", "titleIntructions");
                titleExceptionDetail = Resources.getValue("basicWebParam_path", "titleExceptionDetail");
                contacto = Resources.getValue("basicWebParam_path", "contactoWarning");
                renderedPanelInfo = true;
                break;
            case ERROR_TYPE_MESSAGE:
                imagen = Resources.getValue("basicWebParam_path", "errorMesageImage");
                head = Resources.getValue("basicWebParam_path", "errorMessageHead");
                titleIntructions = Resources.getValue("basicWebParam_path", "titleIntructions");
                titleExceptionDetail = Resources.getValue("basicWebParam_path", "titleExceptionDetail");
                contacto = Resources.getValue("basicWebParam_path", "contactoError");
                renderedPanelInfo = true;
                break;
            default:
        }
        
        tipoExepcion = tipo;
        origen = volver.isEmpty() ? "goMainPage" : volver;
        mensaje = msg;
        exception = ex;
        redirectTo("goInfoPage");
        return;
    }

    protected void redirectTo(String page) {
        JsfUtils.handleNavigation(page);
        JsfUtils.getFacesContext().renderResponse();
        JsfUtils.getFacesContext().responseComplete();
    }

    public void onClickVolver(ActionEvent ae) {
        if (origen.equals("goLogin")) {
            JsfUtils.logout();
            JsfUtils.redirect(Resources.getValue("basicWebParam_path", "URI_REDIRECT_LOGOUT"));
        } else {
            redirectTo(origen);
        }
    }

    public void onClickViewPanelInfo(ActionEvent ae) {
        this.renderedPanelInfo = !this.renderedPanelInfo;
    }

    public int getTipoExepcion() {
        return tipoExepcion;
    }

    public int getTipoExepcionInfo() {
        return INFORMATION_TYPE_MESSAGE;
    }

    public int getTipoExepcionWarning() {
        return WARNING_TYPE_MESSAGE;
    }

    public int getTipoExepcionError() {
        return ERROR_TYPE_MESSAGE;
    }

    public String getTitleIntructions() {
        return titleIntructions;
    }

    public String getTitleExceptionDetail() {
        return titleExceptionDetail;
    }

    public String getOrigen() {
        return origen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getHead() {
        return head;
    }

    public String getImagen() {
        return imagen;
    }

    public String getException() {
        return exception;
    }

    public String getContacto() {
        return contacto;
    }

    public boolean isRenderedPanelInfo() {
        return renderedPanelInfo;
    }
}
