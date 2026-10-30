package co.edu.uco.imexcol.negocio.casouso.validador.lineapedido;

import co.edu.uco.imexcol.negocio.casouso.regla.lineapedido.LineaPedidoExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarLineaPedidoExistePorId implements Validador {

    private static final ValidarLineaPedidoExistePorId INSTANCIA = new ValidarLineaPedidoExistePorId();

    private ValidarLineaPedidoExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        LineaPedidoExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
