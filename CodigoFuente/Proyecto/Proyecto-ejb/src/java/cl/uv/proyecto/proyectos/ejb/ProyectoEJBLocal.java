/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.proyectos.ejb;

import cl.uv.proyecto.persistencia.entidades.Proyecto;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface ProyectoEJBLocal {

    public void cerrarProyecto(Proyecto p);

    public void reabrirProyecto(Proyecto p);
    
}
