/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.model.base.core.beans.AtributosFuncionario;
import cl.uv.model.base.core.ejb.AuthEJBBeanLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.security.openam.OpenAMUserDetails;
import cl.uv.security.openam.OpenAMUtil;
import cl.uv.view.controller.base.authentication.MbSSO;
import cl.uv.view.controller.base.utils.Resources;
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
    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    @ManagedProperty(value = "#{mbSSO.funcionarioDisico}")
    private FuncionarioDisico funcionario;

    public MbFuncionarioInfo() {
    }

    @PostConstruct
    private void init() {
        if (funcionario.getFechaPrimerAcceso() == null) {
            funcionario.setFechaPrimerAcceso(new Date());
        }
        funcionario.setFechaUltimoAcceso(new Date());
        funcionarioDisicoFacade.edit(funcionario);
    }

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
