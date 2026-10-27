package co.edu.uco.imexcol.negocio.casouso.validador.categoria;

import co.edu.uco.imexcol.negocio.casouso.regla.categoria.CategoriaNoTieneProductosRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCategoriaNoTieneProductos implements Validador {

    private static final ValidarCategoriaNoTieneProductos INSTANCIA = new ValidarCategoriaNoTieneProductos();

    private ValidarCategoriaNoTieneProductos() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CategoriaNoTieneProductosRegla.obtenerInstancia().ejecutar(datos);
    }
}
