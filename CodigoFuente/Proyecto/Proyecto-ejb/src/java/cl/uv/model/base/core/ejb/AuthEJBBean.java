package cl.uv.model.base.core.ejb;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.ws.funcionarios.AccessDenied_Exception;
import cl.uv.model.base.ws.funcionarios.Attribute;
import cl.uv.model.base.ws.funcionarios.GeneralFailure_Exception;
import cl.uv.model.base.ws.funcionarios.IdentityServicesImpl;
import cl.uv.model.base.ws.funcionarios.IdentityServicesImplService;
import cl.uv.model.base.ws.funcionarios.InvalidToken_Exception;
import cl.uv.model.base.ws.funcionarios.Token;
import cl.uv.model.base.ws.funcionarios.TokenExpired_Exception;
import cl.uv.model.base.ws.funcionarios.UserDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

@Stateless
public class AuthEJBBean implements AuthEJBBeanLocal {

    private AtributosFuncionario atributosFuncionario;

    public AuthEJBBean() {
        this.atributosFuncionario = null;
    }

    @Override
    public AtributosFuncionario getAtributosFuncionarios(String tokenCookie) {
        try {
            Token token = new Token();
            token.setId(tokenCookie);
            List<String> attributeNames = null;
            UserDetails results = getPort().attributes(attributeNames, token);
            List<Attribute> attrs = results.getAttributes();
            Iterator<Attribute> items = attrs.iterator();
            ArrayList<String> listaRoles = (ArrayList<String>) results.getRoles();
            atributosFuncionario = new AtributosFuncionario();
            String Cn = "", Sn = "", Correouv = "", Mail = "", Rut = "", Givenname = "", Uid = "";
            while (items.hasNext()) {
                Attribute attr = items.next();
                if (attr.getName().equals("cn")) {
                    Cn = (attr.getValues().get(0));
                }
                if (attr.getName().equals("sn")) {
                    Sn = (attr.getValues().get(0));
                }
                if (attr.getName().equals("correouv")) {
                    Correouv = (attr.getValues().get(0));
                }
                if (attr.getName().equals("mail")) {
                    Mail = (attr.getValues().get(0));
                }
                if (attr.getName().equals("rut")) {
                    Rut = (attr.getValues().get(0));
                }
                if (attr.getName().equals("givenname")) {
                    Givenname = (attr.getValues().get(0));
                }
                if (attr.getName().equals("uid")){
                    Uid = (attr.getValues().get(0));
                }
            }
            atributosFuncionario.setUid(Uid);
            atributosFuncionario.setCn(Cn);
            atributosFuncionario.setSn(Sn);
            atributosFuncionario.setCorreouv(Correouv);
            atributosFuncionario.setMail(Mail);
            atributosFuncionario.setRut(Rut);
            atributosFuncionario.setGivenname(Givenname);
            atributosFuncionario.setListaRoles(listaRoles);
        } catch (AccessDenied_Exception ex) {
            return null;
        } catch (TokenExpired_Exception ex) {
            return null;
        } catch (GeneralFailure_Exception ex) {
            return null;
        }
        return atributosFuncionario;
    }

    @Override
    public boolean validateToken(String tokenCookie) {
        Token token = new Token();
        token.setId(tokenCookie);
        try {
            getPort().isTokenValid(token);
            return true;
        } catch (GeneralFailure_Exception ex) {
            return false;
        } catch (InvalidToken_Exception ex) {
            return false;
        } catch (TokenExpired_Exception ex) {
            return false;
        }
    }

    private static IdentityServicesImpl getPort() {
        IdentityServicesImplService service = new IdentityServicesImplService();
        IdentityServicesImpl port = service.getIdentityServicesImplPort();
        return port;
    }

    @Override
    public void logout(String tokenCookie) {
        Token token = new Token();
        token.setId(tokenCookie);
        try {
            getPort().logout(token);
        } catch (GeneralFailure_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
