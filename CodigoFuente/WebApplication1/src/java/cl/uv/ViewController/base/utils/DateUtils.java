/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uv.ViewController.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Eduardo Godoy
 */
public  class DateUtils {

    public DateUtils() {
    }

    /**
     * Este método compara dos fechas y  retorna un cero si son iguales, 1 si la fecha 1 es mayor que la fecha 2 y -1 si
     * la fecha 1 es menor que la fecha 2. Recibe los parametros como tipo Date. En caso de existir una Exception el método
     * retorna  null
     * @return Integer
     */
    public static Integer comparaFechas(Date p_fecha1, Date p_fecha2) {
        Integer result = null;

        try {
            if ((null != p_fecha1) && (null != p_fecha2)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar fechaInicial = Calendar.getInstance();
                Calendar fechaFinal = Calendar.getInstance();
                fechaInicial.setTime(p_fecha1); 
                fechaFinal.setTime(p_fecha2);
                
                result = fechaInicial.compareTo(fechaFinal);
            }
        } catch (Exception ex) {
            return null;
        }
        return result;
    }

    /**
     * Este método recibe una fecha del tipo Date y retorna la misma fecha en formato Calendar. Si la fecha que se recibe es nula entonces se retorna la fecha actual.
     * @param p_fecha Date
     * @return fecha Calendar
     */
    public static Calendar date2Calendar(Date p_fecha) {
        Calendar result = Calendar.getInstance();
        if (p_fecha != null) {
            result.setTime(p_fecha);
        }
        return result;
    }

    /**
     * Este método retorna la fecha del dia actual.
     * @return Date
     *
     */
    public static Date getToday() {
        Calendar fechaHoy = Calendar.getInstance();
        return fechaHoy.getTime();
    }

    /**
     * Este método retorna la fecha del día actual en formato "dd/MM/yyyy" y como tipo String.
     * @return String
     */
    public static String getTodayStr() {
       return getTodayStrByFormat("dd/MM/yyyy");
    }

    /**
     * Este método retorna la fecha del día actual en el formato especificado y como  tipo String.
     * @return String
     */
    public static String getTodayStrByFormat(String format) {
       Calendar fechaHoy = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat(format);
       return sdf.format(fechaHoy.getTime());
    }

    public static String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        TimeZone tm =TimeZone.getTimeZone(Resources.getValue("basicWebParam_path", "localZonaHoraria"));
        cal.setTimeZone(tm);
        return (new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString()+ ":" + new Integer(cal.get(Calendar.MINUTE)).toString());
    }
    /**
     * Este método recibe una fecha de tipo Date y retorna la misma fecha como  string  en formato dd/MM/yyyy.
     * @param p_date
     * @return
     */
    public static String getDateToString(Date p_date) {        
        return getDateToStringFormat(p_date, "dd/MM/yyyy");      
    }

    /**
     * Este método recibe una fecha de tipo Date y retorna la misma fecha como  string  en el  formato Ingresado.
     * @param p_date
     * @return
     */

    public static String getDateToStringFormat(Date p_date, String p_fmt) {
        if (p_date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(p_fmt);
            return sdf.format(p_date);
        }else{
            return "";
        }
    }

    /**
     * Permite convertir una línea de texto que representa una fecha en el formato ingresado por fmt,
     * cabe destacar que se utiliza la nomenclatura del Objeto SimpleDateFormat.
     *
     * @param p_dateString String, La fecha a convertir
     * @param p_fmt        String, Formato en el cuál esta escrita la fecha
     * @return Date con la fecha ingresada
     */
    public static Date stringTodate(String p_dateString, String p_fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(p_fmt);
        try {
            return sdf.parse(p_dateString);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String intDateToString(String p_dateString, String in_fmt,String out_fmt) {
        Date date;
        String newFormat = null;
        SimpleDateFormat sdf = new SimpleDateFormat(in_fmt);
        try {
            //sdf.applyPattern(p_fmt);
            date =  sdf.parse(p_dateString);
            sdf.applyPattern(out_fmt);
            newFormat=sdf.format(date);
        } catch (Exception ex) {
            date = null;
        }
        return  newFormat;
    }

    /**
     * Este método resta la cantidad de dias a la fecha que se enetrega por parametro. Si alguno de los parametros
     * viene null entonces retorna la fecha del dia new Date()
     *
     * @return Date
     *
     */
    public static Date restaDias(Date p_fecha, Long numeroDias) {
        if (p_fecha == null || numeroDias == null ) {
            return new Date();
        }
        
        Date result = new Date();
        Long tmp = new Long(0);
        tmp = p_fecha.getTime() - (numeroDias * (24 * 60 * 60 * 1000));
        result.setTime(tmp);
        return result;
    }

    /**
     * Este método recibe un año y dice si es bisiesto o no.
     * @param p_fecha
     * @return
     */
    public static boolean esAnnoBisiesto(Integer year) {
        boolean result = false;
        if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Este método suma la cantidad de dias a la fecha que se entrega por parametro. Si alguno de los parametros
     * viene null entonces retorna la fecha del dia new Date()
     *
     * @return Date
     *
     */
    public static Date sumaDias(Date p_fecha, Long numeroDias) {
        if (p_fecha == null) {
            return new Date();
        }
        if (numeroDias == null) {
            return new Date();
        }
        Date result = new Date();
        Long tmp = new Long(0);
        tmp = p_fecha.getTime() + (numeroDias * (24 * 60 * 60 * 1000));
        result.setTime(tmp);
        return result;
    }

    /**
     * Este método recibe el  mes de una fecha y retorna el nombre del mes.
     * @return String mesNombre
     */
    public static String getNameOfMonth(int p_month) {
        String result = "";

        switch (p_month) {
        case 1:
            result = "Enero";
            break;
        case 2:
            result = "Febrero";
            break;
        case 3:
            result = "Marzo";
            break;
        case 4:
            result = "Abril";
            break;
        case 5:
            result = "Mayo";
            break;
        case 6:
            result = "Junio";
            break;
        case 7:
            result = "Julio";
            break;
        case 8:
            result = "Agosto";
            break;
        case 9:
            result = "Septiembre";
            break;
        case 10:
            result = "Octubre";
            break;
        case 11:
            result = "Noviembre";
            break;
        case 12:
            result = "Diciembre";
            break;
        }
        return result;
    }

    public static String getFirstDayWhitDate(String year, String month){
        Calendar calInicio= Calendar.getInstance();
        calInicio.set(Calendar.DATE,1);
        calInicio.set(Calendar.MONTH,Integer.parseInt(month));
        calInicio.set(Calendar.YEAR,Integer.parseInt(year));
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        System.out.println( format.format(calInicio.getTime()));
        return format.format(calInicio.getTime());
    }

    public static String getLastDayWhitDate(String year, String month){
        Calendar calFin= Calendar.getInstance();
        calFin.set(Calendar.DATE,1);
        calFin.set(Calendar.MONTH,Integer.parseInt(month));
        calFin.set(Calendar.YEAR,Integer.parseInt(year));
        int lastDayOfMonth = calFin.getActualMaximum(Calendar.DATE);
        calFin.set(Calendar.DATE,lastDayOfMonth);
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        return format.format(calFin.getTime());
    }
  /**
    * Este método recibe el formato de fecha y retorna la fecha actual en String
    * @param formato formato de fecha requerida
    * @return String fecha actual
    */
   public static String getCurrentDate(String formato){
       Calendar cal= Calendar.getInstance();
       SimpleDateFormat format= new SimpleDateFormat(formato);
       return format.format(cal.getTime());
   }

}

