/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import javax.ejb.Local;

/**
 *
 * @author Jano
 */
@Local
public interface EmailEJBLocal {

    void enviarEmail(String direccion, String asunto, String mensaje);
    void enviarEmail(String[] direcciones,String asunto, String mensaje);

    public void hacerNanda();
}
