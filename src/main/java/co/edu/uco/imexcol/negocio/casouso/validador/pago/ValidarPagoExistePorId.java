package co.edu.uco.imexcol.negocio.casouso.validador.pago;

import co.edu.uco.imexcol.negocio.casouso.regla.pago.PagoExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarPagoExistePorId implements Validador {

    private static final ValidarPagoExistePorId INSTANCIA = new ValidarPagoExistePorId();

    private ValidarPagoExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        PagoExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
