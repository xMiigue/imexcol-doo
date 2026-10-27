package co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo;

import co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo.ObservacionObligatoriaEnNovedadRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarObservacionObligatoriaEnNovedad implements Validador {

    private static final ValidarObservacionObligatoriaEnNovedad INSTANCIA = new ValidarObservacionObligatoriaEnNovedad();

    private ValidarObservacionObligatoriaEnNovedad() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ObservacionObligatoriaEnNovedadRegla.obtenerInstancia().ejecutar(datos);
    }
}
