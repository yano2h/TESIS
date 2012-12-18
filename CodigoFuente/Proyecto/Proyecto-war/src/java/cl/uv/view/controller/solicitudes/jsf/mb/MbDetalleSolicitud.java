/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.ArchivoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ComentarioSolicitudFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.SolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.*;
import cl.uv.proyecto.persistencia.jsf.mb.FuncionarioDisicoController;
import cl.uv.proyecto.requerimientos.ejb.SolicitudRequerimientoEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbBase;
import cl.uv.view.controller.base.jsf.mb.MbFilesUpload;
import cl.uv.view.controller.base.utils.JsfUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbDetalleSolicitud extends MbBase implements Serializable {

    @EJB
    private SolicitudRequerimientoFacadeLocal solicitudFacade;
    @EJB
    private ComentarioSolicitudFacadeLocal comentarioFacade;
    @EJB
    private ArchivoSolicitudRequerimientoFacadeLocal archivosAdjuntosFacade;
    @EJB
    private SolicitudRequerimientoEJBLocal solicitudRequerimientoEJB;
    @ManagedProperty(value = "#{mbFilesUpload}")
    private MbFilesUpload mbFilesUpload;
    @ManagedProperty(value = "#{funcionarioDisicoController}")
    private FuncionarioDisicoController mbFuncionarioDisico;
    
    private String codigo;
    private SolicitudRequerimiento solicitud;
    private String comentario;
    private String respuesta;
    private String motivoTransferencia;
    private String motivoRechazo;
    private String emailsRespuestaManual;
    private String asuntoRespuestaManual;
    private Area nuevaArea;
    private List<FuncionarioDisico> funcionariosArea;

    public MbDetalleSolicitud() {
        comentario = "";
        respuesta = "";
        motivoTransferencia = "";
        motivoRechazo = "";
        emailsRespuestaManual = "";
        asuntoRespuestaManual = "";
    }

    @PostConstruct
    public void init() {
        codigo = JsfUtils.getRequestParameter("codigo");
        solicitud = solicitudFacade.buscarPorCodigo(codigo);

        if (solicitud == null) {
            JsfUtils.addErrorMessage("Error:", "La solicitud con codigo " + codigo + " no pudo ser encontrada");
        } else {
            solicitud.setComentarios(comentarioFacade.buscarComentariosPorSolicitud(solicitud.getIdSolicitudRequerimiento()));
            solicitud.setArchivosAdjuntos(archivosAdjuntosFacade.buscarArchivosPorSolicitud(solicitud));
            if (solicitud.getResponsable().equals(getFuncionarioDisico())) {
                solicitudRequerimientoEJB.dejarPendienteSolicitud(solicitud);
            }
        }
    }

    public void setMbFilesUpload(MbFilesUpload mbFilesUpload) {
        this.mbFilesUpload = mbFilesUpload;
    }

    public void setMbFuncionarioDisico(FuncionarioDisicoController mbFuncionarioDisico) {
        this.mbFuncionarioDisico = mbFuncionarioDisico;
    }

    
    public SolicitudRequerimiento getSolicitud() {
        return solicitud;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getAsuntoRespuestaManual() {
        return asuntoRespuestaManual;
    }

    public void setAsuntoRespuestaManual(String asuntoRespuestaManual) {
        this.asuntoRespuestaManual = asuntoRespuestaManual;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEmailsRespuestaManual() {
        return emailsRespuestaManual;
    }

    public void setEmailsRespuestaManual(String emailsRespuestaManual) {
        this.emailsRespuestaManual = emailsRespuestaManual;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getMotivoTransferencia() {
        return motivoTransferencia;
    }

    public void setMotivoTransferencia(String motivoTransferencia) {
        this.motivoTransferencia = motivoTransferencia;
    }

    public Area getNuevaArea() {
        return nuevaArea;
    }

    public void setNuevaArea(Area nuevaArea) {
        this.nuevaArea = nuevaArea;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getMinDate() {
        Date d = new Date();
        d = new Date(d.getTime() + (1000 * 60 * 30));
        return d;
    }

    public void comentar(ActionEvent event) {
        if (!comentario.isEmpty()) {
            solicitudRequerimientoEJB.comentarSolicitud(comentario, solicitud, getFuncionario());
        }
        comentario = "";
        solicitud.setComentarios(comentarioFacade.buscarComentariosPorSolicitud(solicitud.getIdSolicitudRequerimiento()));
    }

    public void eliminarComentario(ComentarioSolicitud c) {
        System.out.println("EliminarCOmentario - " + c.getSolicitudRequerimiento().getAsunto());
        c.setVisible(false);
        comentarioFacade.edit(c);
    }

    public void enviarRespuestaDirecta() {
        if (!respuesta.isEmpty()) {
            solicitud.setRespuesta(respuesta);
            solicitudRequerimientoEJB.enviarRespuestaDirecta(solicitud, true, mbFilesUpload.extraerArchivosAdjuntos());
        }
        respuesta = "";
        solicitud.setArchivosAdjuntos(archivosAdjuntosFacade.buscarArchivosPorSolicitud(solicitud));
    }

    public void enviarRespuestaManual() {
        if (!respuesta.isEmpty()) {
            String[] direcciones = emailsRespuestaManual.replaceAll(" ", "").split(",");
            solicitud.setRespuesta(respuesta);
            solicitudRequerimientoEJB.enviarRespuestaManual(solicitud, direcciones, asuntoRespuestaManual, mbFilesUpload.extraerArchivosAdjuntos());
        }
        solicitud.setArchivosAdjuntos(archivosAdjuntosFacade.buscarArchivosPorSolicitud(solicitud));
    }

    public void respuestaAlJefeDeArea() {
        solicitudRequerimientoEJB.enviarRespuestaJefeArea(solicitud);
    }

    public void transferirSolicitud() {
        if (!motivoTransferencia.isEmpty()) {
            solicitudRequerimientoEJB.transferirSolicitud(solicitud, nuevaArea, motivoTransferencia);
        }
        motivoTransferencia = "";
    }

    public void rechazarSolcitiud() {
        if (!motivoRechazo.isEmpty()) {
            solicitud.setRespuesta(motivoRechazo);
            solicitudRequerimientoEJB.rechazarSolicitud(solicitud);
        }
    }

    public void convertirEnProyecto() {
        solicitudRequerimientoEJB.convertirSolicitudEnProyecto(solicitud);
        JsfUtils.handleNavigation("/view/proyectos/crearProyecto?faces-redirect=true");
    }

    public void asignarSolicitud() {
        if (solicitud.getResponsable() == null) {
            JsfUtils.addErrorMessage("Error al asignar responsable solicitud", "Debe seleccionar un funcionario para poder asignar la solicitud ");
        } else {
            solicitudRequerimientoEJB.asignarSolicitud(solicitud);
            JsfUtils.addSuccessMessage("Asignacion Exitosa", "La solicitud fue asignada exitosamente a: " + solicitud.getResponsable().getNombre() + " " + solicitud.getResponsable().getApellidoPaterno() + " " + solicitud.getResponsable().getApellidoMaterno());
        }
    }
    
    public void editarSolicitud() {
        if (solicitud.getResponsable() == null) {
            JsfUtils.addErrorMessage("Error al asignar responsable solicitud", "Debe seleccionar un funcionario para poder asignar la solicitud ");
        } else {
            solicitudRequerimientoEJB.editarResponsableSolicitud(solicitud);
            JsfUtils.addSuccessMessage("Edicion Exitosa", "La solicitud se ha modificado exitosamente.");
        }
    }

    public void iniciarSolicitud() {
        solicitudRequerimientoEJB.iniciarSolicitud(solicitud);
        JsfUtils.addSuccessMessage("Operación Exitosa", "La solicitud a sido cambiada correctamente al estado Iniciada");
    }
    
    public void cargarFuncionariosArea(){
        funcionariosArea = mbFuncionarioDisico.getFuncionariosArea();
    }

    public List<FuncionarioDisico> getFuncionariosArea() {
        return funcionariosArea;
    }

    public void setFuncionariosArea(List<FuncionarioDisico> funcionariosArea) {
        this.funcionariosArea = funcionariosArea;
    }
    
    
}
