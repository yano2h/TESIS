/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.proyectos.ejb;

import cl.uv.proyecto.persistencia.entidades.ArchivoProyecto;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface ProyectoEJBLocal {

    public void cerrarProyecto(Proyecto p);

    public void reabrirProyecto(Proyecto p);

    public String sugerirCodigoInterno(Area a);

    public void crearProyecto(Proyecto p, FuncionarioDisico f);
    
    void removerArchivosAdjuntos(List<ArchivoProyecto> files);
}
