package co.edu.uco.imexcol.negocio.casouso.validador.factura;

import co.edu.uco.imexcol.negocio.casouso.regla.factura.TotalFacturadoEsSumaDeSubtotalEImpuestosRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarTotalFacturadoEsSumaDeSubtotalEImpuestos implements Validador {

    private static final ValidarTotalFacturadoEsSumaDeSubtotalEImpuestos INSTANCIA = new ValidarTotalFacturadoEsSumaDeSubtotalEImpuestos();

    private ValidarTotalFacturadoEsSumaDeSubtotalEImpuestos() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        TotalFacturadoEsSumaDeSubtotalEImpuestosRegla.obtenerInstancia().ejecutar(datos);
    }
}
