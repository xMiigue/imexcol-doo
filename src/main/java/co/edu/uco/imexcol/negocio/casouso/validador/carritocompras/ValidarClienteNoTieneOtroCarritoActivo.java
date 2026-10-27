package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.carritocompras.ClienteNoTieneOtroCarritoActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarClienteNoTieneOtroCarritoActivo implements Validador {

    private static final ValidarClienteNoTieneOtroCarritoActivo INSTANCIA =
            new ValidarClienteNoTieneOtroCarritoActivo();

    private ValidarClienteNoTieneOtroCarritoActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ClienteNoTieneOtroCarritoActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
