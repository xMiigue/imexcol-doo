package co.edu.uco.imexcol.negocio.casouso.validador.factura;

import co.edu.uco.imexcol.negocio.casouso.regla.factura.NumeroFacturaNoExisteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarNumeroFacturaNoExiste implements Validador {

    private static final ValidarNumeroFacturaNoExiste INSTANCIA = new ValidarNumeroFacturaNoExiste();

    private ValidarNumeroFacturaNoExiste() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        NumeroFacturaNoExisteRegla.obtenerInstancia().ejecutar(datos);
    }
}
