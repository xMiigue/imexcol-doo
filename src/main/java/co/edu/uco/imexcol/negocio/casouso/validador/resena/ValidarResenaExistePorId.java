package co.edu.uco.imexcol.negocio.casouso.validador.resena;

import co.edu.uco.imexcol.negocio.casouso.regla.resena.ResenaExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarResenaExistePorId implements Validador {

    private static final ValidarResenaExistePorId INSTANCIA = new ValidarResenaExistePorId();

    private ValidarResenaExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ResenaExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
