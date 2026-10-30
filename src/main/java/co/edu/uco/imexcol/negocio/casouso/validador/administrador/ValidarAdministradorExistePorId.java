package co.edu.uco.imexcol.negocio.casouso.validador.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.administrador.AdministradorExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarAdministradorExistePorId implements Validador {

    private static final ValidarAdministradorExistePorId INSTANCIA = new ValidarAdministradorExistePorId();

    private ValidarAdministradorExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        AdministradorExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
