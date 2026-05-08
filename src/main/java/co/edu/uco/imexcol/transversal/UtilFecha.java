package co.edu.uco.imexcol.transversal;

import java.time.LocalDate;

public final class UtilFecha {

    private UtilFecha() {
        super();
    }

    public static LocalDate obtenerFechaPorDefecto() {
        return LocalDate.of(1, 1, 1);
    }

    public static LocalDate obtenerValorDefecto(final LocalDate valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerFechaPorDefecto());
    }

    public static LocalDate obtenerFechaActual() {
        return LocalDate.now();
    }

    public static boolean esFechaPorDefecto(final LocalDate fecha) {
        return obtenerFechaPorDefecto().equals(fecha);
    }

    public static boolean esFechaPosteriorOIgualAHoy(final LocalDate valor) {
        return valor.isAfter(LocalDate.now()) || valor.isEqual(LocalDate.now());
    }

    public static boolean esFechaAnteriorA(final LocalDate valor, final LocalDate referencia) {
        return valor.isBefore(referencia);
    }
}
