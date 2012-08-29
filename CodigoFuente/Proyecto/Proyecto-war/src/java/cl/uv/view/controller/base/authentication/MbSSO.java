/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.authentication;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.security.openam.OpenAMUserDetails;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@SessionScoped
public class MbSSO {
    
    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;
    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    
    private OpenAMUserDetails user;
    private Funcionario funcionario;
    private FuncionarioDisico funcionarioDisico;
    
    public MbSSO() {
    }
    
    @PostConstruct
    private void init(){
        user = (OpenAMUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer rut = Integer.parseInt( user.getUsername() );
        System.out.println("RUT SSO:"+rut);
        funcionario = funcionarioFacade.find(rut);
        funcionarioDisico = funcionarioDisicoFacade.find(rut);
        if (funcionarioDisico == null) {
            System.out.println("Funcionario:"+rut+" not found");
        }
        if(funcionario == null){
           funcionario = saveUser(user);
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
     
    
    public void redirectHomePage(){
        if (JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_JAREA")) 
            || JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_JDEPTO")) 
            || JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_FDISICO")) 
            || JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_ADM")) ) {
            
            JsfUtils.performNavigation( Resources.getValue("pages", "home_page_funcionario"), true);
            
        }else if(JsfUtils.getExternalContext().isUserInRole(Resources.getValue("security", "R_SOLICITANTE"))){
            JsfUtils.performNavigation( Resources.getValue("pages", "home_page_solicitante"), true);
        }   
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public FuncionarioDisico getFuncionarioDisico() {
        return funcionarioDisico;
    }

    public OpenAMUserDetails getUser() {
        return user;
    }
    
    
}
