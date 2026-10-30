package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoEstaPagadoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoEstaPagado implements Validador {

    private static final ValidarPedidoEstaPagado INSTANCIA = new ValidarPedidoEstaPagado();

    private ValidarPedidoEstaPagado() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoEstaPagadoRegla.obtenerInstancia().ejecutar(datos);
    }
}
