package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NumeroEnRangoRegla implements Regla {

    private static final NumeroEnRangoRegla INSTANCIA = new NumeroEnRangoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 4;
    private static final int POSICION_VALOR = 0;
    private static final int POSICION_NOMBRE_DATO = 1;
    private static final int POSICION_MINIMO = 2;
    private static final int POSICION_MAXIMO = 3;

    private NumeroEnRangoRegla() {
        super();
    }

    public static NumeroEnRangoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var valor = UtilNumerico.convertirADouble((Number) datos[POSICION_VALOR]);
        final var nombreDato = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_DATO]);
        final var minimo = UtilNumerico.convertirADouble((Number) datos[POSICION_MINIMO]);
        final var maximo = UtilNumerico.convertirADouble((Number) datos[POSICION_MAXIMO]);

        if (!UtilNumerico.estaEnRango(valor, minimo, maximo)) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_NUMERO_FUERA_DE_RANGO.getContenido()
                    .formatted(nombreDato, String.valueOf(minimo), String.valueOf(maximo));
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_NUMERO_FUERA_DE_RANGO.getContenido()
                    .formatted(nombreDato, String.valueOf(minimo), String.valueOf(maximo));
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
