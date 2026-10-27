package co.edu.uco.imexcol.negocio.casouso.validador.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.cliente.ClienteNoHaResenadoProductoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarClienteNoHaResenadoProducto implements Validador {

    private static final ValidarClienteNoHaResenadoProducto INSTANCIA = new ValidarClienteNoHaResenadoProducto();

    private ValidarClienteNoHaResenadoProducto() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ClienteNoHaResenadoProductoRegla.obtenerInstancia().ejecutar(datos);
    }
}
