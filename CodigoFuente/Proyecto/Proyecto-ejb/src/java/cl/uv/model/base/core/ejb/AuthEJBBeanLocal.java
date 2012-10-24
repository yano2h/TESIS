package cl.uv.model.base.core.ejb;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import javax.ejb.Local;

@Local
public interface AuthEJBBeanLocal {

    public boolean validateToken(String tokenCookie);

    public AtributosFuncionario getAtributosFuncionarios(String tokenCookie);
    public AtributosFuncionario readFuncionarios(Integer rut);
    
    public void logout(String tokenCookie);

    public void addUser(AtributosFuncionario atributosFuncionario);
}
