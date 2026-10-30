package co.edu.uco.imexcol.negocio.casouso.validador.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.administrador.AdministradorNoEsElUnicoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarAdministradorNoEsElUnico implements Validador {

    private static final ValidarAdministradorNoEsElUnico INSTANCIA = new ValidarAdministradorNoEsElUnico();

    private ValidarAdministradorNoEsElUnico() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        AdministradorNoEsElUnicoRegla.obtenerInstancia().ejecutar(datos);
    }
}
