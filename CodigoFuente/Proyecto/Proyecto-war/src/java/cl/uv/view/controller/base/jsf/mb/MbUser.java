/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.security.openam.OpenAMUserDetails;
import cl.uv.view.controller.base.authentication.MbSSO;
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
 * @author Jano
 */
@ManagedBean
@SessionScoped
public class MbUser implements Serializable{
    
    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;
    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    
    private Funcionario funcionario;
    private FuncionarioDisico funcionarioDisico;
    
    @ManagedProperty(value = "#{mbSSO}")
    private MbSSO mbSSO;
    
    @PostConstruct
    private void init(){
        Integer rut = Integer.parseInt( mbSSO.getUser().getUsername() );
        System.out.println("RUT SSO:"+rut);
        funcionario = funcionarioFacade.find(rut);
        funcionarioDisico = funcionarioDisicoFacade.find(rut);
        if (funcionarioDisico == null) {
            System.out.println("Funcionario:"+rut+" not found");
        }
        
        if(funcionario == null){
           funcionario = saveUser( mbSSO.getUser() );
        }
        
        funcionario.setFechaUltimoAcceso(new Date());
        funcionarioFacade.edit(funcionario);
    }
    
    private Funcionario saveUser(OpenAMUserDetails u){
        Funcionario f = new Funcionario();
        f.setRut(Integer.parseInt( u.getUsername() ));
        f.setCorreoUv( u.getFuncionario().getCorreouv() );
        f.setFechaPrimerAcceso(new Date());
        f.setNombre( u.getFuncionario().getGivenname() );
        
        String[] apellidos = u.getFuncionario().getSn().split(" ");
        String[] nombreCompleto = u.getFuncionario().getCn().split(" ");
        
        if(apellidos.length > 0 && apellidos[0].equals(nombreCompleto[0])){
            f.setApellidoPaterno(apellidos[0]);
        }else{
            f.setApellidoPaterno("");
        }
        
        if (apellidos.length > 1 && nombreCompleto.length >= 3 && apellidos[1].equals(nombreCompleto[1])) {
               f.setApellidoMaterno(apellidos[1]);          
        }else if(apellidos.length==2){
            f.setApellidoMaterno(apellidos[1]);
        }else{
            f.setApellidoMaterno("");
        }
        
        funcionarioFacade.create(f);
        return f;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public void setFuncionarioDisico(FuncionarioDisico funcionarioDisico) {
        this.funcionarioDisico = funcionarioDisico;
    }

    public void setMbSSO(MbSSO mbSSO) {
        this.mbSSO = mbSSO;
    }
    
     
}
