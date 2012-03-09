package cl.uv.ViewController.base.jsf.mb;

import cl.uv.ViewController.base.utils.Resources;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MbSessionController {
    public static final String  NAVIGATE_PATH = "navigate_path";
    
    private String navPage = "";
    private String contentPage = "";
    private String leftMenuPage = "";
    private String rightMenuPage = "";
    private String headerPage = "";
    private String footerPage = "";
    private Map sessionScopeMap = new HashMap<String, Object>();
    private Map sessionNavigateMap = new HashMap<String, Object>();

    public MbSessionController() {
        init();
    }

    private void init() {
        sessionNavigateMap.putAll(Resources.getMapPageList(MbSessionController.NAVIGATE_PATH));
 
        this.setNavPage((String) getSessionNavigateMap().get("nav"));
        this.setHeaderPage((String) getSessionNavigateMap().get("header"));
        this.setLeftMenuPage((String) getSessionNavigateMap().get("leftMenu"));
        this.setRightMenuPage((String) getSessionNavigateMap().get("rightMenu"));
        this.setFooterPage((String) getSessionNavigateMap().get("footer"));
        this.setContentPage((String) getSessionNavigateMap().get("inicio"));
    }

    public void navigate(String page) {
        setContentPage((String) getSessionNavigateMap().get(page));
    }

    public String getNavPage() {
        return navPage;
    }

    public void setNavPage(String navPage) {
        this.navPage = navPage;
    }

    public String getContentPage() {
        return contentPage;
    }

    public void setContentPage(String contentPage) {
        this.contentPage = contentPage;
    }

    public Map getSessionScopeMap() {
        return sessionScopeMap;
    }

    public String getLeftMenuPage() {
        return leftMenuPage;
    }

    public void setLeftMenuPage(String leftMenuPage) {
        this.leftMenuPage = leftMenuPage;
    }

    public String getFooterPage() {
        return footerPage;
    }

    private Map getSessionNavigateMap() {
        return sessionNavigateMap;
    }

    public String getRightMenuPage() {
        return rightMenuPage;
    }

    public void setRightMenuPage(String rightMenuPage) {
        this.rightMenuPage = rightMenuPage;
    }

    public String getHeaderPage() {
        return headerPage;
    }

    public void setHeaderPage(String headerPage) {
        this.headerPage = headerPage;
    }

    public void setFooterPage(String footerPage) {
        this.footerPage = footerPage;
    }
}
