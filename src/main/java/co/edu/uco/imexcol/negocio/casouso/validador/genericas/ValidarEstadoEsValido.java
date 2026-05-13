package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEstadoEsValido implements Validador {

    private static final ValidarEstadoEsValido INSTANCIA = new ValidarEstadoEsValido();

    private ValidarEstadoEsValido() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        EstadoEsValidoRegla.ejecutarRegla(datos);
    }
}
