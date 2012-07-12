/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacade;
import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@SessionScoped
public class MbFuncionarioInfo implements Serializable {

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
            
    private FuncionarioDisico funcionario;
    
    public MbFuncionarioInfo() {
    }
    
    @PostConstruct
    private void init(){
         funcionario = funcionarioDisicoFacade.find(new Integer(18000000));
        if(funcionario.getFechaPrimerAcceso()==null){
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
    
    
    
}
