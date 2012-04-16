package cl.uv.ViewController.base.jsf.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class MbMessageDialog {

    private boolean dialogMessage = false;
    private String titulo  = "";
    private String mensaje = "";

    public void onClickDialog(ActionEvent actionEvent) {
       
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

    public boolean isDialogMessage() {
        return dialogMessage;
    }

    public void setDialogMessage(boolean dialogMessage) {
        this.dialogMessage = dialogMessage;
    }
}
