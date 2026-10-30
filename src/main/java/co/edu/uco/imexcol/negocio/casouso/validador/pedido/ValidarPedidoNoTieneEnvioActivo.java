package co.edu.uco.imexcol.negocio.casouso.validador.pedido;

import co.edu.uco.imexcol.negocio.casouso.regla.pedido.PedidoNoTieneEnvioActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPedidoNoTieneEnvioActivo implements Validador {

    private static final ValidarPedidoNoTieneEnvioActivo INSTANCIA = new ValidarPedidoNoTieneEnvioActivo();

    private ValidarPedidoNoTieneEnvioActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PedidoNoTieneEnvioActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
