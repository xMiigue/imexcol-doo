package co.edu.uco.imexcol.negocio.casouso.validador.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.cliente.ContrasenaClienteCoincideRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarContrasenaClienteCoincide implements Validador {

    private static final ValidarContrasenaClienteCoincide INSTANCIA = new ValidarContrasenaClienteCoincide();

    private ValidarContrasenaClienteCoincide() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ContrasenaClienteCoincideRegla.obtenerInstancia().ejecutar(datos);
    }
}
