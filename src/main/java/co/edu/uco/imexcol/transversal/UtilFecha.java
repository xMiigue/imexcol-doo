package co.edu.uco.imexcol.transversal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public final class UtilFecha {

    private static final int ANIO_POR_DEFECTO = 1;
    private static final int MES_POR_DEFECTO = 1;
    private static final int DIA_POR_DEFECTO = 1;
    private static final ZoneId ZONA_HORARIA = ZoneId.systemDefault();

    private UtilFecha() {
        super();
    }

    public static LocalDate obtenerFechaPorDefecto() {
        return LocalDate.of(ANIO_POR_DEFECTO, MES_POR_DEFECTO, DIA_POR_DEFECTO);
    }

    public static LocalDate obtenerValorDefecto(final LocalDate valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerFechaPorDefecto());
    }

    public static LocalDate obtenerFechaActual() {
        return LocalDate.now(ZONA_HORARIA);
    }

    public static boolean esFechaPorDefecto(final LocalDate fecha) {
        return sonFechasIguales(fecha, obtenerFechaPorDefecto());
    }

    public static boolean sonFechasIguales(final LocalDate valor, final LocalDate referencia) {
        return obtenerValorDefecto(valor).isEqual(obtenerValorDefecto(referencia));
    }

    public static boolean esFechaAnteriorA(final LocalDate valor, final LocalDate referencia) {
        return obtenerValorDefecto(valor).isBefore(obtenerValorDefecto(referencia));
    }

    public static boolean esFechaPosteriorA(final LocalDate valor, final LocalDate referencia) {
        return obtenerValorDefecto(valor).isAfter(obtenerValorDefecto(referencia));
    }

    public static boolean esFechaAnteriorAHoy(final LocalDate valor) {
        return esFechaAnteriorA(valor, obtenerFechaActual());
    }

    public static boolean esFechaPosteriorAHoy(final LocalDate valor) {
        return esFechaPosteriorA(valor, obtenerFechaActual());
    }

    public static boolean esFechaPosteriorOIgualAHoy(final LocalDate valor) {
        return !esFechaAnteriorAHoy(valor);
    }

    public static LocalDateTime obtenerFechaHoraPorDefecto() {
        return obtenerFechaPorDefecto().atStartOfDay();
    }

    public static LocalDateTime obtenerValorDefecto(final LocalDateTime valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerFechaHoraPorDefecto());
    }

    public static LocalDateTime obtenerFechaHoraActual() {
        return truncarASegundos(LocalDateTime.now(ZONA_HORARIA));
    }

    public static LocalDateTime truncarASegundos(final LocalDateTime valor) {
        return obtenerValorDefecto(valor).truncatedTo(ChronoUnit.SECONDS);
    }

    public static boolean esFechaHoraPorDefecto(final LocalDateTime valor) {
        return sonFechasHoraIguales(valor, obtenerFechaHoraPorDefecto());
    }

    public static boolean sonFechasHoraIguales(final LocalDateTime valor, final LocalDateTime referencia) {
        return obtenerValorDefecto(valor).isEqual(obtenerValorDefecto(referencia));
    }

    public static boolean esFechaHoraAnteriorA(final LocalDateTime valor, final LocalDate referencia) {
        return esFechaAnteriorA(obtenerValorDefecto(valor).toLocalDate(), referencia);
    }
}
