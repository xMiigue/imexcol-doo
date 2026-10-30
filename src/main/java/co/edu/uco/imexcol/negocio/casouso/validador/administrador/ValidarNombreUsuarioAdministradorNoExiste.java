package co.edu.uco.imexcol.negocio.casouso.validador.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.administrador.NombreUsuarioAdministradorNoExisteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarNombreUsuarioAdministradorNoExiste implements Validador {

    private static final ValidarNombreUsuarioAdministradorNoExiste INSTANCIA =
            new ValidarNombreUsuarioAdministradorNoExiste();

    private ValidarNombreUsuarioAdministradorNoExiste() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        NombreUsuarioAdministradorNoExisteRegla.obtenerInstancia().ejecutar(datos);
    }
}
