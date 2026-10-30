package co.edu.uco.imexcol.negocio.casouso.validador.metodopago;

import co.edu.uco.imexcol.negocio.casouso.regla.metodopago.MetodoPagoEstaActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarMetodoPagoEstaActivo implements Validador {

    private static final ValidarMetodoPagoEstaActivo INSTANCIA = new ValidarMetodoPagoEstaActivo();

    private ValidarMetodoPagoEstaActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        MetodoPagoEstaActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
