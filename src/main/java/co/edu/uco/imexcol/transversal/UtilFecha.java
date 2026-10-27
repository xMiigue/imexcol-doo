package co.edu.uco.imexcol.transversal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public static LocalDateTime obtenerFechaHoraPorDefecto() {
        return obtenerFechaPorDefecto().atStartOfDay();
    }

    public static LocalDateTime obtenerValorDefecto(final LocalDateTime valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerFechaHoraPorDefecto());
    }

    public static LocalDateTime obtenerFechaHoraActual() {
        return truncarASegundos(LocalDateTime.now());
    }

    public static LocalDateTime truncarASegundos(final LocalDateTime valor) {
        return obtenerValorDefecto(valor).truncatedTo(ChronoUnit.SECONDS);
    }

    public static boolean esFechaHoraPorDefecto(final LocalDateTime valor) {
        return obtenerFechaHoraPorDefecto().equals(valor);
    }

    public static boolean esFechaHoraAnteriorA(final LocalDateTime valor, final LocalDate referencia) {
        return esFechaAnteriorA(obtenerValorDefecto(valor).toLocalDate(), obtenerValorDefecto(referencia));
    }
}
