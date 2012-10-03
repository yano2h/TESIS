/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.file.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoAdjunto;
import cl.uv.proyecto.persistencia.entidades.SolicitudRequerimiento;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface FileManagerEJBLocal {

    
    public void adjuntarArchivosSolicitudRequerimiento(List<ArchivoAdjunto> archivosAdjuntos, SolicitudRequerimiento solicitud);

    public ArchivoAdjunto loadContentFile(ArchivoAdjunto a);
    
}
