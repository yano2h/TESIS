/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.security.openid.controller;

import cl.uv.proyecto.url.utils.UrlBuilder;
import cl.uv.proyecto.url.utils.UrlResolver;
import cl.uv.security.openid.OpenIdSession;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@SessionScoped
public class GmailSession implements Serializable{

    private static String ENDPOINT_GOOGLE = "https://www.google.com/accounts/o8/id";
    private ConsumerManager manager;
    private DiscoveryInformation discovered;
//    private String openIdEmail;
//    private String validatedId;

    @ManagedProperty(value="#{openIdSession}")
    private OpenIdSession openIdSession;

    public void googleAuthentication() {
        manager = new ConsumerManager();
        Map parameters = JsfUtils.getExternalContext().getRequestParameterMap();
        String paramUrl = (String) parameters.get("url");
        String returnToUrl = returnToUrl(UrlBuilder.buildUrlExtensionResponse(paramUrl));
        String url = authRequest(returnToUrl);

        if (url != null) {
            JsfUtils.redirect(url);
        }
    }

    private String returnToUrl(String urlExtension) {
        HttpServletRequest request = (HttpServletRequest) JsfUtils.getExternalContext().getRequest();
        String returnToUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                + JsfUtils.getFacesContext().getApplication().getViewHandler().getActionURL(JsfUtils.getFacesContext(), urlExtension);
        return returnToUrl;
    }

    private String authRequest(String returnToUrl) {
        try {
            List discoveries = manager.discover(ENDPOINT_GOOGLE);
            discovered = manager.associate(discoveries);
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

            FetchRequest fetch = FetchRequest.createFetchRequest();
            fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);
            authReq.addExtension(fetch);

            return authReq.getDestinationUrl(true);
        } catch (OpenIDException e) {
            // TODO
        }
        return null;
    }

    public void verify() {
        ExternalContext context = javax.faces.context.FacesContext
                .getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        openIdSession.setId( verifyResponse(request) );
    }

    private String verifyResponse(HttpServletRequest httpReq) {
        try {
            ParameterList response =
                    new ParameterList(httpReq.getParameterMap());

            StringBuffer receivingURL = httpReq.getRequestURL();
            String queryString = httpReq.getQueryString();
            if (queryString != null && queryString.length() > 0) {
                receivingURL.append("?").append(httpReq.getQueryString());
            }

            VerificationResult verification = manager.verify(receivingURL.toString(), response, discovered);

            Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);

                    List emails = fetchResp.getAttributeValues("email");
                    openIdSession.setEmail((String) emails.get(0));
                }
                return verified.getIdentifier();
            }
        } catch (OpenIDException e) {
            // TODO
        }
        return null;
    }

    public String getOpenIdEmail() {
        return openIdSession.getEmail();
    }

    public String getValidatedId() {
        return openIdSession.getId();
    }

    public String getOnLoad() {
        verify();
        Map parameters = JsfUtils.getExternalContext().getRequestParameterMap();
        String paramsUrl = (String) parameters.get("url");
        String mainPage = Resources.getValue("pages", "redirect_main_page");
        mainPage += "?faces-redirect=true&url="+paramsUrl;
        System.out.println("URL MAINPAGE:"+mainPage);
        JsfUtils.redirect(mainPage);
        return "pageLoaded";
    }
       
    public void closeSession(){
        openIdSession.closeSession();
    }
    
    public void setOpenIdSession(OpenIdSession openIdSession) {
        this.openIdSession = openIdSession;
    }
}
