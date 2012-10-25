/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.url.utils;

import cl.uv.model.base.utils.Resources;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Alejandro
 */
public class UrlBuilder {
    public static String param1="tipoRecurso";
    public static String param2="idRecurso";
            
    public static String buildPublicUrlSolicitudReq(String codigoConsulta){
        String dominio = Resources.getValue("BasicParams", "dominio");
        String puerto = Resources.getValue("BasicParams", "puerto");
        String nombreProyecto = Resources.getValue("BasicParams", "nombre_proyecto");
        String urlPublica = Resources.getValue("BasicParams", "url_publica");
        
        String url = dominio+puerto+nombreProyecto+urlPublica;
        String parametros = buildEncodeParams(TypeWebResource.SOLICITUD, codigoConsulta);
        
        return url+"?url="+parametros;
    }
    
    private static String buildEncodeParams(TypeWebResource t, String idRecurso){
        String parametrosUrl = param1+"="+t.name()+"&"+param2+"="+idRecurso;
        parametrosUrl = encodeURL(parametrosUrl);
        return parametrosUrl;
    }
    
    public static String encodeURL(String url) {
        byte[] encoded = Base64.encodeBase64(url.getBytes());
        return new String(encoded);
    }
}
