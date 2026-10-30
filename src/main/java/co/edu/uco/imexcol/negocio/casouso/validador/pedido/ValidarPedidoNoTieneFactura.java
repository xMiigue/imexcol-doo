package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoNoTieneFacturaRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoNoTieneFactura implements Validador {

    private static final ValidarPedidoNoTieneFactura INSTANCIA = new ValidarPedidoNoTieneFactura();

    private ValidarPedidoNoTieneFactura() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoNoTieneFacturaRegla.obtenerInstancia().ejecutar(datos);
    }
}
