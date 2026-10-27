package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoTienePagoAprobadoParaFacturarRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoTienePagoAprobadoParaFacturar implements Validador {

    private static final ValidarPedidoTienePagoAprobadoParaFacturar INSTANCIA = new ValidarPedidoTienePagoAprobadoParaFacturar();

    private ValidarPedidoTienePagoAprobadoParaFacturar() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoTienePagoAprobadoParaFacturarRegla.obtenerInstancia().ejecutar(datos);
    }
}
