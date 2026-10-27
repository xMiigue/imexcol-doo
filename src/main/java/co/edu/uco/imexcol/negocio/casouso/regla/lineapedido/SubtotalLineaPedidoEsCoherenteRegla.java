package co.edu.uco.imexcol.negocio.casouso.regla.lineapedido;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class SubtotalLineaPedidoEsCoherenteRegla implements Regla {

    private static final SubtotalLineaPedidoEsCoherenteRegla INSTANCIA = new SubtotalLineaPedidoEsCoherenteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_CANTIDAD = 0;
    private static final int POSICION_PRECIO_UNITARIO = 1;
    private static final int POSICION_SUBTOTAL = 2;

    private SubtotalLineaPedidoEsCoherenteRegla() {
        super();
    }

    public static SubtotalLineaPedidoEsCoherenteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var cantidad = UtilNumerico.obtenerValorDefecto((Integer) datos[POSICION_CANTIDAD]);
        final var precioUnitario = UtilNumerico.obtenerValorDefecto((Double) datos[POSICION_PRECIO_UNITARIO]);
        final var subtotal = UtilNumerico.obtenerValorDefecto((Double) datos[POSICION_SUBTOTAL]);

        final var subtotalEsperado = UtilNumerico.multiplicarADosDecimales(precioUnitario, cantidad);
        if (!UtilNumerico.sonIgualesConDosDecimales(subtotal, subtotalEsperado)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_LINEA_PEDIDO_SUBTOTAL_INCONSISTENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_LINEA_PEDIDO_SUBTOTAL_INCONSISTENTE.getContenido()
                            .formatted(subtotal, subtotalEsperado, cantidad, precioUnitario));
        }
    }
}
