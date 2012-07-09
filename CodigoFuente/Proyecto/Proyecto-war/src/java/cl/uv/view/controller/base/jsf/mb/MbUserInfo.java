/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jano
 */
@ManagedBean
@SessionScoped
public class MbUserInfo implements Serializable{
    
    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;
    private Funcionario funcionario;
    
    public MbUserInfo() {
    }
    
    @PostConstruct
    public void init(){
        funcionario = funcionarioFacade.find(new Integer(16775578));
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    
}
