package co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo;

import co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo.FechaEventoNoEsAnteriorAlDespachoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarFechaEventoNoEsAnteriorAlDespacho implements Validador {

    private static final ValidarFechaEventoNoEsAnteriorAlDespacho INSTANCIA = new ValidarFechaEventoNoEsAnteriorAlDespacho();

    private ValidarFechaEventoNoEsAnteriorAlDespacho() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        FechaEventoNoEsAnteriorAlDespachoRegla.obtenerInstancia().ejecutar(datos);
    }
}
