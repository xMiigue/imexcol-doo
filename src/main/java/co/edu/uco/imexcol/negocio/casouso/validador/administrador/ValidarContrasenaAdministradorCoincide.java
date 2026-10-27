package co.edu.uco.imexcol.negocio.casouso.validador.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.administrador.ContrasenaAdministradorCoincideRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarContrasenaAdministradorCoincide implements Validador {

    private static final ValidarContrasenaAdministradorCoincide INSTANCIA = new ValidarContrasenaAdministradorCoincide();

    private ValidarContrasenaAdministradorCoincide() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ContrasenaAdministradorCoincideRegla.obtenerInstancia().ejecutar(datos);
    }
}
