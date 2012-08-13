/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.view.controller.base.utils.JsfUtils;
import com.iplanet.dpro.session.Session;
import com.iplanet.dpro.session.SessionException;
import com.iplanet.dpro.session.SessionID;
import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

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
