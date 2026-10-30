package co.edu.uco.imexcol.negocio.casouso.validador.envio;

import co.edu.uco.imexcol.negocio.casouso.regla.envio.EnvioExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEnvioExistePorId implements Validador {

    private static final ValidarEnvioExistePorId INSTANCIA = new ValidarEnvioExistePorId();

    private ValidarEnvioExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        EnvioExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
