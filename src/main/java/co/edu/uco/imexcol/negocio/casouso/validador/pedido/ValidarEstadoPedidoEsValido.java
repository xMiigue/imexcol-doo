package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPedidoEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;

public final class ValidarEstadoPedidoEsValido implements Validador {

    private static final ValidarEstadoPedidoEsValido INSTANCIA = new ValidarEstadoPedidoEsValido();
    private static final String NOMBRE_DATO = "estado del pedido";
    private static final int POSICION_ESTADO = 0;

    private ValidarEstadoPedidoEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = UtilTexto.aplicarTrim((String) datos[POSICION_ESTADO]);
        EstadoEsValidoRegla.obtenerInstancia().ejecutar(estado, NOMBRE_DATO,
                EstadoPedidoEnum.obtenerValoresPermitidos());
    }
}
