package co.edu.uco.imexcol.negocio.casouso.validador.resena;

import co.edu.uco.imexcol.negocio.casouso.regla.resena.CalificacionResenaEnRangoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCalificacionResenaEnRango implements Validador {

    private static final ValidarCalificacionResenaEnRango INSTANCIA = new ValidarCalificacionResenaEnRango();

    private ValidarCalificacionResenaEnRango() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CalificacionResenaEnRangoRegla.obtenerInstancia().ejecutar(datos);
    }
}
