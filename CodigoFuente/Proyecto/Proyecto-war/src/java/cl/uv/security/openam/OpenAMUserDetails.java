/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Alejandro
 */
public class OpenAMUserDetails implements UserDetails{

    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<GrantedAuthority> authorities;
    
    /*Para uso de la aplicacion*/
    private AtributosFuncionario funcionario;

    public OpenAMUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
        this.credentialsNonExpired = false;
    }
    
    
    public OpenAMUserDetails(String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }
    
    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    public AtributosFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(AtributosFuncionario funcionario) {
        this.funcionario = funcionario;
    }
    
    public Boolean isUserInRole(String rol){
        return authorities.contains(new SimpleGrantedAuthority(rol));
    }
}
