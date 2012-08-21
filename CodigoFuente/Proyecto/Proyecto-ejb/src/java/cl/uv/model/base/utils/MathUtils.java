/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.model.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Jano
 */
public class MathUtils {
    
    /*
     * A --> B
     * X --> Y
     * 
     * Y = B * X / A
     */
    //private static Long FACTOR_PORCENTAJE = 100L;
    
    public static float calcularPorcentajeRedondeado(Long b, Long a, int cantDecimales){
        Double y = calcularReglaDeTres(b, 100L, a);
        return redondearFloat(y, cantDecimales);
    }
    
    public static double calcularReglaDeTres(Long b, Long x, Long a){
        return (a > 0)? (b.doubleValue() * x.doubleValue()) / a.doubleValue() : 0;
    }
    
    public static BigDecimal redondear(double v, int i){
        BigDecimal big = new BigDecimal(v);
        return big.setScale( i, RoundingMode.HALF_UP );
    }
    
    public static Float redondearFloat(double v, int i){
        return redondear(v, i).floatValue();
    }
    
}
