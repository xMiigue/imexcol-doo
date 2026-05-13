package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class NumeroEsPositivoRegla implements Regla {

    private static final NumeroEsPositivoRegla INSTANCIA = new NumeroEsPositivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;

    private NumeroEsPositivoRegla() {
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

        final var valor = ((Number) datos[0]).doubleValue();
        final var nombreDato = (String) datos[1];

        if (valor < 0) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_NUMERO_NEGATIVO.getContenido()
                    .formatted(nombreDato);
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_NUMERO_NEGATIVO.getContenido()
                    .formatted(nombreDato);
            throw ImexcolException.crear(mensajeUsuario, mensajeTecnico, Lugar.NEGOCIO);
        }
    }
}
