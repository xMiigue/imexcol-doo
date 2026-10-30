package co.edu.uco.imexcol.negocio.casouso.validador.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.cliente.ClienteComproProductoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarClienteComproProducto implements Validador {

    private static final ValidarClienteComproProducto INSTANCIA = new ValidarClienteComproProducto();

    private ValidarClienteComproProducto() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ClienteComproProductoRegla.obtenerInstancia().ejecutar(datos);
    }
}
