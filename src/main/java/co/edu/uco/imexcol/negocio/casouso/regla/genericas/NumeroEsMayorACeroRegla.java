package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NumeroEsMayorACeroRegla implements Regla {

    private static final NumeroEsMayorACeroRegla INSTANCIA = new NumeroEsMayorACeroRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;

    private NumeroEsMayorACeroRegla() {
        super();
    }

    public static NumeroEsMayorACeroRegla obtenerInstancia() {
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

        if (valor <= 0) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_NUMERO_NO_MAYOR_A_CERO.getContenido()
                    .formatted(nombreDato);
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_NUMERO_NO_MAYOR_A_CERO.getContenido()
                    .formatted(nombreDato);
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
