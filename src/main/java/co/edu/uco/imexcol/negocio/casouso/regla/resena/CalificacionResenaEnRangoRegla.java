package co.edu.uco.imexcol.negocio.casouso.regla.resena;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CalificacionResenaEnRangoRegla implements Regla {

    private static final CalificacionResenaEnRangoRegla INSTANCIA = new CalificacionResenaEnRangoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 1;
    private static final int POSICION_CALIFICACION = 0;
    private static final int CALIFICACION_MINIMA = 1;
    private static final int CALIFICACION_MAXIMA = 5;

    private CalificacionResenaEnRangoRegla() {
        super();
    }

    public static CalificacionResenaEnRangoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var calificacion = ((Number) datos[POSICION_CALIFICACION]).intValue();

        if (calificacion < CALIFICACION_MINIMA || calificacion > CALIFICACION_MAXIMA) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_RESENA_CALIFICACION_FUERA_DE_RANGO.getContenido()
                            .formatted(CALIFICACION_MINIMA, CALIFICACION_MAXIMA),
                    MensajesEnum.ERROR_TECNICO_RESENA_CALIFICACION_FUERA_DE_RANGO.getContenido()
                            .formatted(calificacion, CALIFICACION_MINIMA, CALIFICACION_MAXIMA));
        }
    }
}
