package cl.uv.ViewController.base.jsf.mb;

import cl.uv.ViewController.base.utils.JsfUtils;

public class MbLeftMenuController {

    private MbSessionController sc;
    private MbMessagePopUp popup;

    public MbLeftMenuController() {
        sc = (MbSessionController) JsfUtils.getValue("#{MbSessionController}");
        popup = (MbMessagePopUp) JsfUtils.getValue("#{MbMessagePopUp}");
    }

    private void goPopUp(String message) {
        popup.setMensaje(message);
        popup.setPopupMessage(true);
    }

    public String goHome() {
        sc.navigate("inicio");
        return null;
    }

}
