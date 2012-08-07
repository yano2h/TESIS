/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openam;

import com.iplanet.sso.SSOToken;
import java.util.Arrays;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
/**
 *
 * @author Alejandro
 */
public class OpenAMAuthenticationToken extends AbstractAuthenticationToken{

    private SSOToken token;
    
    public OpenAMAuthenticationToken(GrantedAuthority ga[]) {
        super(Arrays.asList(ga));
    }
    
    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
