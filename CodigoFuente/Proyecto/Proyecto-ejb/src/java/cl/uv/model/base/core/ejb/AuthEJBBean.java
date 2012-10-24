package cl.uv.model.base.core.ejb;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.utils.Resources;
import cl.uv.model.base.ws.funcionarios.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

@Stateless
public class AuthEJBBean implements AuthEJBBeanLocal {

    private AtributosFuncionario atributosFuncionario;
    private Token tokenAdminSession;

    public AuthEJBBean() {
        this.atributosFuncionario = null;
    }

    private void authenticateAdmin() {
        System.out.println("authenticateAdmin");
        try {
            tokenAdminSession = getPort().authenticate(Resources.getValue("BasicParam", "userAdmin"),
                    Resources.getValue("BasicParam", "passwordAdmin"),
                    Resources.getValue("BasicParam", "realm"));
        } catch (GeneralFailure_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidCredentials_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPassword_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NeedMoreCredentials_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserNotFound_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tokenAdminSession:"+tokenAdminSession);
    }

    private void logoutAdmin() {
        System.out.println("logoutAdmin");
        try {
            getPort().logout(tokenAdminSession);
        } catch (GeneralFailure_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if (attr.getName().equals("uid")) {
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
            System.out.println("Exception: " + ex.getMessage());
            return null;
        } catch (TokenExpired_Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return null;
        } catch (GeneralFailure_Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return null;
        }
        return atributosFuncionario;
    }

    @Override
    public AtributosFuncionario readFuncionarios(Integer rut) {
        System.out.println("readFuncionarios");
        AtributosFuncionario funcionario = null;
        try {
            authenticateAdmin();
            IdentityDetails user = getPort().read(rut.toString(), null, tokenAdminSession);
            System.out.println("IdentityDetails:"+user);
            List<Attribute> attr = user.getAttributes();
            System.out.println("ROLES:"+user.getRoles());
            System.out.println("GRUPOS:"+user.getGroups());
            funcionario = parseAttributesToFuncionario(attr, user.getGroups());
            logoutAdmin();
        } catch (AccessDenied_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralFailure_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NeedMoreCredentials_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectNotFound_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TokenExpired_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            return funcionario;
        }
    }

    private AtributosFuncionario parseAttributesToFuncionario(List<Attribute> attr, List<String> roles) {
        AtributosFuncionario funcionario = new AtributosFuncionario();
        funcionario.setListaRoles(roles);
        for (Attribute a : attr) {
            System.out.println("Atributo:"+a.getName());
            if (a.getName().equals("cn")) {
                funcionario.setCn(a.getValues().get(0));
            } else if (a.getName().equals("sn")) {
                funcionario.setSn(a.getValues().get(0));
            } else if (a.getName().equals("correouv")) {
                funcionario.setCorreouv(a.getValues().get(0));
            } else if (a.getName().equals("mail")) {
                funcionario.setMail(a.getValues().get(0));
            } else if (a.getName().equals("rut")) {
                funcionario.setRut(a.getValues().get(0));
            } else if (a.getName().equals("givenname")) {
                funcionario.setGivenname(a.getValues().get(0));
            } else if (a.getName().equals("uid")) {
                funcionario.setUid(a.getValues().get(0));
            }
        }

        return funcionario;
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

    @Override
    public void addUser(AtributosFuncionario atributosFuncionario) {
        try {
            cl.uv.model.base.ws.funcionarios.Token tokenAdmin;
            tokenAdmin = getPort().authenticate(Resources.getValue("BasicParam", "userAdmin"),
                    Resources.getValue("BasicParam", "passwordAdmin"),
                    Resources.getValue("BasicParam", "realm"));
            cl.uv.model.base.ws.funcionarios.IdentityDetails identityDetails = new cl.uv.model.base.ws.funcionarios.IdentityDetails();
            identityDetails.setName(atributosFuncionario.getUid());

            identityDetails.getAttributes().add(createAttribute("NombreCompleto", atributosFuncionario.getCn()));
            identityDetails.getAttributes().add(createAttribute("ApellidoPaterno", atributosFuncionario.getSn()));
            identityDetails.getAttributes().add(createAttribute("FechaCadPassword", Resources.getValue("BasicParam", "ValorFecha")));
            identityDetails.getAttributes().add(createAttribute("mail", atributosFuncionario.getMail()));
            identityDetails.getAttributes().add(createAttribute("correoUV", atributosFuncionario.getCorreouv()));
            identityDetails.getAttributes().add(createAttribute("Rut", atributosFuncionario.getRut()));
            for (String str : atributosFuncionario.getListaRoles()) {
                System.out.println("ROLES:" + str);
                identityDetails.getGroups().add(str);
            }

//            if (atributosFuncionario.getPassword() == null) {
//                String pass = atributosFuncionario.getUid();
//                while (pass.length() < 8) {
//                    pass = "0" + pass;
//                }
//                identityDetails.getAttributes().add(createAttribute("Password", pass));
//            } else {
//                identityDetails.getAttributes().add(createAttribute("Password", atributosFuncionario.getPassword()));
//            }



            try {
                getPort().update(identityDetails, tokenAdmin);
            } catch (AccessDenied_Exception ex) {
                Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ObjectNotFound_Exception ex) {
                try {
                    getPort().create(identityDetails, tokenAdmin);
                } catch (DuplicateObject_Exception ex1) {
                    Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (TokenExpired_Exception ex1) {
                    Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (TokenExpired_Exception ex) {
                Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            getPort().logout(tokenAdmin);

        } catch (GeneralFailure_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidCredentials_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPassword_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NeedMoreCredentials_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserNotFound_Exception ex) {
            Logger.getLogger(AuthEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Attribute createAttribute(String nameAtributte, String value) {
        Attribute attr = new Attribute();
        attr.setName(Resources.getValue("BasicParam", nameAtributte));
        attr.getValues().add(value);
        return attr;
    }
}
