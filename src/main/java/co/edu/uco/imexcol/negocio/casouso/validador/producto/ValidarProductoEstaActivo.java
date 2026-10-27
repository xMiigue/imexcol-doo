package co.edu.uco.imexcol.negocio.casouso.validador.producto;

import co.edu.uco.imexcol.negocio.casouso.regla.producto.ProductoEstaActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarProductoEstaActivo implements Validador {

    private static final ValidarProductoEstaActivo INSTANCIA = new ValidarProductoEstaActivo();

    private ValidarProductoEstaActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ProductoEstaActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
