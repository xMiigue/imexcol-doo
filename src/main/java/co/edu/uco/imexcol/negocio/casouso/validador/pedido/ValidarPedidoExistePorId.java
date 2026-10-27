package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoExistePorId implements Validador {

    private static final ValidarPedidoExistePorId INSTANCIA = new ValidarPedidoExistePorId();

    private ValidarPedidoExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
