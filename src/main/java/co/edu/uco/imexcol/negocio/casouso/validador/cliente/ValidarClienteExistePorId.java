package co.edu.uco.imexcol.negocio.casouso.validador.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.cliente.ClienteExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarClienteExistePorId implements Validador {

    private static final ValidarClienteExistePorId INSTANCIA = new ValidarClienteExistePorId();

    private ValidarClienteExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ClienteExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
