package co.edu.uco.imexcol.negocio.casouso.validador.producto;

import co.edu.uco.imexcol.negocio.casouso.regla.producto.ProductoExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarProductoExistePorId implements Validador {

    private static final ValidarProductoExistePorId INSTANCIA = new ValidarProductoExistePorId();

    private ValidarProductoExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ProductoExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
