/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.proyectos.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.ejb.EstadoProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.ejb.ProyectoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.Proyecto;
import java.util.Date;
import java.util.Formatter;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Alejandro
 */
@Stateless
public class ProyectoEJB implements ProyectoEJBLocal {

    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private EstadoProyectoFacadeLocal estadoProyectoFacade;

    @Override
    public void cerrarProyecto(Proyecto p) {
        p.setFechaTermino(new Date());
        p.setEstadoProyecto(estadoProyectoFacade.find(Resources.getValueShort("Estados", "EstadoP_FINALIZADO")));
        proyectoFacade.edit(p);
    }

    @Override
    public void reabrirProyecto(Proyecto p) {
        p.setFechaTermino(null);
        p.setEstadoProyecto(estadoProyectoFacade.find(Resources.getValueShort("Estados", "EstadoP_ACTIVO")));
        proyectoFacade.edit(p);
    }

    @Override
    public String sugerirCodigoInterno(Area a) {
        String prefijo = "P" + a.getNombreArea().substring(0, 1);
        String maxCodigo = proyectoFacade.buscarMaximoCodigo(prefijo);
        maxCodigo = maxCodigo.replaceFirst(prefijo, "");
        Integer nuevoNumero = Integer.parseInt(maxCodigo) + 1;
        Formatter fmt = new Formatter();
        fmt.format("%04d", nuevoNumero);
        return prefijo + fmt.toString();
    }
}
