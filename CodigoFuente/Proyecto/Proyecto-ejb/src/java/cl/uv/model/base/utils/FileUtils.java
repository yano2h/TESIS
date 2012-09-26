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
}
