package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.EstadoEsValidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoCarritoComprasEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;

public final class ValidarEstadoCarritoComprasEsValido implements Validador {

    private static final ValidarEstadoCarritoComprasEsValido INSTANCIA = new ValidarEstadoCarritoComprasEsValido();
    private static final String NOMBRE_DATO = "estado del carrito de compras";
    private static final int POSICION_ESTADO = 0;

    private ValidarEstadoCarritoComprasEsValido() {
        super();
    }

    public static void ejecutarValidacion(final String estado) {
        INSTANCIA.validar(estado);
    }

    @Override
    public void validar(final Object... datos) {
        final var estado = UtilTexto.aplicarTrim((String) datos[POSICION_ESTADO]);
        EstadoEsValidoRegla.obtenerInstancia().ejecutar(estado, NOMBRE_DATO,
                EstadoCarritoComprasEnum.obtenerValoresPermitidos());
    }
}
