/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.file.ejb;

import cl.uv.model.base.utils.FileUtils;
import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.ArchivoAdjuntoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ArchivoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.ArchivoSolicitudRequerimiento;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Alejandro
 */
@Stateless
public class FileManagerEJB implements FileManagerEJBLocal {

    @EJB
    private ArchivoAdjuntoFacadeLocal archivoAdjuntoFacade;
    @EJB
    private ArchivoSolicitudRequerimientoFacadeLocal archivoSolicitudRequerimientoFacade;
    private String basePath;

    public FileManagerEJB() {
        basePath = Resources.getValue("BasicParam", "pathArchivosSolicitudes");
    }
    
    @Override
    public void adjuntarArchivosSolicitudRequerimiento(List<ArchivoAdjunto> archivosAdjuntos, SolicitudRequerimiento solicitud){
        List<ArchivoSolicitudRequerimiento> listArchivos = new ArrayList<ArchivoSolicitudRequerimiento>();

        for (ArchivoAdjunto archivoAdjunto : archivosAdjuntos) {
            archivoAdjunto.setFechaUpload(new Date());
            String pathFile = buildPathArchivoAdjuntoSolicitudReq(archivoAdjunto.getFechaUpload(),
                                                                  solicitud.getIdSolicitudRequerimiento());
            archivoAdjunto.setPathFile(pathFile+archivoAdjunto.getNombre());
            archivoAdjunto.setSizeFormat(FileUtils.convertSizeRedondeado(archivoAdjunto.getSizeFile()));
            FileUtils.createDirectory(basePath + pathFile);
            FileUtils.writeUploadFile(basePath + archivoAdjunto.getPathFile(), archivoAdjunto.getInputStream());//Escribe el archivo en disco
            archivoAdjuntoFacade.create(archivoAdjunto);            
            ArchivoSolicitudRequerimiento asr = new ArchivoSolicitudRequerimiento(solicitud.getIdSolicitudRequerimiento(), 
                                                                                  archivoAdjunto.getIdArchivo());
            asr.setSolicitudRequerimiento(solicitud);
            asr.setArchivoAdjunto(archivoAdjunto);
            listArchivos.add(asr);
        }
        
        for (ArchivoSolicitudRequerimiento a : listArchivos) {
            System.out.println("Arch:"+a);
            archivoSolicitudRequerimientoFacade.create(a);
        }
    }
    
    private void writeFile(InputStream input, String name, String pathFile){
        FileUtils.createDirectory(basePath + pathFile);
        FileUtils.writeUploadFile(basePath + pathFile + name, input);
    }
            
    @Override
    public ArchivoAdjunto loadContentFile(ArchivoAdjunto a){
        String fullPath = basePath + a.getPathFile();
        a.setInputStream(FileUtils.readDownloadFile(fullPath));
        return a;
    }
    
    @Override
    public String buildFullPath(ArchivoAdjunto a){
        return basePath + a.getPathFile();
    }
    
    private String buildPathArchivoAdjuntoSolicitudReq(Date d, Long id){
        String pathDate = FileUtils.convertDateToPath(d);
        String path = "/"+ pathDate+"/"+id.toString()+"/";
        return path;
    }
}
