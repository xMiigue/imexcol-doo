package co.edu.uco.imexcol.negocio.casouso.validador.envio;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoEnvioEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;

public final class ValidarEstadoEnvioEsValido implements Validador {

    private static final ValidarEstadoEnvioEsValido INSTANCIA = new ValidarEstadoEnvioEsValido();
    private static final String NOMBRE_DATO = "estado del envío";
    private static final int POSICION_ESTADO = 0;

    private ValidarEstadoEnvioEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = UtilTexto.aplicarTrim((String) datos[POSICION_ESTADO]);
        EstadoEsValidoRegla.obtenerInstancia().ejecutar(estado, NOMBRE_DATO,
                EstadoEnvioEnum.obtenerValoresPermitidos());
    }
}
