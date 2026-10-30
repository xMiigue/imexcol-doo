package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoNoTienePagosRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoNoTienePagos implements Validador {

    private static final ValidarPedidoNoTienePagos INSTANCIA = new ValidarPedidoNoTienePagos();

    private ValidarPedidoNoTienePagos() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoNoTienePagosRegla.obtenerInstancia().ejecutar(datos);
    }
}
