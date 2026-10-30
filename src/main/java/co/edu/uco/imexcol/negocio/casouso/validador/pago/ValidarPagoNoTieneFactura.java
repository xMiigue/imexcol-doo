package co.edu.uco.imexcol.negocio.casouso.validador.pago;

import co.edu.uco.imexcol.negocio.casouso.regla.pago.PagoNoTieneFacturaRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPagoNoTieneFactura implements Validador {

    private static final ValidarPagoNoTieneFactura INSTANCIA = new ValidarPagoNoTieneFactura();

    private ValidarPagoNoTieneFactura() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PagoNoTieneFacturaRegla.obtenerInstancia().ejecutar(datos);
    }
}
