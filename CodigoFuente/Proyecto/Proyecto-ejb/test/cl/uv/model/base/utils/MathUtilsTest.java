/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.model.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro
 */
public class MathUtilsTest {

    /**
     * Test of calcularPorcentajeRedondeado method, of class MathUtils.
     */
    @Test
    public void testCalcularPorcentajeRedondeado() {
        System.out.println("calcularPorcentajeRedondeado");
        Long b = 50L;
        Long a = 100L;
        int cantDecimales = 0;
        float expResult = 50F;
        float result = MathUtils.calcularPorcentajeRedondeado(b, a, cantDecimales);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCalcularPorcentajeRedondeadoParaArraiba() {
        System.out.println("calcularPorcentajeRedondeadoParaArraiba");
        Long b = 55L;
        Long a = 99L;
        int cantDecimales = 1;
        float expResult = 55.6F;
        float result = MathUtils.calcularPorcentajeRedondeado(b, a, cantDecimales);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCalcularPorcentajeRedondeadoParaAbajo() {
        System.out.println("calcularPorcentajeRedondeadoParaAbajo");
        Long b = 55L;
        Long a = 98L;
        int cantDecimales = 1;
        float expResult = 56.1F;
        float result = MathUtils.calcularPorcentajeRedondeado(b, a, cantDecimales);
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of calcularReglaDeTres method, of class MathUtils.
     */
    @Test
    public void testCalcularReglaDeTres() {
        System.out.println("calcularReglaDeTres");
        Long b = 10L;
        Long x = 100L;
        Long a = 100L;
        double expResult = 10.0;
        double result = MathUtils.calcularReglaDeTres(b, x, a);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCalcularReglaDeTresParaDenominadorCero() {
        System.out.println("calcularReglaDeTresParaDenominadorCero");
        Long b = 10L;
        Long x = 100L;
        Long a = 0L;
        double expResult = 0.0;
        double result = MathUtils.calcularReglaDeTres(b, x, a);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCalcularReglaDeTresParaNumeradorCero() {
        System.out.println("calcularReglaDeTresNumeradorCero");
        Long b = 0L;
        Long x = 100L;
        Long a = 100L;
        double expResult = 0.0;
        double result = MathUtils.calcularReglaDeTres(b, x, a);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCalcularReglaDeTresParaCien() {
        System.out.println("calcularReglaDeTresParaCien");
        Long b = 100L;
        Long x = 100L;
        Long a = 100L;
        double expResult = 100.0;
        double result = MathUtils.calcularReglaDeTres(b, x, a);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCalcularReglaDeTresParaPorcentajeMayorACien() {
        System.out.println("calcularReglaDeTresParaPorcentajeMayorACien");
        Long b = 200L;
        Long x = 100L;
        Long a = 100L;
        double expResult = 200.0;
        double result = MathUtils.calcularReglaDeTres(b, x, a);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of redondear method, of class MathUtils.
     */
    @Test
    public void testRedondearCero() {
        System.out.println("redondearCero");
        double v = 0;
        int i = 0;
        double bg = 0;
        BigDecimal expResult = new BigDecimal(bg);
        BigDecimal result = MathUtils.redondear(v, i);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRedondearParaArribaSinDecimal() {
        System.out.println("redondearParaArribaSinDecimal");
        double v = 10.9;
        int i = 0;
        double bg = 11;
        BigDecimal expResult = new BigDecimal(bg);
        BigDecimal result = MathUtils.redondear(v, i);
        assertEquals(expResult, result);
    }

    @Test
    public void testRedondearParaAbajoSinDecimal() {
        System.out.println("redondearParaAbajoSinDecimal");
        double v = 10.1;
        int i = 0;
        double bg = 10;
        BigDecimal expResult = new BigDecimal(bg);
        BigDecimal result = MathUtils.redondear(v, i);
        assertEquals(expResult, result);
    }

    @Test
    public void testRedondearParaArribaConUnDecimal() {
        System.out.println("redondearParaArribaConUnDecimal");
        double v = 10.19;
        int i = 1;
        double bg = 10.2;
        BigDecimal expResult = (new BigDecimal(bg)).setScale(i, RoundingMode.HALF_UP);
        BigDecimal result = MathUtils.redondear(v, i);
        assertEquals(expResult, result);
    }

    @Test
    public void testRedondearParaAbajoConUnDecimal() {
        System.out.println("redondearParaAbajoConUnDecimal");
        double v = 10.14;
        int i = 1;
        double bg = 10.1;
        BigDecimal expResult = (new BigDecimal(bg)).setScale(i, RoundingMode.HALF_UP);
        BigDecimal result = MathUtils.redondear(v, i);
        assertEquals(expResult, result);
    }

    @Test
    public void testRedondearParaArribaConDecimal5() {
        System.out.println("redondearParaAbajoConUnDecimal");
        double v = 10.15;
        int i = 1;
        double bg = 10.2;
        BigDecimal expResult = (new BigDecimal(bg)).setScale(i, RoundingMode.HALF_UP);
        BigDecimal result = MathUtils.redondear(v, i);
        assertEquals(expResult, result);
    }

    /**
     * Test of redondearFloat method, of class MathUtils.
     */
    @Test
    public void testRedondearFloatCero() {
        System.out.println("redondearFloatCero");
        double v = 0.0;
        int i = 0;
        Float expResult = 0F;
        Float result = MathUtils.redondearFloat(v, i);
        assertEquals(expResult, result);

    }

    @Test
    public void testRedondearFloatParaArribaSinDecimal() {
        System.out.println("redondearFloatParaArribaSinDecimal");
        double v = 10.9;
        int i = 0;
        Float expResult = 11F;
        Float result = MathUtils.redondearFloat(v, i);
        assertEquals(expResult, result);

    }

    @Test
    public void testRedondearFloatParaAbajoSinDecimal() {
        System.out.println("redondearFloatParaAbajoSinDecimal");
        double v = 10.1;
        int i = 0;
        Float expResult = 10F;
        Float result = MathUtils.redondearFloat(v, i);
        assertEquals(expResult, result);
    }

    @Test
    public void testRedondearFloatParaArribaConUnDecimal() {
        System.out.println("redondearFloatParaArribaConUnDecimal");
        double v = 10.19;
        int i = 1;
        Float expResult = 10.2F;
        Float result = MathUtils.redondearFloat(v, i);
        assertEquals(expResult, result);
    }

    @Test
    public void testRedondearFloatParaAbajoConUnDecimal() {
        System.out.println("redondearFloatParaAbajoConUnDecimal");
        double v = 10.14;
        int i = 1;
        Float expResult = 10.1F;
        Float result = MathUtils.redondearFloat(v, i);
        assertEquals(expResult, result);
    }

    @Test
    public void testRedondearFloatParaArribaConDecimal5() {
        System.out.println("redondearFloatParaAbajoConUnDecimal");
        double v = 10.15;
        int i = 1;
        Float expResult = 10.2F;
        Float result = MathUtils.redondearFloat(v, i);
        assertEquals(expResult, result);
    }
}
