/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.test.junit.base;

import cl.uv.model.base.utils.Resources;
import cl.uv.proyecto.persistencia.entidades.*;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Alejandro
 */
public class EntityUtils {
    public static final Long ID_SOLICITUD_TEST = 1L;
    public static final Integer RUT_FUNCIONARIO_TEST = 11111111;
    public static final Integer ID_TAREA_SCM_TEST = 1;
    
    private static short idArea = 4;
    private static short idEstadoProyecto = 20;
    private static short idEstadoSolCambio = 20;
    private static short idEstadoSolReq = 20;
    private static short idTipoSolReq = 20;
    private static long idSolicitudReq = 100L;
    private static int rutFunctionario = 123;
    private static int idItemConfig = 1;
    private static Random r = new Random((new Date()).getTime());
    
    
    public static Area createArea(){
        Area a = new Area(idArea++);
        a.setNombreArea("Area - "+a.getIdArea());
        a.setDescripcionArea("Descripcion Area - "+a.getIdArea());
        return a;
    }
    
    public static EstadoProyecto createEstadoProyecto(){
        EstadoProyecto e = new EstadoProyecto(idEstadoProyecto++);
        e.setNombreEstadoProyecto("Estado "+e.getIdEstadoProyecto());
        return e;
    }
    
    public static EstadoSolicitudCambio createEstadoSolicitudCambio(){
        EstadoSolicitudCambio e = new EstadoSolicitudCambio(idEstadoSolCambio++);
        e.setDescripcionEstado("Descripcion estado sol"+e.getIdEstadoSolicitudCambio());
        e.setNombreEstadoSolicitud("Nombre Estado SC"+e.getIdEstadoSolicitudCambio());
        return e;
    }
    
    public static EstadoSolicitudRequerimiento createEstadoSolicitudReq(){
        EstadoSolicitudRequerimiento e = new EstadoSolicitudRequerimiento(idEstadoSolReq++);
        e.setDescripcionEstado("Descripcion estado sol"+e.getIdEstadoSolicitudRequerimiento());
        e.setNombreEstadoSolicitud("Nombre Estado SR"+e.getIdEstadoSolicitudRequerimiento());
        return e;
    }
            
    public static FormularioImplementacion createFormularioImplementacion(){
        FormularioImplementacion f = new FormularioImplementacion();
        return  f;
    }
    
    public static Funcionario createFuncionario(){
        Funcionario f = new Funcionario(rutFunctionario++);
        f.setApellidoMaterno("Apellido M"+f.getRut());
        f.setApellidoPaterno("Apellido P"+f.getRut());
        f.setCorreoUv("correo."+f.getApellidoPaterno()+"@uv.cl");
        f.setFechaPrimerAcceso(new Date());
        f.setFechaUltimoAcceso(new Date());
        f.setNombre("Nombre F"+f.getRut());
        f.setRol("Rol X");
        return f;
    }
    
    public static FuncionarioDisico createFuncionarioDisico(){
        FuncionarioDisico f = (FuncionarioDisico) EntityUtils.createFuncionario();
        f.setAnexo("123");
        f.setCargo("Cargo X");
        return f;
    }
    
    public static FuncionarioDisico createFuncionarioDisico(Funcionario fun){
        FuncionarioDisico f = (FuncionarioDisico) fun;
        f.setAnexo("123");
        f.setCargo("Cargo X");
        return f;
    }
    
    public static ItemConfiguracion createItemConfiguracion(){
        ItemConfiguracion i = new ItemConfiguracion(idItemConfig++);
        i.setCodigoIdentificadorIc("123"+i.getIdItemConfiguracion());
        i.setFechaUltimaModificacion(new Date());
        i.setNombreItemConfiguracion("NombreItem "+i.getIdItemConfiguracion());
        i.setVersion("v1.0");
        return i;
    }
    
    public static ComentarioSolicitud createComentarioSolicitud(Funcionario auth, SolicitudRequerimiento s){
        ComentarioSolicitud c = new ComentarioSolicitud();
        c.setAutor(auth);
        c.setComentario("Comentario - "+c.getIdComentario());
        c.setFecha(new Date());
        c.setVisible(true);
        c.setSolicitudRequerimiento(s);
        return c;
    }
    
    public static ComentarioSolicitud createComentarioSolicitud(SolicitudRequerimiento s){
        ComentarioSolicitud c = new ComentarioSolicitud();
        c.setAutor(new Funcionario(RUT_FUNCIONARIO_TEST));
        c.setComentario("Comentario - "+c.getIdComentario());
        c.setFecha(new Date());
        c.setVisible(true);
        c.setSolicitudRequerimiento(s);
        return c;
    }
    
    public static ComentarioSolicitud createComentarioSolicitud(){
        ComentarioSolicitud c = new ComentarioSolicitud();
        c.setAutor(new Funcionario(RUT_FUNCIONARIO_TEST));
        c.setComentario("Comentario - "+c.getIdComentario());
        c.setFecha(new Date());
        c.setVisible(true);
        c.setSolicitudRequerimiento(new SolicitudRequerimiento(ID_SOLICITUD_TEST));
        return c;
    }
    
    public static SolicitudRequerimiento createSolicitudReq(Area a, EstadoSolicitudRequerimiento e, TipoPrioridad tp, Funcionario solicitante, TipoSolicitudRequerimiento ts){
        SolicitudRequerimiento s = new SolicitudRequerimiento(idSolicitudReq++);
        s.setAreaResponsable(a);
        s.setAsunto("Asunto Solicitud "+s.getIdSolicitudRequerimiento());
        s.setCodigoConsulta(generateCodeRandom(s.getIdSolicitudRequerimiento()));
        s.setEstadoSolicitud(e);
        s.setFechaEnvio(new Date());
        s.setFechaUltimaActualizacion(new Date());
        s.setMensaje("Mensaje");
        s.setPrioridadSolicitud(tp);
        s.setRespuesta("Respuesta");
        s.setSolicitante(solicitante);
        s.setTipoSolicitud(ts);
        return s;
    }
    
    public static SolicitudRequerimiento createSolicitudReq(Area a, TipoSolicitudRequerimiento ts){
        SolicitudRequerimiento s = new SolicitudRequerimiento();
        s.setAreaResponsable(a);
        s.setAsunto("Asunto Solicitud ");
        s.setFechaEnvio(new Date());
        s.setFechaUltimaActualizacion(new Date());
        s.setMensaje("Mensaje");
        s.setRespuesta("Respuesta");
        s.setTipoSolicitud(ts);
        return s;
    }
    
    public static SolicitudRequerimiento createSolicitudReq(Funcionario sol){
        SolicitudRequerimiento s = new SolicitudRequerimiento(idSolicitudReq++);
        s.setAreaResponsable( new Area(Resources.getValueShort("Tipos", "Area_Desarrollo")) );
        s.setAsunto("Asunto Solicitud "+s.getIdSolicitudRequerimiento());
        s.setCodigoConsulta(generateCodeRandom(s.getIdSolicitudRequerimiento()));
        s.setEstadoSolicitud(new EstadoSolicitudRequerimiento(Resources.getValueShort("Estados", "EstadoSR_ENVIADA")) );
        s.setFechaEnvio(new Date());
        s.setFechaUltimaActualizacion(new Date());
        s.setMensaje("Mensaje");
        s.setPrioridadSolicitud(new TipoPrioridad(Resources.getValueShort("Estados", "EstadoSR_ENVIADA")));
        s.setRespuesta("Respuesta");
        s.setSolicitante(sol);
        s.setTipoSolicitud(new TipoSolicitudRequerimiento(Resources.getValueShort("Tipos", "TiposSol_CorreoUV")));
        return s;
    }
    
    public static TipoSolicitudRequerimiento createTipoSolicitudRequerimiento(){
        TipoSolicitudRequerimiento t = new TipoSolicitudRequerimiento(idTipoSolReq++);
        t.setDescripcionTipo("Descripcion - "+t.getIdTipoSolicitudRequerimiento());
        t.setNombreTipoSolicitud("Nombre - "+t.getIdTipoSolicitudRequerimiento());
        return t;
    }
    
    private static String generateCodeRandom(long idSol){
        String s = ""+idSol;
        for (int i = 0; i < 5; i++) {
            s += keyRamdom();
        }
        
        return s;
    }
    
    private static String keyRamdom(){
        String seed =  "1qQaAzZ2wWsSxX3eEdDcC4rRfFvV5TtgGbB6yYhHnN7uUjJmM8iIkK9oOlpP0";
        int length = seed.length() - 1;
        int n = (int) (r.nextInt() % length);
        n = (n<0)? n*-1:n;
        return String.valueOf(seed.charAt(n));
    }

    public static Entregable createEntregable() {
        Entregable e = new Entregable();
        e.setNombreEntregable("Entregable Test");
        e.setTareaScm(new TareaScm(ID_TAREA_SCM_TEST));
        return e;
    }
}
