package co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoEventoRastreoEnum;

public final class ValidarEstadoEventoRastreoEsValido implements Validador {

    private static final ValidarEstadoEventoRastreoEsValido INSTANCIA = new ValidarEstadoEventoRastreoEsValido();
    private static final String NOMBRE_DATO = "estado logístico del evento de rastreo";
    private static final int POSICION_ESTADO = 0;

    private ValidarEstadoEventoRastreoEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = (String) datos[POSICION_ESTADO];
        EstadoEsValidoRegla.obtenerInstancia().ejecutar(estado, NOMBRE_DATO,
                EstadoEventoRastreoEnum.obtenerValoresPermitidos());
    }
}
