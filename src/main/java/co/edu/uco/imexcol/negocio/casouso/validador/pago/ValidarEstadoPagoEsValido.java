package co.edu.uco.imexcol.negocio.casouso.validador.pago;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPagoEnum;

public final class ValidarEstadoPagoEsValido implements Validador {

    private static final ValidarEstadoPagoEsValido INSTANCIA = new ValidarEstadoPagoEsValido();
    private static final String NOMBRE_DATO = "estado del pago";
    private static final int POSICION_ESTADO = 0;

    private ValidarEstadoPagoEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = (String) datos[POSICION_ESTADO];
        EstadoEsValidoRegla.obtenerInstancia().ejecutar(estado, NOMBRE_DATO,
                EstadoPagoEnum.obtenerValoresPermitidos());
    }
}
