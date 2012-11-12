/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.model.base.utils.FileUtils;
import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import cl.uv.view.controller.solicitudes.jsf.mb.MbCrearSolicitud;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbFilesUpload {

    private Long sizeLimitAttachment;
    private Long sizeAttachment;
    private List<ArchivoAdjunto> archivosAdjuntos;
    
    public MbFilesUpload() {
        sizeAttachment = 0L;
        sizeLimitAttachment = Resources.getValueLong("email", "sizeLimitAttachment");
        archivosAdjuntos = new ArrayList<ArchivoAdjunto>();
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (archivosAdjuntos == null) {
            archivosAdjuntos = new ArrayList<ArchivoAdjunto>();
        }
        
        if (sizeValidation(file.getSize())) {
            ArchivoAdjunto adjunto = new ArchivoAdjunto();
            adjunto.setMimetype(file.getContentType());
            adjunto.setNombre(normalizarNombre(file.getFileName()));
            adjunto.setSizeFile(file.getSize());
            adjunto.setSizeFormat(FileUtils.convertSizeRedondeado(file.getSize()));
            try {
                adjunto.setInputStream(file.getInputstream());
                archivosAdjuntos.add(adjunto);
                sizeAttachment += adjunto.getSizeFile();
            } catch (IOException ex) {
                Logger.getLogger(MbCrearSolicitud.class.getName()).log(Level.SEVERE, null, ex);
                JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al Adjuntar Archivo", "No se puedo adjuntar el archivo");
            }
        }
    }

    public void remove(ArchivoAdjunto f) {
        archivosAdjuntos.remove(f);
        sizeAttachment -= f.getSizeFile();
    }

    public boolean sizeValidation(Long size) {
        if ((sizeAttachment + size) > sizeLimitAttachment) {
            if (archivosAdjuntos != null && archivosAdjuntos.size() > 0) {
                JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, 
                                    "Error al Adjuntar Archivo", 
                                    Resources.getValue("email", "msg_error_totalsize"));
            }
            return false;
        } else {
            return true;
        }
    }

    public List<ArchivoAdjunto> getArchivosAdjuntos() {
        return archivosAdjuntos;
    }

    public void setArchivosAdjuntos(List<ArchivoAdjunto> archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }
    
    public List<ArchivoAdjunto> extraerArchivosAdjuntos(){
        List<ArchivoAdjunto> list = new ArrayList<ArchivoAdjunto>(getArchivosAdjuntos());
        getArchivosAdjuntos().clear();
        return list;
    }
    
    public String normalizarNombre(String input) {
        // Descomposición canónica
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Nos quedamos únicamente con los caracteres ASCII
        Pattern pattern = Pattern.compile("\\P{ASCII}+");
        String output = pattern.matcher(normalized).replaceAll("");
        return output.replaceAll(" ", "_");
    }   
}
