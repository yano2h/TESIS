/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.view.controller.base.authentication.MbSSO;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.Flash;
        
public class MbBase implements Serializable{
    
    @ManagedProperty("#{flash}")
    private Flash flash;
    
    @ManagedProperty("#{mbSSO}")
    private MbSSO mbSSO;
    
    public MbBase(){}

    public Flash getFlash() {
        return flash;
    }

    public void setFlash(Flash flash) {
        this.flash = flash;
    }

    public MbSSO getMbSSO() {
        return mbSSO;
    }

    public void setMbSSO(MbSSO mbSSO) {
        this.mbSSO = mbSSO;
    }
    
    public Object getValueOfFlashContext(String key){
        Object v = getFlash().get(key);
        getFlash().keep(key);
        return v;
    }
            
    public Object putValueOnFlashContext(String key, Object value){
        return getFlash().put(key, value);
    }
    
    public FuncionarioDisico getFuncionarioDisico(){
        return mbSSO.getFuncionarioDisico();
    }
    
    public boolean isUserInRole(String rol){
        return JsfUtils.getExternalContext().isUserInRole(rol);
    }
}
