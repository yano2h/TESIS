/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.NotificacionFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Funcionario;
import cl.uv.proyecto.persistencia.entidades.Notificacion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
public class MbUserInfo implements Serializable {

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;
    @EJB
    private NotificacionFacadeLocal notificacionFacade;
    private Funcionario funcionario;

    public MbUserInfo() {
    }

    @PostConstruct
    public void init() {
        funcionario = funcionarioFacade.find(new Integer(16775578));
        if(funcionario.getFechaPrimerAcceso()==null){
            funcionario.setFechaPrimerAcceso(new Date());
        }
        funcionario.setFechaUltimoAcceso(new Date());
        funcionarioFacade.edit(funcionario);
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public List<Notificacion> getNotificaciones() {
        funcionario.setNotificaciones(notificacionFacade.buscarNotificacionPorDestinatario(funcionario));
        return funcionario.getNotificaciones();
    }

    public void marcarComoRevisada(Notificacion notificacion) {
        if (notificacion != null && !notificacion.getRevisada()) {
            notificacion.setRevisada(true);
            notificacionFacade.edit(notificacion);
        }
    }
}
