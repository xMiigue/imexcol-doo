package co.edu.uco.imexcol.negocio.casouso.validador.direccion;

import co.edu.uco.imexcol.negocio.casouso.regla.direccion.DireccionExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarDireccionExistePorId implements Validador {

    private static final ValidarDireccionExistePorId INSTANCIA = new ValidarDireccionExistePorId();

    private ValidarDireccionExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        DireccionExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
