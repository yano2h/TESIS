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
    
    @Asynchronous
    @Override
    public void adjuntarArchivosSolicitudRequerimiento(List<ArchivoAdjunto> archivosAdjuntos, SolicitudRequerimiento solicitud){
        String basePath = Resources.getValue("BasicParam", "pathArchivosSolicitudes");
        for (ArchivoAdjunto archivoAdjunto : archivosAdjuntos) {
            archivoAdjunto.setFechaUpload(new Date());
            String pathFile = buildPathArchivoAdjuntoSolicitudReq(archivoAdjunto.getFechaUpload(),
                                                                  solicitud.getIdSolicitudRequerimiento());
            archivoAdjunto.setPathFile(pathFile+archivoAdjunto.getNombre());
            archivoAdjunto.setSizeFormat(FileUtils.convertSize(archivoAdjunto.getSizeFile()));
            FileUtils.createDirectory(basePath + pathFile);
            FileUtils.writeUploadFile(basePath + archivoAdjunto.getPathFile(), archivoAdjunto.getInputStream());
            archivoAdjuntoFacade.create(archivoAdjunto);
            
            ArchivoSolicitudRequerimiento asr = new ArchivoSolicitudRequerimiento(solicitud.getIdSolicitudRequerimiento(), 
                                                                                  archivoAdjunto.getIdArchivo());
            archivoSolicitudRequerimientoFacade.create(asr);
        }
    }
    
    public ArchivoAdjunto loadContentFile(ArchivoAdjunto a){
        String basePath = Resources.getValue("BasicParam", "pathArchivosSolicitudes");
        String fullPath = basePath + a.getPathFile();
        a.setInputStream(FileUtils.readDownloadFile(fullPath));
        return a;
    }
    
    private String buildPathArchivoAdjuntoSolicitudReq(Date d, Long id){
        String pathDate = FileUtils.convertDateToPath(d);
        String path = "/"+ pathDate+"/"+id.toString()+"/";
        return path;
    }
}
