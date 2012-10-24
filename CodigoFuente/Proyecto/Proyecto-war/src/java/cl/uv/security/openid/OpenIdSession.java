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
    }
    
    public boolean isUserAuthenticated(){
        return (id != null && !id.isEmpty() && email != null && !email.isEmpty());
    }
}
