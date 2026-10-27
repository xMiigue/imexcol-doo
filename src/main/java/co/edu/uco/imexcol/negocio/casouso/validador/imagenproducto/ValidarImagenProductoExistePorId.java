package co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto;

import co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto.ImagenProductoExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarImagenProductoExistePorId implements Validador {

    private static final ValidarImagenProductoExistePorId INSTANCIA = new ValidarImagenProductoExistePorId();

    private ValidarImagenProductoExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ImagenProductoExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
