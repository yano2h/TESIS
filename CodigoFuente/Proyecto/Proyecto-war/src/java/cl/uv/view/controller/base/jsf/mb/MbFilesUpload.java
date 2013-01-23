/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.base.jsf.mb;

import cl.uv.model.base.utils.FileUtils;
import cl.uv.proyecto.file.ejb.FileManagerEJBLocal;
import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.ArchivoProyecto;
import cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimiento;
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
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbFilesUpload {

    @EJB
    private FileManagerEJBLocal fileManagerEJB;
    private Long sizeLimitAttachment;
    private Long sizeAttachment;
    private List<ArchivoAdjunto> archivosAdjuntos;
    private UploadedFile uploadFile;
    private StreamedContent fileDownload;

    public MbFilesUpload() {
        sizeAttachment = 0L;
        sizeLimitAttachment = Resources.getValueLong("email", "sizeLimitAttachment");
        archivosAdjuntos = new ArrayList<ArchivoAdjunto>();
    }

    private ArchivoAdjunto parseUploadFileToArchivoAdjunto(UploadedFile file) throws IOException {
        System.out.println("--parseUploadFileToArchivoAdjunto--");
        ArchivoAdjunto adjunto = new ArchivoAdjunto();
        adjunto.setMimetype(file.getContentType());
        adjunto.setNombre(normalizarNombre(file.getFileName()));
        adjunto.setSizeFile(file.getSize());
        adjunto.setSizeFormat(FileUtils.convertSizeRedondeado(file.getSize()));
        adjunto.setInputStream(file.getInputstream());

        return adjunto;
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
                Logger.getLogger(MbFilesUpload.class.getName()).log(Level.SEVERE, null, ex);
                JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al Adjuntar Archivo", "No se puedo adjuntar el archivo");
            }
        }
    }

    public void handleFileUploadWithoutSizeValidation(FileUploadEvent event) {
        setUploadFile(event.getFile());
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

    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public UploadedFile extraerUploadedFile() {
        UploadedFile copy = getUploadFile();
        setUploadFile(null);
        return copy;
    }

    public ArchivoAdjunto extraerArchivoAdjunto() {
        System.out.println("--extraerArchivoAdjunto--");
        ArchivoAdjunto copy = null;
        try {
            copy = parseUploadFileToArchivoAdjunto(uploadFile);
        } catch (IOException ex) {
            Logger.getLogger(MbFilesUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        uploadFile = null;
        return copy;
    }

    public List<ArchivoAdjunto> extraerArchivosAdjuntos() {
        List<ArchivoAdjunto> list = new ArrayList<ArchivoAdjunto>(getArchivosAdjuntos());
        getArchivosAdjuntos().clear();
        return list;
    }

    public String normalizarNombre(String input) {
        // Descomposición canónica
        System.out.println("ORIGINAL : ");
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Nos quedamos únicamente con los caracteres ASCII
        Pattern pattern = Pattern.compile("\\P{ASCII}+");
        String output = pattern.matcher(normalized).replaceAll("");
        System.out.println("NORMAL   : " + output);
        return output.replaceAll(" ", "_");
    }

    public void clearFiles() {
        System.out.println("CLEAN");
        archivosAdjuntos.clear();
    }

    public void downloadFileProyecto(ArchivoProyecto a) {
        System.out.println("downloadFile(Proyecto)");
        ArchivoAdjunto file = a.getArchivoAdjunto();
        if (file.getInputStream() == null) {
            fileManagerEJB.loadContentFileProyecto(file);
        }

        setFileDownload(new DefaultStreamedContent(file.getInputStream(), file.getMimetype(), file.getNombre()));
    }

    public void downloadFileSolicitud(ArchivoSolicitudRequerimiento a) {
        System.out.println("downloadFile(Solicitud)");
        ArchivoAdjunto file = a.getArchivoAdjunto();
        if (file.getInputStream() == null) {
            fileManagerEJB.loadContentFileSolicitud(file);
        }

        setFileDownload(new DefaultStreamedContent(file.getInputStream(), file.getMimetype(), file.getNombre()));
    }

    public String findIcon(ArchivoAdjunto a) {
        String pathIcon = Resources.getValue("iconos", "path_iconos");
        String extension = FilenameUtils.getExtension(a.getNombre());
        String icono = "";
        try {
            icono = Resources.getValue("iconos", extension);
        } catch (Exception e) {
            icono = Resources.getValue("iconos", "notfound");
        }
        return pathIcon + icono;
    }

    public StreamedContent getFileDownload() {
        return fileDownload;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }
}
