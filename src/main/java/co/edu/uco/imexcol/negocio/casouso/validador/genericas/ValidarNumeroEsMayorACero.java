package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.NumeroEsMayorACeroRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarNumeroEsMayorACero implements Validador {

    private static final ValidarNumeroEsMayorACero INSTANCIA = new ValidarNumeroEsMayorACero();

    private ValidarNumeroEsMayorACero() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        NumeroEsMayorACeroRegla.ejecutarRegla(datos);
    }
}
