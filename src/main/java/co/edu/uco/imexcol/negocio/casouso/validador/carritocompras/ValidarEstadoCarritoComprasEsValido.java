package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEstadoCarritoComprasEsValido implements Validador {

    private static final ValidarEstadoCarritoComprasEsValido INSTANCIA = new ValidarEstadoCarritoComprasEsValido();

    public static final String[] ESTADOS_VALIDOS = { "Activo", "Abandonado", "Convertido" };

    private ValidarEstadoCarritoComprasEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = (String) datos[0];
        EstadoEsValidoRegla.ejecutarRegla(estado, "estado del carrito de compras", ESTADOS_VALIDOS);
    }
}
