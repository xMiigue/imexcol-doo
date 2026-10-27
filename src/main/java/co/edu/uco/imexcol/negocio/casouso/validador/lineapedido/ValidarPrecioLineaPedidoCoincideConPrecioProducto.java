package co.edu.uco.imexcol.negocio.casouso.validador.lineapedido;

import co.edu.uco.imexcol.negocio.casouso.regla.lineapedido.PrecioLineaPedidoCoincideConPrecioProductoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPrecioLineaPedidoCoincideConPrecioProducto implements Validador {

    private static final ValidarPrecioLineaPedidoCoincideConPrecioProducto INSTANCIA =
            new ValidarPrecioLineaPedidoCoincideConPrecioProducto();

    private ValidarPrecioLineaPedidoCoincideConPrecioProducto() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PrecioLineaPedidoCoincideConPrecioProductoRegla.obtenerInstancia().ejecutar(datos);
    }
}
