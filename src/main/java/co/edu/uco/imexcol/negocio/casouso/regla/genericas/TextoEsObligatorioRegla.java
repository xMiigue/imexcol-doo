package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class TextoEsObligatorioRegla implements Regla {

    private static final TextoEsObligatorioRegla INSTANCIA = new TextoEsObligatorioRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_TEXTO = 0;
    private static final int POSICION_NOMBRE_DATO = 1;

    private TextoEsObligatorioRegla() {
        super();
    }

    public static TextoEsObligatorioRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var texto = UtilTexto.obtenerValorDefecto((String) datos[POSICION_TEXTO]);
        final var nombreDato = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_DATO]);

        if (UtilTexto.estaVacia(texto)) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_TEXTO_REQUERIDO.getContenido()
                    .formatted(nombreDato);
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_TEXTO_REQUERIDO.getContenido()
                    .formatted(nombreDato);
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
