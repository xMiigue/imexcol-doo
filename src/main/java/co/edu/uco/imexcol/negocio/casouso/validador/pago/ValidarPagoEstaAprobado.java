package co.edu.uco.imexcol.negocio.casouso.validador.pago;

import co.edu.uco.imexcol.negocio.casouso.regla.pago.PagoEstaAprobadoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPagoEstaAprobado implements Validador {

    private static final ValidarPagoEstaAprobado INSTANCIA = new ValidarPagoEstaAprobado();

    private ValidarPagoEstaAprobado() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PagoEstaAprobadoRegla.obtenerInstancia().ejecutar(datos);
    }
}
