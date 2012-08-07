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

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String redirectUrl = buildRedirectUrlToLoginPage(httpRequest);
        httpResponse.sendRedirect(httpResponse.encodeRedirectURL(redirectUrl));
    }

    protected String buildRedirectUrlToLoginPage(HttpServletRequest request) {

        String redirectToUrl;
        try {
            redirectToUrl = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            redirectToUrl = request.getRequestURL().toString();
        }
        
        String redirectUrl = "http://test.uv.cl:8080/Login-war/mainPage.jspx?goto=" + redirectToUrl;
        return redirectUrl;
    }
}
