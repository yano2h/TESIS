/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.proyectos.ejb;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.file.ejb.FileManagerEJBLocal;
import cl.uv.proyecto.persistencia.ejb.*;
import cl.uv.proyecto.persistencia.entidades.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Alejandro
 */
@Stateless
public class ProyectoEJB implements ProyectoEJBLocal {

    @EJB
    private ArchivoProyectoFacadeLocal archivoProyectoFacade;
    @EJB
    private ProyectoFacadeLocal proyectoFacade;
    @EJB
    private EstadoProyectoFacadeLocal estadoProyectoFacade;
    @EJB
    private RolProyectoFacadeLocal rolProyectoFacade;
    @EJB
    private ParticipanteProyectoFacadeLocal participanteProyectoFacade;
    @EJB
    private FileManagerEJBLocal fileManagerEJB;

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

    @Override
    public void crearProyecto(Proyecto p, FuncionarioDisico f) {
        if (p != null && f != null && f.getArea() != null) {
            p.setAreaResponsable(f.getArea());
            proyectoFacade.create(p);
            RolProyecto rol = rolProyectoFacade.find(Resources.getValueShort("Tipos", "RolProyecto_JP"));
            ParticipanteProyecto participante = new ParticipanteProyecto(f.getRut(), p.getIdProyecto());
            participante.setProyecto(p);
            participante.setParticipante(f);
            participante.setRol(rol);
            participanteProyectoFacade.create(participante);
            if (p.getArchivoProyectoList() != null && p.getArchivoProyectoList().size() > 0) {
                fileManagerEJB.adjuntarArchivosProyecto(p.getArchivoProyectoList(), p);
            }
        } else {
            throw new NullPointerException("Imposible crear el proyecto nulo o con un funcionario nulo o sin area");
        }
    }

    @Override
    public void removerArchivosAdjuntos(List<ArchivoProyecto> files) {
        if (files != null) {
            for (ArchivoProyecto a : files) {
                fileManagerEJB.removerArchivoAdjuntoProyecto(a.getArchivoAdjunto());
                archivoProyectoFacade.remove(a);
            }
        }
    }
}
