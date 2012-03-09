package cl.uv.ViewController.base.utils;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Resources {

    public static final String PATH_RESOURCES = "cl.uv.ViewController.base.properties.Path";
    
    public static String getValue(String path, String key) {
        return PropertyResourceBundle.getBundle(Resources.getPropertiesPath(path)).getString(key);
    }

    public static String getPropertiesPath(String key) {
        return PropertyResourceBundle.getBundle(Resources.PATH_RESOURCES).getString(key);
    }

    public static ResourceBundle getPageList(String path) {
        return PropertyResourceBundle.getBundle(Resources.getPropertiesPath(path));
    }
}