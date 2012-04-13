package cl.uv.ViewController.base.jsf.core;

import cl.uv.ViewController.base.jsf.mb.MbInfo;
import cl.uv.ViewController.base.jsf.mb.MbSSOUtils;
import cl.uv.ViewController.base.utils.JsfUtils;
import cl.uv.ViewController.base.utils.Resources;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import javax.servlet.http.HttpServletResponse;

public class CachePhaseListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        if (Boolean.valueOf(Resources.getValue("project_info", "projectLDAP"))) {
            FacesContext facesContext = phaseEvent.getFacesContext();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            String requestServerPath = facesContext.getExternalContext().getRequestServletPath();

            if (requestServerPath != null) {
                if (requestServerPath.equals("/mainPage.xhtml") || requestServerPath.equals("")) {
                    MbSSOUtils mbSSOUtils = (MbSSOUtils) JsfUtils.getValue("mbSSOUtils}");
                    if (!mbSSOUtils.doValidateUser()) {
                        MbInfo mbInfo = (MbInfo) JsfUtils.getValue("mbInfo");
                        mbInfo.createMessage(MbInfo.ERROR_TYPE_MESSAGE, "La sesión ha expirado.", "goLogin", "");
                    }
                }
            }

            try {
                response.setHeader("Cache-Control", "no-store");
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
