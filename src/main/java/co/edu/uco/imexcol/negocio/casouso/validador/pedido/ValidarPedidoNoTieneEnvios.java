package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoNoTieneEnviosRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoNoTieneEnvios implements Validador {

    private static final ValidarPedidoNoTieneEnvios INSTANCIA = new ValidarPedidoNoTieneEnvios();

    private ValidarPedidoNoTieneEnvios() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoNoTieneEnviosRegla.obtenerInstancia().ejecutar(datos);
    }
}
