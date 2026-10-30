package co.edu.uco.imexcol.negocio.casouso.regla.factura;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class TotalFacturadoEsSumaDeSubtotalEImpuestosRegla implements Regla {

    private static final TotalFacturadoEsSumaDeSubtotalEImpuestosRegla INSTANCIA =
            new TotalFacturadoEsSumaDeSubtotalEImpuestosRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_SUBTOTAL = 0;
    private static final int POSICION_TOTAL_IMPUESTOS = 1;
    private static final int POSICION_TOTAL_FACTURADO = 2;

    private TotalFacturadoEsSumaDeSubtotalEImpuestosRegla() {
        super();
    }

    public static TotalFacturadoEsSumaDeSubtotalEImpuestosRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var subtotal = ((Number) datos[POSICION_SUBTOTAL]).doubleValue();
        final var totalImpuestos = ((Number) datos[POSICION_TOTAL_IMPUESTOS]).doubleValue();
        final var totalFacturado = ((Number) datos[POSICION_TOTAL_FACTURADO]).doubleValue();

        final var sumaEsperada = subtotal + totalImpuestos;
        if (!UtilNumerico.sonIgualesConDosDecimales(totalFacturado, sumaEsperada)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTURA_TOTAL_INCONSISTENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTURA_TOTAL_INCONSISTENTE.getContenido()
                            .formatted(totalFacturado, subtotal, totalImpuestos,
                                    UtilNumerico.redondearADosDecimales(sumaEsperada)));
        }
    }
}
