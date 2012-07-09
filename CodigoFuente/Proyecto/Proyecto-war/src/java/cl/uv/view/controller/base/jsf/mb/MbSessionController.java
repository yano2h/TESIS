/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@SessionScoped
public class MbSessionController implements Serializable{
    public static final String  NAVIGATE_PATH = "pages";

    private Map<String, String> pagesMap;
    private String left = "";
    private String top = "";
    private String footer = "";
    private String content = "";
    
    public MbSessionController() {
        pagesMap = new HashMap<String, String>();
        pagesMap.putAll(Resources.getMapPageList(MbSessionController.NAVIGATE_PATH));
        this.top = pagesMap.get("header");
        this.left = pagesMap.get("menuLeft");
        this.footer = pagesMap.get("footer");
        this.content = pagesMap.get("content");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }
    
    
}
