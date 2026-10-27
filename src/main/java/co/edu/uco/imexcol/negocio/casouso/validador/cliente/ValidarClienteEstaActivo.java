package co.edu.uco.imexcol.negocio.casouso.validador.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.cliente.ClienteEstaActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarClienteEstaActivo implements Validador {

    private static final ValidarClienteEstaActivo INSTANCIA = new ValidarClienteEstaActivo();

    private ValidarClienteEstaActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ClienteEstaActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
