package co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto;

import co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto.ExtensionFotoPermitidaRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarExtensionFotoPermitida implements Validador {

    private static final ValidarExtensionFotoPermitida INSTANCIA = new ValidarExtensionFotoPermitida();

    private ValidarExtensionFotoPermitida() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ExtensionFotoPermitidaRegla.obtenerInstancia().ejecutar(datos);
    }
}
