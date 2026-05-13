package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.NumeroEnRangoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarNumeroEnRango implements Validador {

    private static final ValidarNumeroEnRango INSTANCIA = new ValidarNumeroEnRango();

    private ValidarNumeroEnRango() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        NumeroEnRangoRegla.ejecutarRegla(datos);
    }
}
