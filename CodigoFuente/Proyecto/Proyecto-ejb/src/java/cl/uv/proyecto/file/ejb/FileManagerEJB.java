/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.file.ejb;

import cl.uv.model.base.utils.FileUtils;
import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.ArchivoAdjuntoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ArchivoProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ArchivoSolicitudRequerimientoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.*;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.io.FilenameUtils;

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
    @EJB
    private ArchivoProyectoFacadeLocal archivoProyectoFacade;
    private String basePathSolicitudes;
    private String basePathProyectos;
    private final String prefijoDelete = "_delete_";

    public FileManagerEJB() {

        basePathSolicitudes = Resources.getValue("BasicParam", "pathArchivosSolicitudes");
        basePathProyectos = Resources.getValue("BasicParam", "pathArchivosProyectos");
    }

    @Override
    public void adjuntarArchivosSolicitudRequerimiento(List<ArchivoAdjunto> archivosAdjuntos, SolicitudRequerimiento solicitud) {
        List<ArchivoSolicitudRequerimiento> listArchivos = new ArrayList<ArchivoSolicitudRequerimiento>();

        for (ArchivoAdjunto archivoAdjunto : archivosAdjuntos) {
            archivoAdjunto.setFechaUpload(new Date());
            String pathFile = buildPathArchivoAdjunto(archivoAdjunto.getFechaUpload(),
                    solicitud.getIdSolicitudRequerimiento());
            if (FileUtils.isRenameNecessary(basePathSolicitudes + pathFile + archivoAdjunto.getNombre())) {
                String nuevoNombre = FileUtils.buildNewName(basePathSolicitudes + pathFile + archivoAdjunto.getNombre());
                archivoAdjunto.setNombre(nuevoNombre);
            }

            archivoAdjunto.setPathFile(pathFile + archivoAdjunto.getNombre());
            archivoAdjunto.setSizeFormat(FileUtils.convertSizeRedondeado(archivoAdjunto.getSizeFile()));

            writeFile(archivoAdjunto.getInputStream(), archivoAdjunto.getNombre(), basePathSolicitudes, pathFile);

            archivoAdjuntoFacade.create(archivoAdjunto);
            ArchivoSolicitudRequerimiento asr = new ArchivoSolicitudRequerimiento(solicitud.getIdSolicitudRequerimiento(),
                    archivoAdjunto.getIdArchivo());
            asr.setSolicitudRequerimiento(solicitud);
            asr.setArchivoAdjunto(archivoAdjunto);
            listArchivos.add(asr);
        }

        for (ArchivoSolicitudRequerimiento a : listArchivos) {
            archivoSolicitudRequerimientoFacade.create(a);
        }
    }

    @Override
    public void adjuntarArchivosProyecto(List<ArchivoProyecto> archivosAdjuntos, Proyecto p) {
        for (ArchivoProyecto archivoProyecto : archivosAdjuntos) {
            ArchivoAdjunto archivoAdjunto = archivoProyecto.getArchivoAdjunto();
            archivoAdjunto.setFechaUpload(new Date());
            String pathFile = buildPathArchivoAdjunto(archivoAdjunto.getFechaUpload(),
                    (long) p.getIdProyecto());
            if (FileUtils.isRenameNecessary(basePathProyectos + pathFile + archivoAdjunto.getNombre())) {
                String nuevoNombre = FileUtils.buildNewName(basePathProyectos + pathFile + archivoAdjunto.getNombre());
                archivoAdjunto.setNombre(nuevoNombre);
            }

            archivoAdjunto.setPathFile(pathFile + archivoAdjunto.getNombre());
            archivoAdjunto.setSizeFormat(FileUtils.convertSizeRedondeado(archivoAdjunto.getSizeFile()));

            writeFile(archivoAdjunto.getInputStream(), archivoAdjunto.getNombre(), basePathProyectos, pathFile);
            archivoAdjuntoFacade.create(archivoAdjunto);
            archivoProyecto.setArchivoProyectoPK(new ArchivoProyectoPK(p.getIdProyecto(), archivoAdjunto.getIdArchivo()));
        }

        for (ArchivoProyecto archivoProyecto : archivosAdjuntos) {
            archivoProyectoFacade.create(archivoProyecto);
        }
    }

    @Asynchronous
    private void writeFile(InputStream input, String name, String basePath, String pathFile) {
        FileUtils.createDirectory(basePath + pathFile);
        FileUtils.writeUploadFile(basePath + pathFile + name, input);
    }

    @Override
    public ArchivoAdjunto loadContentFileSolicitud(ArchivoAdjunto a) {
        System.out.println("loadContentFileSolicitud");
        String fullPath = basePathSolicitudes + a.getPathFile();
        a.setInputStream(FileUtils.readDownloadFile(fullPath));
        return a;
    }

    @Override
    public ArchivoAdjunto loadContentFileProyecto(ArchivoAdjunto a) {
        System.out.println("loadContentFileProyecto");
        String fullPath = basePathProyectos + a.getPathFile();
        a.setInputStream(FileUtils.readDownloadFile(fullPath));
        return a;
    }

    @Override
    public String buildFullPath(ArchivoAdjunto a) {
        return basePathSolicitudes + a.getPathFile();
    }

    private String buildPathArchivoAdjunto(Date d, Long id) {
        String pathDate = FileUtils.convertDateToPath(d);
        String path = "/" + pathDate + "/" + id.toString() + "/";
        return path;
    }

    @Override
    public void removerArchivoAdjunto(String path, ArchivoAdjunto a) {
        File f = new File(path + a.getPathFile());
        if (f.exists()) {
            f.delete();
        }
        archivoAdjuntoFacade.edit(a);
    }

    @Override
    public void removerArchivoAdjuntoProyecto(ArchivoAdjunto a) {
        removerArchivoAdjunto(basePathProyectos, a);
    }
}
