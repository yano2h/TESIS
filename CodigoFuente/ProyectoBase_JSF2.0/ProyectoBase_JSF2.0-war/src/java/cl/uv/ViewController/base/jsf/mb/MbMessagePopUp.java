package cl.uv.ViewController.base.jsf.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class MbMessagePopUp {

    private boolean popupMessage = false;
    private String titulo = "";
    private String mensaje = "";

    public void onClickHidePanelPopUp(ActionEvent actionEvent) {
        popupMessage = !popupMessage;
    }

    public boolean isPopupMessage() {
        return popupMessage;
    }

    public void setPopupMessage(boolean popupMessage) {
        this.popupMessage = popupMessage;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
