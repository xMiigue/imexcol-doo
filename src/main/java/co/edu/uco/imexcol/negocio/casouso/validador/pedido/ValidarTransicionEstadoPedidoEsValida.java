package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.TransicionEstadoPedidoEsValidaRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarTransicionEstadoPedidoEsValida implements Validador {

    private static final ValidarTransicionEstadoPedidoEsValida INSTANCIA =
            new ValidarTransicionEstadoPedidoEsValida();

    private ValidarTransicionEstadoPedidoEsValida() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        TransicionEstadoPedidoEsValidaRegla.obtenerInstancia().ejecutar(datos);
    }
}
