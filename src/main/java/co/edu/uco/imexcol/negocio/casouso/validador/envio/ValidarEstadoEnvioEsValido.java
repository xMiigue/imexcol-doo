package co.edu.uco.imexcol.negocio.casouso.validador.envio;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEstadoEnvioEsValido implements Validador {

    private static final ValidarEstadoEnvioEsValido INSTANCIA = new ValidarEstadoEnvioEsValido();

    public static final String[] ESTADOS_VALIDOS = {
            "PREPARANDO", "EN_TRANSITO", "ENTREGADO", "DEVUELTO", "EXTRAVIADO"
    };

    private ValidarEstadoEnvioEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = (String) datos[0];
        EstadoEsValidoRegla.ejecutarRegla(estado, "estado del envío", ESTADOS_VALIDOS);
    }
}
