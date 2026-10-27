package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.carritocompras.CarritoComprasPerteneceAlClienteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCarritoComprasPerteneceAlCliente implements Validador {

    private static final ValidarCarritoComprasPerteneceAlCliente INSTANCIA =
            new ValidarCarritoComprasPerteneceAlCliente();

    private ValidarCarritoComprasPerteneceAlCliente() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CarritoComprasPerteneceAlClienteRegla.obtenerInstancia().ejecutar(datos);
    }
}
