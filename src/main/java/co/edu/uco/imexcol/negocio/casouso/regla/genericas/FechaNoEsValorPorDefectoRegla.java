package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import java.time.LocalDate;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class FechaNoEsValorPorDefectoRegla implements Regla {

    private static final FechaNoEsValorPorDefectoRegla INSTANCIA = new FechaNoEsValorPorDefectoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_FECHA = 0;
    private static final int POSICION_NOMBRE_DATO = 1;

    private FechaNoEsValorPorDefectoRegla() {
        super();
    }

    public static FechaNoEsValorPorDefectoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var fecha = (LocalDate) datos[POSICION_FECHA];
        final var nombreDato = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_DATO]);

        if (UtilFecha.esFechaPorDefecto(fecha)) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_FECHA_REQUERIDA.getContenido()
                    .formatted(nombreDato);
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_FECHA_REQUERIDA.getContenido()
                    .formatted(nombreDato);
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
