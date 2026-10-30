package co.edu.uco.imexcol.negocio.casouso.validador.envio;

import co.edu.uco.imexcol.negocio.casouso.regla.envio.EnvioPerteneceAlClienteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEnvioPerteneceAlCliente implements Validador {

    private static final ValidarEnvioPerteneceAlCliente INSTANCIA = new ValidarEnvioPerteneceAlCliente();

    private ValidarEnvioPerteneceAlCliente() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        EnvioPerteneceAlClienteRegla.obtenerInstancia().ejecutar(datos);
    }
}
