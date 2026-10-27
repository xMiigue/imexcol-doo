package co.edu.uco.imexcol.negocio.casouso.validador.lineapedido;

import co.edu.uco.imexcol.negocio.casouso.regla.lineapedido.SubtotalLineaPedidoEsCoherenteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarSubtotalLineaPedidoEsCoherente implements Validador {

    private static final ValidarSubtotalLineaPedidoEsCoherente INSTANCIA =
            new ValidarSubtotalLineaPedidoEsCoherente();

    private ValidarSubtotalLineaPedidoEsCoherente() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        SubtotalLineaPedidoEsCoherenteRegla.obtenerInstancia().ejecutar(datos);
    }
}
