package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.TextoEsObligatorioRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarTextoEsObligatorio implements Validador {

    private static final ValidarTextoEsObligatorio INSTANCIA = new ValidarTextoEsObligatorio();

    private ValidarTextoEsObligatorio() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        TextoEsObligatorioRegla.ejecutarRegla(datos);
    }
}
