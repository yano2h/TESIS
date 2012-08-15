/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Alejandro
 */
public class OpenAMUtil {

    public static String getToken(String tokenName, HttpServletRequest request) {
        
        String valueCookie = null;

        if ( request.getHeader("user-agent").contains("MSIE") ) {
            valueCookie = request.getParameter(tokenName);
        } else {
            Cookie cookies[] = request.getCookies();

            if(cookies != null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(tokenName)) {
                            valueCookie = cookie.getValue();
                    }
                }
            }
        }
        
        return valueCookie;
    }
   
}
