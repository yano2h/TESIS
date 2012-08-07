/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import java.util.List;
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
        AtributosFuncionario attr = authEJBBean.getAtributosFuncionarios(tokenOpenAM);
        OpenAMUserDetails user = new OpenAMUserDetails(attr.getUid(), tokenOpenAM);
        user
        
    }

    private OpenAMUserDetails createUser(AtributosFuncionario attr){
        return new OpenAMUserDetails(attr.getUid(), null, true, true, true, true, createGrantedAuthority(attr.getListaRoles()));
    }
    
    private GrantedAuthority[] createGrantedAuthority(List<String> roles){
        GrantedAuthority[] authoritys = new GrantedAuthority[roles.size()];
        int i=0;
        for (String rol : roles) {
            authoritys[i++] = new SimpleGrantedAuthority(rol);
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
