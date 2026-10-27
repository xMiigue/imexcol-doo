package co.edu.uco.imexcol.negocio.casouso.validador.metodopago;

import co.edu.uco.imexcol.negocio.casouso.regla.metodopago.NombreMetodoPagoNoExisteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarNombreMetodoPagoNoExiste implements Validador {

    private static final ValidarNombreMetodoPagoNoExiste INSTANCIA = new ValidarNombreMetodoPagoNoExiste();

    private ValidarNombreMetodoPagoNoExiste() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        NombreMetodoPagoNoExisteRegla.obtenerInstancia().ejecutar(datos);
    }
}
