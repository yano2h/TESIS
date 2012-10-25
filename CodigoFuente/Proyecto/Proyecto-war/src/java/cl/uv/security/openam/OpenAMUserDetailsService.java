/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
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
    
    private FuncionarioFacadeLocal funcionarioFacade = lookupFuncionarioFacadeLocal();
    private AuthEJBBeanLocal authEJBBean = lookupAuthEJBBeanLocal();
    
    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        String tokenOpenAM = (String) token.getCredentials();
        String emailOpenId = (String) token.getPrincipal();
        System.out.println("loadUserDetails - token:"+tokenOpenAM+" - email :"+emailOpenId);
        AtributosFuncionario funcionario = null;
        
        if (!tokenOpenAM.equals("N/A") && !tokenOpenAM.equals("TEST")) {
            System.out.println("Login con token");
            funcionario = authEJBBean.getAtributosFuncionarios(tokenOpenAM);
        }else if (!emailOpenId.equals("N/A")){
            System.out.println("Login con mail");
            Integer rut = funcionarioFacade.buscarRutPorEmail(emailOpenId);
            System.out.println("RUT:"+rut);
            funcionario = authEJBBean.readFuncionarios(rut);
            System.out.println("Funcionario:"+funcionario.getCorreouv());
        }else if (tokenOpenAM.equals("TEST") ){
            System.out.println("Login con usuario default");
            funcionario = OpenAMUtil.createFalseUser();
        }else{
            return null;
        }

        OpenAMUserDetails user = createUser(funcionario, tokenOpenAM);
        user.setFuncionario(funcionario);
        System.out.println("User:"+user.getUsername());
        return user;
    }

    private OpenAMUserDetails createUser(AtributosFuncionario attr, String token) {        
        if(attr != null){
            return new OpenAMUserDetails(attr.getUid(), token,
                                         true, true, true, true,
                                         createGrantedAuthority(attr.getListaRoles()));
        }else{
           return new OpenAMUserDetails("", token);
        }
        
    }

    private Set<GrantedAuthority> createGrantedAuthority(List<String> roles) {
        Set<GrantedAuthority> authoritys = new HashSet<GrantedAuthority>();
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

    private FuncionarioFacadeLocal lookupFuncionarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FuncionarioFacadeLocal) c.lookup("java:global/Proyecto/Proyecto-ejb/FuncionarioFacade!cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
