package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.carritocompras.CarritoComprasEstaActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCarritoComprasEstaActivo implements Validador {

    private static final ValidarCarritoComprasEstaActivo INSTANCIA = new ValidarCarritoComprasEstaActivo();

    private ValidarCarritoComprasEstaActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CarritoComprasEstaActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
