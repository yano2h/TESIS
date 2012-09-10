/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.security.openam.OpenAMUserDetails;
import cl.uv.security.openam.OpenAMUtil;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@SessionScoped
public class MbFuncionarioInfo implements Serializable {

    @EJB
    private AuthEJBBeanLocal authEjb;

    @ManagedProperty(value = "#{mbUser.funcionarioDisico}")
    private FuncionarioDisico funcionario;


    public FuncionarioDisico getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioDisico funcionario) {
        this.funcionario = funcionario;
    }

    public void crearUsuario() {
        AtributosFuncionario a = ((OpenAMUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFuncionario();
        a.getListaRoles().add( OpenAMUtil.convertRolToFormatLDAP( Resources.getValue("security", "R_JAREA")));
        authEjb.addUser(a);
    }
}
