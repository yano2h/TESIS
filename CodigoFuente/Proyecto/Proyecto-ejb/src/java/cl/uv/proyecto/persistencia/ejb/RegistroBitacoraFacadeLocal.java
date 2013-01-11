/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.persistencia.ejb;

import cl.uv.proyecto.persistencia.entidades.Proyecto;
import cl.uv.proyecto.persistencia.entidades.RegistroBitacora;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alejandro
 */
@Local
public interface RegistroBitacoraFacadeLocal {
    void create(RegistroBitacora registroBitacora);

    void edit(RegistroBitacora registroBitacora);

    void remove(RegistroBitacora registroBitacora);

    RegistroBitacora find(Object id);

    List<RegistroBitacora> findAll();

    List<RegistroBitacora> findRange(int[] range);

    int count();
    
    List<RegistroBitacora> buscarBitacoraProyecto(Proyecto p);
    
    Date getFechaUltimoRegistro(Proyecto p);
    
    List<String> buscarContraparte(String contraparte);
}
