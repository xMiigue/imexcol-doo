package co.edu.uco.imexcol.negocio.casouso.validador.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.administrador.AdministradorEstaActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarAdministradorEstaActivo implements Validador {

    private static final ValidarAdministradorEstaActivo INSTANCIA = new ValidarAdministradorEstaActivo();

    private ValidarAdministradorEstaActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        AdministradorEstaActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
