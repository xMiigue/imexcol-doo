package co.edu.uco.imexcol.negocio.casouso.validador.metodopago;

import co.edu.uco.imexcol.negocio.casouso.regla.metodopago.MetodoPagoExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarMetodoPagoExistePorId implements Validador {

    private static final ValidarMetodoPagoExistePorId INSTANCIA = new ValidarMetodoPagoExistePorId();

    private ValidarMetodoPagoExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        MetodoPagoExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
