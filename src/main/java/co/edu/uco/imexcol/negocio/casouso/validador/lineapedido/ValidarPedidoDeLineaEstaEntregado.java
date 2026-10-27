package co.edu.uco.imexcol.negocio.casouso.validador.lineapedido;

import co.edu.uco.imexcol.negocio.casouso.regla.lineapedido.PedidoDeLineaEstaEntregadoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoDeLineaEstaEntregado implements Validador {

    private static final ValidarPedidoDeLineaEstaEntregado INSTANCIA = new ValidarPedidoDeLineaEstaEntregado();

    private ValidarPedidoDeLineaEstaEntregado() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoDeLineaEstaEntregadoRegla.obtenerInstancia().ejecutar(datos);
    }
}
