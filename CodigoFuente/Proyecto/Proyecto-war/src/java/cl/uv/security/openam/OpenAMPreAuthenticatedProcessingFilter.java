/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.view.controller.base.utils.Resources;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 *
 * @author Alejandro
 */
public class OpenAMPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter{
    
    private String cookieNameToken = null;
    
    public String getCookieNameToken() {
        return cookieNameToken;
    }

    public void setCookieNameToken(String cookieNameToken) {
        this.cookieNameToken = cookieNameToken;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return "N/A";
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return (Resources.getValue("security", "enviroment").equals("testMacBook"))?"N/A":OpenAMUtil.getToken(cookieNameToken, request);
    }
    
}
