package co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEstadoEventoRastreoEsValido implements Validador {

    private static final ValidarEstadoEventoRastreoEsValido INSTANCIA = new ValidarEstadoEventoRastreoEsValido();

    public static final String[] ESTADOS_VALIDOS = {
            "REGISTRADO", "EN_TRANSITO", "EN_ADUANA", "EN_REPARTO", "ENTREGADO", "INCIDENCIA"
    };

    private ValidarEstadoEventoRastreoEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = (String) datos[0];
        EstadoEsValidoRegla.ejecutarRegla(estado, "estado del evento de rastreo", ESTADOS_VALIDOS);
    }
}
