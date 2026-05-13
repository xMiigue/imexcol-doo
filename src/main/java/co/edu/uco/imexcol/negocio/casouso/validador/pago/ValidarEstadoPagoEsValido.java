package co.edu.uco.imexcol.negocio.casouso.validador.pago;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEstadoPagoEsValido implements Validador {

    private static final ValidarEstadoPagoEsValido INSTANCIA = new ValidarEstadoPagoEsValido();

    public static final String[] ESTADOS_VALIDOS = {
            "PENDIENTE", "EN_PROCESO", "APROBADO", "RECHAZADO", "REEMBOLSADO"
    };

    private ValidarEstadoPagoEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = (String) datos[0];
        EstadoEsValidoRegla.ejecutarRegla(estado, "estado del pago", ESTADOS_VALIDOS);
    }
}
