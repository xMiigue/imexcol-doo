package co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto;

import co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto.FotoImagenProductoNoExisteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarFotoImagenProductoNoExiste implements Validador {

    private static final ValidarFotoImagenProductoNoExiste INSTANCIA = new ValidarFotoImagenProductoNoExiste();

    private ValidarFotoImagenProductoNoExiste() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        FotoImagenProductoNoExisteRegla.obtenerInstancia().ejecutar(datos);
    }
}
