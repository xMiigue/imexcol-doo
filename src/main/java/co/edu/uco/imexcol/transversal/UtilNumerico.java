package co.edu.uco.imexcol.transversal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class UtilNumerico {

    private static final int DECIMALES_MONEDA = 2;

    private UtilNumerico() {
        super();
    }

    public static int obtenerValorPorDefectoInt() {
        return 0;
    }

    public static int obtenerValorDefecto(final Integer valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerValorPorDefectoInt());
    }

    public static double obtenerValorPorDefectoDouble() {
        return 0.0;
    }

    public static double obtenerValorDefecto(final Double valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerValorPorDefectoDouble());
    }

    public static long obtenerValorPorDefectoLong() {
        return 0L;
    }

    public static long obtenerValorDefecto(final Long valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerValorPorDefectoLong());
    }

    public static double redondearADosDecimales(final double valor) {
        return BigDecimal.valueOf(valor).setScale(DECIMALES_MONEDA, RoundingMode.HALF_UP).doubleValue();
    }

    public static double dividirTruncandoADosDecimales(final double dividendo, final double divisor) {
        return BigDecimal.valueOf(dividendo)
                .divide(BigDecimal.valueOf(divisor), DECIMALES_MONEDA, RoundingMode.DOWN)
                .doubleValue();
    }

    public static boolean sonIgualesConDosDecimales(final double primerValor, final double segundoValor) {
        return BigDecimal.valueOf(redondearADosDecimales(primerValor))
                .compareTo(BigDecimal.valueOf(redondearADosDecimales(segundoValor))) == 0;
    }
}
