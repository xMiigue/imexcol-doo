package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NumeroEnRangoRegla implements Regla {

    private static final NumeroEnRangoRegla INSTANCIA = new NumeroEnRangoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 4;

    private NumeroEnRangoRegla() {
        super();
    }

    public static NumeroEnRangoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        if (UtilObjeto.esNulo(datos)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_DATOS_NULOS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_DATOS_NULOS.getContenido());
        }
        if (datos.length < CANTIDAD_PARAMETROS_REQUERIDOS) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_PARAMETROS_INSUFICIENTES.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_PARAMETROS_INSUFICIENTES.getContenido());
        }

        final var valor = ((Number) datos[0]).doubleValue();
        final var nombreDato = (String) datos[1];
        final var minimo = ((Number) datos[2]).doubleValue();
        final var maximo = ((Number) datos[3]).doubleValue();

        if (valor < minimo || valor > maximo) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_NUMERO_FUERA_DE_RANGO.getContenido()
                    .formatted(nombreDato, String.valueOf(minimo), String.valueOf(maximo));
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_NUMERO_FUERA_DE_RANGO.getContenido()
                    .formatted(nombreDato, String.valueOf(minimo), String.valueOf(maximo));
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
