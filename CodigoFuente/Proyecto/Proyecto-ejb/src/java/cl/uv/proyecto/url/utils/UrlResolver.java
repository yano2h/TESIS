/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.url.utils;

import cl.uv.model.base.utils.Resources;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Alejandro
 */
public class UrlResolver {
    public static String param1 = "action";
    public static String buildRedirectResource(String redirectEncode, boolean isSolicitante) {
        String url = "";
        Map params = decodeParameters(redirectEncode);
        String tipoRecurso = (String) params.get(UrlBuilder.param1);
        String idRecurso = (String) params.get(UrlBuilder.param2);
        
        if (tipoRecurso!=null && tipoRecurso.equals(TypeWebResource.SOLICITUD.name())) {
            url = (isSolicitante) ? Resources.getValue("Urls", "solicitud_solicitante")
                                  : Resources.getValue("Urls", "solicitud_funcionario");
            url += "?codigo=" + idRecurso;
        }

        return url;
    }


    
    public static Map decodeParameters(String parametersEncode) {
        Map paramsMap = null;
        String parametersDecode = decodeURL(parametersEncode);
        System.out.println("Params Decode"+parametersDecode);
        String[] arrayParams = parametersDecode.split("&");
        if (arrayParams.length > 0) {
            paramsMap = new HashMap();
            for (String p : arrayParams) {
                String[] keyValue = p.split("=");
                System.out.println("KEY VALUE:"+keyValue);
                if (keyValue.length == 2) {
                    paramsMap.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return paramsMap;
    }

    public static String decodeURL(String url) {
        byte[] decoded = Base64.decodeBase64(url.getBytes());
        return new String(decoded);
    }
}
