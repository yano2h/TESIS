/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.file.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.ArchivoProyecto;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface FileManagerEJBLocal {

    
    public void adjuntarArchivosSolicitudRequerimiento(List<ArchivoAdjunto> archivosAdjuntos, SolicitudRequerimiento solicitud);
    public void adjuntarArchivosProyecto(List<ArchivoProyecto> archivosAdjuntos, Proyecto p);
    
    public ArchivoAdjunto loadContentFileSolicitud(ArchivoAdjunto a);
    public ArchivoAdjunto loadContentFileProyecto(ArchivoAdjunto a);
    public String buildFullPath(ArchivoAdjunto a);
    
}
