package co.edu.uco.imexcol.negocio.casouso.validador.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.cliente.CorreoClienteNoExisteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCorreoClienteNoExiste implements Validador {

    private static final ValidarCorreoClienteNoExiste INSTANCIA = new ValidarCorreoClienteNoExiste();

    private ValidarCorreoClienteNoExiste() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CorreoClienteNoExisteRegla.obtenerInstancia().ejecutar(datos);
    }
}
