package cl.uv.model.base.utils;

import java.util.*;

public class Resources {

    public static final String PATH_RESOURCES = "cl.uv.model.base.properties.Path";
    
    public static String getValue(String path, String key) {
        return PropertyResourceBundle.getBundle(Resources.getPropertiesPath(path)).getString(key);
    }

    public static Short getValueShort(String path, String key) {
        return Short.parseShort(getValue(path, key));
    }
    
    public static Integer getValueInteger(String path, String key) {
        return Integer.parseInt(getValue(path, key));
    }
    
    public static Long getValueLong(String path, String key) {
        return Long.parseLong(getValue(path, key));
    }
    
    public static String getPropertiesPath(String key) {
        return PropertyResourceBundle.getBundle(Resources.PATH_RESOURCES).getString(key);
    }

    public static ResourceBundle getPageList(String path) {
        return PropertyResourceBundle.getBundle(Resources.getPropertiesPath(path));
    }
    
    public static Map getMapPageList(String propertyName) {
        ResourceBundle strBuffer = PropertyResourceBundle.getBundle(Resources.getPropertiesPath(propertyName));
        Map mapPageList =  new HashMap();
        for (Enumeration e = strBuffer.getKeys(); e.hasMoreElements();) {
            String key = (String)e.nextElement();
            mapPageList.put(key, strBuffer.getString(key));
        }
        
        return mapPageList;
    }
}