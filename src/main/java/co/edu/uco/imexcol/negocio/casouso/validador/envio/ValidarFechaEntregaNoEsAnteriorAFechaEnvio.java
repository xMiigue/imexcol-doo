package co.edu.uco.imexcol.negocio.casouso.validador.envio;

import co.edu.uco.imexcol.negocio.casouso.regla.envio.FechaEntregaNoEsAnteriorAFechaEnvioRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarFechaEntregaNoEsAnteriorAFechaEnvio implements Validador {

    private static final ValidarFechaEntregaNoEsAnteriorAFechaEnvio INSTANCIA =
            new ValidarFechaEntregaNoEsAnteriorAFechaEnvio();

    private ValidarFechaEntregaNoEsAnteriorAFechaEnvio() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        FechaEntregaNoEsAnteriorAFechaEnvioRegla.obtenerInstancia().ejecutar(datos);
    }
}
