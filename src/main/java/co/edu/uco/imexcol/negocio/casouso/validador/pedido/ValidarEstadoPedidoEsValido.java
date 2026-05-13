package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEstadoPedidoEsValido implements Validador {

    private static final ValidarEstadoPedidoEsValido INSTANCIA = new ValidarEstadoPedidoEsValido();

    public static final String[] ESTADOS_VALIDOS = {
            "PENDIENTE", "CONFIRMADO", "PREPARANDO", "ENVIADO", "ENTREGADO", "CANCELADO"
    };

    private ValidarEstadoPedidoEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = (String) datos[0];
        EstadoEsValidoRegla.ejecutarRegla(estado, "estado del pedido", ESTADOS_VALIDOS);
    }
}
