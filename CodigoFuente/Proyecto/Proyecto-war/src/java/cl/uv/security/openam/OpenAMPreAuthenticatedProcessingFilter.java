/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.security.openid.OpenIdSession;
import cl.uv.view.controller.base.utils.Resources;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 *
 * @author Alejandro
 */
public class OpenAMPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter{
    
    private String cookieNameToken = null;
    private OpenIdSession openIdSession = null;
    
    public String getCookieNameToken() {
        return cookieNameToken;
    }

    public void setCookieNameToken(String cookieNameToken) {
        this.cookieNameToken = cookieNameToken;
    }

    public OpenIdSession getOpenIdSession() {
        return openIdSession;
    }

    public void setOpenIdSession(OpenIdSession openIdSession) {
        this.openIdSession = openIdSession;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        System.out.println("getPreAuthenticatedPrincipal");
        System.out.println("openIdSession: "+openIdSession);
        if (openIdSession!=null) {
            System.out.println("Email:"+openIdSession.getEmail());
        }
        Object o = (openIdSession!=null && openIdSession.isUserAuthenticated())? openIdSession.getEmail():"N/A"; 
        System.out.println("getPreAuthenticatedPrincipal RETURN:"+o);
        return o;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        System.out.println("getPreAuthenticatedCredentials");
        Object credential = (Resources.getValue("security", "enviroment").equals("testMacBook"))?"TEST":OpenAMUtil.getToken(cookieNameToken, request);
        credential = (credential==null && openIdSession!=null && openIdSession.isUserAuthenticated())?"N/A":credential;
        System.out.println("getPreAuthenticatedCredentials RETURN:"+credential);
        return  credential;
    } 
}
