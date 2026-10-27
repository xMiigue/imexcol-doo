package co.edu.uco.imexcol.negocio.casouso.validador.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.administrador.AdministradorExistePorNombreUsuarioRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarAdministradorExistePorNombreUsuario implements Validador {

    private static final ValidarAdministradorExistePorNombreUsuario INSTANCIA = new ValidarAdministradorExistePorNombreUsuario();

    private ValidarAdministradorExistePorNombreUsuario() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        AdministradorExistePorNombreUsuarioRegla.obtenerInstancia().ejecutar(datos);
    }
}
