package cl.uv.view.controller.proyecto.jsf.core;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.proyecto.jsf.mb.MbSSOUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        FacesContext facesContext = phaseEvent.getFacesContext();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        String requestServerPath = facesContext.getExternalContext().getRequestServletPath();

        if (requestServerPath != null) {
                MbSSOUtils mbSSOUtils = (MbSSOUtils) JsfUtils.getValue("mbSSOUtils");
                
                if (mbSSOUtils.doValidateUser()) {
                    System.out.println("VALIDADO");
                    AtributosFuncionario f = mbSSOUtils.getAtributosFuncionarios();
                    System.out.println("Cn:"+f.getCn());
                    System.out.println("CorreoUv:"+f.getCorreouv());
                    System.out.println("Givnname:"+f.getGivenname());
                    System.out.println("Mail:"+f.getMail());
                    System.out.println("Rut:"+f.getRut());
                    System.out.println("Sn:"+f.getSn());
                    System.out.println("Size Roles:"+f.getListaRoles().size());
                    for (String rol : f.getListaRoles()) {
                        System.out.println("Rol:"+rol);
                    }
                    
                }else{
                    System.out.println("NO VALIDADO");
                  //  JsfUtils.redirect("http://test.uv.cl:8080/Login-war/mainPage.jspx");
                }
        }

        try {
            response.setHeader("Cache-Control", "no-store");
        } catch (NullPointerException npe) {
            Logger.getLogger(CachePhaseListener.class.getName()).log(Level.SEVERE, null, npe);
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
