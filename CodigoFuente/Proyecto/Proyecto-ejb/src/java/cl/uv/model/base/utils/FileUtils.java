/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.model.base.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

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
    
    public static InputStream readDownloadFile(String path){
        System.out.println("PATH:"+path);
        File file = new File(path);
        InputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileInput;
    }
    
    public static boolean createDirectory(String path){
        File f = new File(path);
        if (!f.exists()) {
            return f.mkdirs();
        }
        return true;
    }
    
    public static String convertSize(long bytes){
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

    public static String convertSizeRedondeado(long bytes){
        String unit;
        float size;
        
        if (bytes >= 1000000) {
            size = (float)bytes/(float)1000000;
            unit = "MB";
        }else{
            size = (float)bytes/(float)1000;
            unit = "KB";
        }
        
        int sizeRedondeado = MathUtils.redondearFloat(size,0).intValue();
        return sizeRedondeado+unit;
    }
    
    public static String convertDateToPath(Date d){
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM", new Locale("es","CL"));
        String path = formateador.format(d);
        return path;
    }
    
    public static boolean isRenameNecessary(String filename){
        File f = new File(filename);
        return f.exists();
    }
    
    public static String buildNewName(String filename){
        String name = null;
        String path = FilenameUtils.getFullPath(filename);
        String baseName = FilenameUtils.getBaseName(filename);
        String extension = FilenameUtils.getExtension(filename);
        for(int count=1; isRenameNecessary(filename); count++){
            name = baseName+"_("+count+")."+extension;
            filename = path+name;
        }
        
        return name;
    }
    

}
