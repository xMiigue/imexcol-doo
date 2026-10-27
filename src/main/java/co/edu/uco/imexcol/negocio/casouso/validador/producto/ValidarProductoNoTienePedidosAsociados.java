package co.edu.uco.imexcol.negocio.casouso.validador.producto;

import co.edu.uco.imexcol.negocio.casouso.regla.producto.ProductoNoTienePedidosAsociadosRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarProductoNoTienePedidosAsociados implements Validador {

    private static final ValidarProductoNoTienePedidosAsociados INSTANCIA = new ValidarProductoNoTienePedidosAsociados();

    private ValidarProductoNoTienePedidosAsociados() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ProductoNoTienePedidosAsociadosRegla.obtenerInstancia().ejecutar(datos);
    }
}
