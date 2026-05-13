package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.FechaNoEsValorPorDefectoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarFechaNoEsValorPorDefecto implements Validador {

    private static final ValidarFechaNoEsValorPorDefecto INSTANCIA = new ValidarFechaNoEsValorPorDefecto();

    private ValidarFechaNoEsValorPorDefecto() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        FechaNoEsValorPorDefectoRegla.ejecutarRegla(datos);
    }
}
