package co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo;

import co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo.EventoRastreoPerteneceAEnvioActivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEventoRastreoPerteneceAEnvioActivo implements Validador {

    private static final ValidarEventoRastreoPerteneceAEnvioActivo INSTANCIA = new ValidarEventoRastreoPerteneceAEnvioActivo();

    private ValidarEventoRastreoPerteneceAEnvioActivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        EventoRastreoPerteneceAEnvioActivoRegla.obtenerInstancia().ejecutar(datos);
    }
}
