/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openid.controller;

import cl.uv.view.controller.base.utils.Resources;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Alejandro
 */
public class UrlResolver {

    public static String TYPE_SOLICITUD = "SOLICITUD";

    public static String buildRedirectResource(String redirectEncode, boolean isSolicitante) {
        Map params = decodeParameters(redirectEncode);
        String url = "";
        String typeResource = (String) params.get("tipoRecurso");
        String idResource = (String) params.get("idRecurso");
        
        if (typeResource!=null && typeResource.equals(TYPE_SOLICITUD)) {
            url = (isSolicitante) ? Resources.getValue("Pages", "solicitud_solicitante")
                                  : Resources.getValue("Pages", "solicitud_funcionario");
            url += "codigo=" + idResource;
        }

        return encodeURL(url);
    }

    public static Map decodeParameters(String parametersEncode) {
        Map paramsMap = null;
        String parametersDecode = decodeURL(parametersEncode);
        String[] arrayParams = parametersDecode.split("&");
        if (arrayParams.length > 0) {
            paramsMap = new HashMap();
            for (String p : arrayParams) {
                String[] keyValue = p.split("=");
                if (keyValue.length == 2) {
                    paramsMap.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return paramsMap;
    }

    public static String encodeURL(String url) {
        byte[] encoded = Base64.encodeBase64(url.getBytes());
        return new String(encoded);
    }

    public static String decodeURL(String url) {
        byte[] decoded = Base64.decodeBase64(url.getBytes());
        return new String(decoded);
    }
}
