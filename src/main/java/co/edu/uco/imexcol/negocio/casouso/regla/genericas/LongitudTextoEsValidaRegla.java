package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class LongitudTextoEsValidaRegla implements Regla {

    private static final LongitudTextoEsValidaRegla INSTANCIA = new LongitudTextoEsValidaRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 4;
    private static final int POSICION_TEXTO = 0;
    private static final int POSICION_NOMBRE_DATO = 1;
    private static final int POSICION_LONGITUD_MINIMA = 2;
    private static final int POSICION_LONGITUD_MAXIMA = 3;

    private LongitudTextoEsValidaRegla() {
        super();
    }

    public static LongitudTextoEsValidaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var texto = UtilTexto.obtenerValorDefecto((String) datos[POSICION_TEXTO]);
        final var nombreDato = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_DATO]);
        final var longitudMinima = UtilNumerico.obtenerValorDefecto((Integer) datos[POSICION_LONGITUD_MINIMA]);
        final var longitudMaxima = UtilNumerico.obtenerValorDefecto((Integer) datos[POSICION_LONGITUD_MAXIMA]);

        if (!UtilTexto.longitudEsValida(texto, longitudMinima, longitudMaxima)) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_TEXTO_LONGITUD_INVALIDA.getContenido()
                    .formatted(nombreDato, String.valueOf(longitudMinima), String.valueOf(longitudMaxima));
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_TEXTO_LONGITUD_INVALIDA.getContenido()
                    .formatted(nombreDato, String.valueOf(longitudMinima), String.valueOf(longitudMaxima));
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
