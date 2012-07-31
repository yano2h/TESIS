/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import java.io.Serializable;
import javax.faces.bean.ManagedProperty;

public class MbBase implements Serializable{
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    public MbBase(){}
    
    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public MbFuncionarioInfo getMbFuncionarioInfo() {
        return mbFuncionarioInfo;
    }
    
    public FuncionarioDisico getFuncionario(){
        return mbFuncionarioInfo.getFuncionario();
    }
}
