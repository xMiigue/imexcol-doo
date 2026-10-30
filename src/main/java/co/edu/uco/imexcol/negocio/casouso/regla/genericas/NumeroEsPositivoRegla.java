package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NumeroEsPositivoRegla implements Regla {

    private static final NumeroEsPositivoRegla INSTANCIA = new NumeroEsPositivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_VALOR = 0;
    private static final int POSICION_NOMBRE_DATO = 1;

    private NumeroEsPositivoRegla() {
        super();
    }

    public static NumeroEsPositivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var valor = UtilNumerico.convertirADouble((Number) datos[POSICION_VALOR]);
        final var nombreDato = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_DATO]);

        if (UtilNumerico.esNegativo(valor)) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_NUMERO_NEGATIVO.getContenido()
                    .formatted(nombreDato);
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_NUMERO_NEGATIVO.getContenido()
                    .formatted(nombreDato);
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
