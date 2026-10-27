package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.carritocompras.CarritoComprasExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCarritoComprasExistePorId implements Validador {

    private static final ValidarCarritoComprasExistePorId INSTANCIA = new ValidarCarritoComprasExistePorId();

    private ValidarCarritoComprasExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CarritoComprasExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
