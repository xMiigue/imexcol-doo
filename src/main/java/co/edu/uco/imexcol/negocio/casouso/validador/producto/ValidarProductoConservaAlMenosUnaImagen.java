package co.edu.uco.imexcol.negocio.casouso.validador.producto;

import co.edu.uco.imexcol.negocio.casouso.regla.producto.ProductoConservaAlMenosUnaImagenRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarProductoConservaAlMenosUnaImagen implements Validador {

    private static final ValidarProductoConservaAlMenosUnaImagen INSTANCIA = new ValidarProductoConservaAlMenosUnaImagen();

    private ValidarProductoConservaAlMenosUnaImagen() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ProductoConservaAlMenosUnaImagenRegla.obtenerInstancia().ejecutar(datos);
    }
}
