package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.IdNoEsValorPorDefectoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarIdNoEsValorPorDefecto implements Validador {

    private static final ValidarIdNoEsValorPorDefecto INSTANCIA = new ValidarIdNoEsValorPorDefecto();

    private ValidarIdNoEsValorPorDefecto() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        IdNoEsValorPorDefectoRegla.ejecutarRegla(datos);
    }
}
