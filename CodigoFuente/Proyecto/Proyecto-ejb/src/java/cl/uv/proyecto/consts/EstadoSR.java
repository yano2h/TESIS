/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.consts;

/**
 *
 * @author Jano
 */
public class EstadoSR {
    public static final Short ENVIADA   = new Short((short)0);
    public static final Short RECHAZADA = new Short((short)1);
    public static final Short TRANSFERIDA = new Short((short)2);
    public static final Short ASIGNADA  = new Short((short)3);
    public static final Short PENDIENTE = new Short((short)4);
    public static final Short INICIADA  = new Short((short)5);
    public static final Short VENCIDA   = new Short((short)6);
    public static final Short FINALIZADA_SIN_RESPUESTA = new Short((short)7);
    public static final Short CERRADA   = new Short((short)8);
}
