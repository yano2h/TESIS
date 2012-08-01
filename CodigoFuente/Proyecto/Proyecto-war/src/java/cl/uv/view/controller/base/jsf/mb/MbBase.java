/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import java.io.Serializable;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.Flash;
        
public class MbBase implements Serializable{
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    @ManagedProperty("#{flash}")
    private Flash flash;
    
    public MbBase(){}
    
    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public MbFuncionarioInfo getMbFuncionarioInfo() {
        return mbFuncionarioInfo;
    }

    public Flash getFlash() {
        return flash;
    }

    public void setFlash(Flash flash) {
        this.flash = flash;
    }
    
    public Object getValueOfFlashContext(String key){
        Object v = getFlash().get(key);
        getFlash().keep(key);
        return v;
    }
            
    public Object putValueOnFlashContext(String key, Object value){
        return getFlash().put(key, value);
    }
    
    public FuncionarioDisico getFuncionario(){
        return mbFuncionarioInfo.getFuncionario();
    }
}
