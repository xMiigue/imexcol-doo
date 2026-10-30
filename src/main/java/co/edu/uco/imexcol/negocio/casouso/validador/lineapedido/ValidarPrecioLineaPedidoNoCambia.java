package co.edu.uco.imexcol.negocio.casouso.validador.lineapedido;

import co.edu.uco.imexcol.negocio.casouso.regla.lineapedido.PrecioLineaPedidoNoCambiaRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPrecioLineaPedidoNoCambia implements Validador {

    private static final ValidarPrecioLineaPedidoNoCambia INSTANCIA = new ValidarPrecioLineaPedidoNoCambia();

    private ValidarPrecioLineaPedidoNoCambia() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PrecioLineaPedidoNoCambiaRegla.obtenerInstancia().ejecutar(datos);
    }
}
