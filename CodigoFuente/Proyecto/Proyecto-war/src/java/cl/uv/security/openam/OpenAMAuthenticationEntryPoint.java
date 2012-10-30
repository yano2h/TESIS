/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 *
 * @author Alejandro
 */
public class OpenAMAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private String openAMLogin = null;

    public String getOpenAMLogin() {
        return openAMLogin;
    }

    public void setOpenAMLogin(String openAMLogin) {
        this.openAMLogin = openAMLogin;
    }
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("commence");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String redirectUrl = buildRedirectUrlToLoginPage(httpRequest);
        httpResponse.sendRedirect(httpResponse.encodeRedirectURL(redirectUrl));
        System.out.println("End Commence redirect to:"+redirectUrl);
    }

    protected String buildRedirectUrlToLoginPage(HttpServletRequest request) {
        String redirectToUrl;
        try {
            redirectToUrl = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            redirectToUrl = request.getRequestURL().toString();
        }
        
        redirectToUrl = openAMLogin+"?goto=" + redirectToUrl;
        return redirectToUrl;
    }
}
