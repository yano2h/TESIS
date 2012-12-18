/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.ComentarioSolicitud;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import cl.uv.proyecto.url.utils.UrlBuilder;

/**
 *
 * @author Alejandro
 */
public enum TypeEmail {
    ENVIO_SOLICITUD(){
        @Override
        public String construirMensaje(Object o, String nameInvoker){
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(), 
                                 s.getAsunto(), 
                                 s.getSolicitante().getNombre() ,
                                 s.getCodigoConsulta(), 
                                 getLink(s));
        }
    },
    RECHAZO_SOLICITUD (){
        @Override
        public String construirMensaje(Object o, String nameInvoker){
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(), 
                                 s.getAsunto(), 
                                 s.getSolicitante().getNombre(), 
                                 s.getRespuesta(), 
                                 getLink(s));
        }
    },
    TRANSFERENCIA_SOLICITUD(){
        @Override
        public String construirMensaje(Object o, String nameInvoker){
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(), 
                                 s.getAsunto(), 
                                 s.getSolicitante().getNombre(),
                                 s.getAreaResponsable().getNombreArea(), 
                                 s.getJustificacionTrasnferencia(), 
                                 getLink(s));
        }
    },
    ASIGNACION_SOLICITUD(){
        @Override
        public String construirMensaje(Object o, String nameInvoker){
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(), 
                                 s.getAsunto(), 
                                 s.getSolicitante().getNombre(),
                                 s.getResponsable().getNombreCorto(), 
                                 s.getResponsable().getNombreCorto(),
                                 getLink(s));
        }
        
    },
    INICIO_SOLICITUD(){
        @Override
        public String construirMensaje(Object o, String nameInvoker){
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(), 
                                 s.getAsunto(), 
                                 s.getSolicitante().getNombre(),
                                 s.getResponsable().getNombreCorto(), 
                                 getLink(s));
        }
        
        @Override
        public String construirAsunto(SolicitudRequerimiento s, String nameInvoker){
            return String.format(getAsuntoSinFormato(), s.getResponsable().getNombreCorto(), s.getAsunto());
        }
    },
    CIERRE_SOLICITUD(){
        @Override
        public String construirMensaje(Object o, String nameInvoker){
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(), 
                                 s.getAsunto(), 
                                 s.getSolicitante().getNombre(),
                                 s.getRespuesta(), 
                                 getLink(s));
        }
    },
    COMENTARIO_SOLICITUD(){
        @Override
        public String construirMensaje(Object o, String nameInvoker){
            ComentarioSolicitud c = (ComentarioSolicitud) o;
            SolicitudRequerimiento s = c.getSolicitudRequerimiento();
            
            return String.format(getMensajeSinFormato(), 
                                 s.getAsunto(), 
                                 nameInvoker,
                                 nameInvoker,
                                 c.getComentario(), 
                                 getLink(s));
        }
        
        @Override
        public String construirAsunto(SolicitudRequerimiento s, String nameInvoker){
            return String.format(getAsuntoSinFormato(), nameInvoker, s.getAsunto());
        }
    },
    CAMBIO_RESPONSABLE_SOLICITUD(){

        @Override
        public String construirMensaje(Object o, String nameInvoker) {
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(),
                                 s.getAsunto(),
                                 s.getSolicitante().getNombre(),
                                 s.getResponsable().getNombreCorto(),
                                 s.getResponsable().getNombreCorto(),
                                 getLink(s));
        }
        
        
    },
    CAMBIO_SOLICITUD(){
        @Override
        public String construirMensaje(Object o, String nameInvoker) {
            SolicitudRequerimiento s = (SolicitudRequerimiento) o;
            return String.format(getMensajeSinFormato(),
                                 s.getAsunto(),
                                 s.getResponsable().getNombre(),
                                 "%s",
                                 getLink(s));
        }
        
    };
    
    private static final String propertieEmail = "Emails";
    private static final String prefijoAsunto = "ASUNTO_";
    private static final String prefijoMensaje = "MSG_";
    
    private static String getLink(SolicitudRequerimiento s){
        return UrlBuilder.buildPublicUrlSolicitudReq(s.getCodigoConsulta());
    }
    
    public String getAsuntoSinFormato(){
        return  Resources.getValue(propertieEmail, prefijoAsunto + name());
    }
    
    public String getMensajeSinFormato(){
        return Resources.getValue(propertieEmail, prefijoMensaje + name());
    }
    
    public String construirAsunto(SolicitudRequerimiento s, String nameInvoker){
        return String.format(getAsuntoSinFormato(), s.getAsunto());
    }
            
    public abstract String construirMensaje(Object o, String nameInvoker);
  
    
}
