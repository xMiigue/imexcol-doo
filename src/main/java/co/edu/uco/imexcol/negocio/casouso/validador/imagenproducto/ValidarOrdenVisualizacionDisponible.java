package co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto;

import co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto.OrdenVisualizacionDisponibleRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarOrdenVisualizacionDisponible implements Validador {

    private static final ValidarOrdenVisualizacionDisponible INSTANCIA = new ValidarOrdenVisualizacionDisponible();

    private ValidarOrdenVisualizacionDisponible() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        OrdenVisualizacionDisponibleRegla.obtenerInstancia().ejecutar(datos);
    }
}
