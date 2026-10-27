package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoNoTieneLineasRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoNoTieneLineas implements Validador {

    private static final ValidarPedidoNoTieneLineas INSTANCIA = new ValidarPedidoNoTieneLineas();

    private ValidarPedidoNoTieneLineas() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoNoTieneLineasRegla.obtenerInstancia().ejecutar(datos);
    }
}
