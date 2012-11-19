/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openid;

/**
 *
 * @author Alejandro
 */
public class OpenIdSession {
    private String id;
    private String email;
    private boolean userAuthenticatedWithSSO;

    public OpenIdSession() {
        this.userAuthenticatedWithSSO = false;
    }

    public boolean isUserAuthenticatedWithSSO() {
        return userAuthenticatedWithSSO;
    }

    public void setUserAuthenticatedWithSSO(boolean userAuthenticatedWithSSO) {
        this.userAuthenticatedWithSSO = userAuthenticatedWithSSO;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void closeSession(){
        id = null;
        email = null;
        userAuthenticatedWithSSO = false;
    }
    
    
    public boolean isUserAuthenticatedWithOpenId(){
        return (id != null && !id.isEmpty() && email != null && !email.isEmpty());
    }
    
    public boolean isUserAuthenticated(){
        return (id != null && !id.isEmpty() && email != null && !email.isEmpty()) || userAuthenticatedWithSSO;
    }
}
