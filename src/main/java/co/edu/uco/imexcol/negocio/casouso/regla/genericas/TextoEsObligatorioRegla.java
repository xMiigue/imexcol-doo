package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class TextoEsObligatorioRegla implements Regla {

    private static final TextoEsObligatorioRegla INSTANCIA = new TextoEsObligatorioRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;

    private TextoEsObligatorioRegla() {
        super();
    }

    public static void ejecutarRegla(final Object... datos) {
        INSTANCIA.ejecutar(datos);
    }

    @Override
    public void ejecutar(final Object... datos) {
        if (UtilObjeto.esNulo(datos)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_DATOS_NULOS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_DATOS_NULOS.getContenido(),
                    Lugar.NEGOCIO);
        }
        if (datos.length < CANTIDAD_PARAMETROS_REQUERIDOS) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_PARAMETROS_INSUFICIENTES.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_PARAMETROS_INSUFICIENTES.getContenido(),
                    Lugar.NEGOCIO);
        }

        final var texto = (String) datos[0];
        final var nombreDato = (String) datos[1];

        if (UtilTexto.estaVacia(texto)) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_TEXTO_REQUERIDO.getContenido()
                    .formatted(nombreDato);
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_TEXTO_REQUERIDO.getContenido()
                    .formatted(nombreDato);
            throw ImexcolException.crear(mensajeUsuario, mensajeTecnico, Lugar.NEGOCIO);
        }
    }
}
