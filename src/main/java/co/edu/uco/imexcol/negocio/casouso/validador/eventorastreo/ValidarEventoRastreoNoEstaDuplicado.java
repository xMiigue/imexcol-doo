package co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo;

import co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo.EventoRastreoNoEstaDuplicadoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarEventoRastreoNoEstaDuplicado implements Validador {

    private static final ValidarEventoRastreoNoEstaDuplicado INSTANCIA = new ValidarEventoRastreoNoEstaDuplicado();

    private ValidarEventoRastreoNoEstaDuplicado() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        EventoRastreoNoEstaDuplicadoRegla.obtenerInstancia().ejecutar(datos);
    }
}
