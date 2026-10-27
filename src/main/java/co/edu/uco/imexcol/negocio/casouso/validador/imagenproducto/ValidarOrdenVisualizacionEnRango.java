package co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto;

import co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto.OrdenVisualizacionEnRangoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarOrdenVisualizacionEnRango implements Validador {

    private static final ValidarOrdenVisualizacionEnRango INSTANCIA = new ValidarOrdenVisualizacionEnRango();

    private ValidarOrdenVisualizacionEnRango() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        OrdenVisualizacionEnRangoRegla.obtenerInstancia().ejecutar(datos);
    }
}
