package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.LongitudTextoEsValidaRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarLongitudTextoEsValida implements Validador {

    private static final ValidarLongitudTextoEsValida INSTANCIA = new ValidarLongitudTextoEsValida();

    private ValidarLongitudTextoEsValida() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        LongitudTextoEsValidaRegla.ejecutarRegla(datos);
    }
}
