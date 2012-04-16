package cl.uv.ViewController.base.jsf.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MbLeftMenuController {

    @ManagedProperty(value="#{mbSessionController}")
    private MbSessionController sc;
    
    @ManagedProperty(value="#{mbMessageDialog}")
    private MbMessageDialog dialog;

    public MbLeftMenuController() {
    }

    private void goPopUp(String message) {
        dialog.setMensaje(message);
        dialog.setDialogMessage(true);
    }

    public String goHome() {
        sc.navigate("inicio");
        return null;
    }

    public void setDialog(MbMessageDialog dialog) {
        this.dialog = dialog;
    }
    
     public void setSc(MbSessionController sc) {
        this.sc = sc;
    }
}
