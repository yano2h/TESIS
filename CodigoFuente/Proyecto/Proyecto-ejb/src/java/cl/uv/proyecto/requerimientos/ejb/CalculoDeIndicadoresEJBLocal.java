/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.requerimientos.ejb;

import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface CalculoDeIndicadoresEJBLocal {

    Long contarSolicitudes(FuncionarioDisico funcionario, Short idEstado);

    Long contarSolicitudes(Area area, Short idEstado);

    Long contarSolicitudes(Short idEstado);

    Long porcentajeRetrasos(FuncionarioDisico f);

    Long porcentajeRetrasos(Area a);

    Long porcentajeRetrasos();

    Long porcentajeSolicitudesAsignadas(FuncionarioDisico f);

    Long porcentajeSolicitudesAsignadas(Area a);
}
