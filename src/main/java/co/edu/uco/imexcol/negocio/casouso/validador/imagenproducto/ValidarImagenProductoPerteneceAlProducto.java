package co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto;

import co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto.ImagenProductoPerteneceAlProductoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarImagenProductoPerteneceAlProducto implements Validador {

    private static final ValidarImagenProductoPerteneceAlProducto INSTANCIA = new ValidarImagenProductoPerteneceAlProducto();

    private ValidarImagenProductoPerteneceAlProducto() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ImagenProductoPerteneceAlProductoRegla.obtenerInstancia().ejecutar(datos);
    }
}
