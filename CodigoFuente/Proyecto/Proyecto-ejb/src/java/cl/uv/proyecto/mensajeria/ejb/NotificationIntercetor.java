/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.proyecto.mensajeria.ejb;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Jano
 */
public class NotificationIntercetor {
    NotificacionEJBLocal notificacionEJB = lookupNotificacionEJBLocal();

    @AroundInvoke
    public Object generarNotificacion(InvocationContext ctx) throws Exception {
        Object interceptedObject = ctx.getTarget();
        Method interceptedMethod = ctx.getMethod();
        
        System.out.println("->OBJCT:"+interceptedObject.getClass().getName());
        
        //Antes
        Object o = ctx.proceed();
        //notificacionEJB.crearNotificacionEnvioSolicitud(null);
        //Despues
        
        return o;
    }
    
    private NotificacionEJBLocal lookupNotificacionEJBLocal() {
        try {
            Context c = new InitialContext();
            return (NotificacionEJBLocal) c.lookup("java:global/Proyecto/Proyecto-ejb/NotificacionEJB!cl.uv.proyecto.mensajeria.ejb.NotificacionEJBLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
