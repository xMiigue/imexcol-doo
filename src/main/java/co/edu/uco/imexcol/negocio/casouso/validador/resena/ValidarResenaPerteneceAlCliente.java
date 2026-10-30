package co.edu.uco.imexcol.negocio.casouso.validador.resena;

import co.edu.uco.imexcol.negocio.casouso.regla.resena.ResenaPerteneceAlClienteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarResenaPerteneceAlCliente implements Validador {

    private static final ValidarResenaPerteneceAlCliente INSTANCIA = new ValidarResenaPerteneceAlCliente();

    private ValidarResenaPerteneceAlCliente() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ResenaPerteneceAlClienteRegla.obtenerInstancia().ejecutar(datos);
    }
}
