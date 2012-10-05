/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.view.controller.base.utils.Resources;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Alejandro
 */
public class OpenAMUtil {

    public static String getToken(String tokenName, HttpServletRequest request) {

        String valueCookie = null;

        if (request.getHeader("user-agent").contains("MSIE")) {
            valueCookie = request.getParameter(tokenName);
        } else {
            Cookie cookies[] = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(tokenName)) {
                        valueCookie = cookie.getValue();
                    }
                }
            }
        }

        return valueCookie;
    }

    public static List<String> parseRoles(List<String> roles) {
        List<String> rolesParseados = new ArrayList<String>();
        String prefixApp = Resources.getValue("security", "prefix_app");
        String prefixRol = Resources.getValue("security", "prefix_rol_spring");
        
        for (String rol : roles) {
            System.out.println("ROLES:"+rol);
            rol = rol.split(",")[0].split("=")[1];
            if(rol.startsWith(prefixApp)){
                rol = rol.replaceFirst(prefixApp, prefixRol);
                rolesParseados.add(rol);
            }
        }
        
        return rolesParseados;
    }
    
    public static String convertRolToFormatLDAP(String rol){
        rol = (rol.startsWith(Resources.getValue("security", "prefix_rol_spring")))? 
               rol.replaceFirst(Resources.getValue("security", "prefix_rol_spring"), Resources.getValue("security", "prefix_app")) : 
               Resources.getValue("security", "prefix_app")+rol;
        return rol;
    }
    
    public static AtributosFuncionario createFalseUser() {
        AtributosFuncionario attr = new AtributosFuncionario();
        //attr.setUid("16775578");
        attr.setUid("18000000");
        attr.setGivenname("Alejandro"); //Primer Nombre
        attr.setSn("Alvarez Ahumada"); //Apellido
        attr.setCn(attr.getGivenname()+" "+attr.getSn()); //Givename + Sn
        attr.setCorreouv("alejandro.alvareza@alumnos.uv.cl");
        attr.setMail("yano2h@gmail.com");
        attr.setRut("16775578-K");

        String prefix_app = Resources.getValue("security", "prefix_app");
        prefix_app = "role="+prefix_app;
        String postfix  = ",cn=aaaa,sn=bbbb";
        
        List<String> roles = new ArrayList<String>();
        //roles.add(prefix_app+"ADMIN"+postfix);//
        roles.add(prefix_app+"JEFE_AREA"+postfix);
        //roles.add(prefix_app+"JEFE_DEPTO"+postfix);
        roles.add(prefix_app+"FUNC_DISICO"+postfix);
        roles.add(prefix_app+"SOLICITANTE"+postfix);
        attr.setListaRoles(roles);
        
        return attr;
    }
}
