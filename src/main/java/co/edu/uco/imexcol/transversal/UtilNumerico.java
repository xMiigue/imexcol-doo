package co.edu.uco.imexcol.transversal;

public final class UtilNumerico {

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
}
