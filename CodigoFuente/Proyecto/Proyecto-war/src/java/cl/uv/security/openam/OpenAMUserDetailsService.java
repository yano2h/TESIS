/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import cl.uv.view.controller.base.utils.Resources;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Alejandro
 */
public class OpenAMUserDetailsService implements AuthenticationUserDetailsService<Authentication> {

    private AuthEJBBeanLocal authEJBBean = lookupAuthEJBBeanLocal();

    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        String tokenOpenAM = (String) token.getCredentials();
        AtributosFuncionario attr = (tokenOpenAM.equals("N/A")) ? OpenAMUtil.createFalseUser() : authEJBBean.getAtributosFuncionarios(tokenOpenAM);
        OpenAMUserDetails user = createUser(attr, tokenOpenAM);
        user.setFuncionario(attr);
        return user;
    }

    private OpenAMUserDetails createUser(AtributosFuncionario attr, String token) {
        return new OpenAMUserDetails(attr.getUid(), token,
                true, true, true, true,
                createGrantedAuthority(attr.getListaRoles()));
    }

    private Set<GrantedAuthority> createGrantedAuthority(List<String> roles) {
        Set<GrantedAuthority> authoritys = new HashSet<GrantedAuthority>();

        /*
         * String prefixApp = Resources.getValue("security", "prefix_app");
         * String prefixRol = Resources.getValue("security",
         * "prefix_rol_spring");
         *
         * for (String rol : roles) { String tempRol =
         * rol.split(",")[0].split("=")[1]; if(tempRol.startsWith(prefixApp)){
         * tempRol = tempRol.replaceFirst(prefixApp, prefixRol); authoritys.add(
         * new SimpleGrantedAuthority(tempRol) ); } }
         */

        for (String rol : OpenAMUtil.parseRoles(roles)) {
            authoritys.add(new SimpleGrantedAuthority(rol));
            System.out.println("ROL="+rol);
        }
        return authoritys;
    }

    private AuthEJBBeanLocal lookupAuthEJBBeanLocal() {
        try {
            Context c = new InitialContext();
            return (AuthEJBBeanLocal) c.lookup("java:global/Proyecto/Proyecto-ejb/AuthEJBBean!cl.uv.model.base.core.ejb.AuthEJBBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
