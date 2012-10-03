/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.model.base.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro
 */
public class FileUtils {

    
    public static void writeUploadFile(String path, InputStream input) {
        File file = new File(path);
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            int size;
            byte buf[] = new byte[1024];
            while ((size = input.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
                out.flush();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static boolean createDirectory(String path){
        File f = new File(path);
        if (!f.exists()) {
            return f.mkdir();
        }
        return true;
    }
    
    public String convertSize(long bytes){
        System.out.println("SIZE:"+bytes);
        String unit;
        if (bytes >= 1000000) {
            float size = (float)bytes/(float)1000000;
            unit = size+"MB";
        }else{
            float size = (float)bytes/(float)1000;
            unit = size+"KB";
        }
        
        return unit;
    }

}
