package co.edu.uco.imexcol.negocio.casouso.validador.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.administrador.CredencialesAdministradorSonObligatoriasRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCredencialesAdministradorSonObligatorias implements Validador {

    private static final ValidarCredencialesAdministradorSonObligatorias INSTANCIA = new ValidarCredencialesAdministradorSonObligatorias();

    private ValidarCredencialesAdministradorSonObligatorias() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CredencialesAdministradorSonObligatoriasRegla.obtenerInstancia().ejecutar(datos);
    }
}
