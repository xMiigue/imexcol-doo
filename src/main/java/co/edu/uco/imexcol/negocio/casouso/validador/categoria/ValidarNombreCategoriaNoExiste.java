package co.edu.uco.imexcol.negocio.casouso.validador.categoria;

import co.edu.uco.imexcol.negocio.casouso.regla.categoria.NombreCategoriaNoExisteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarNombreCategoriaNoExiste implements Validador {

    private static final ValidarNombreCategoriaNoExiste INSTANCIA = new ValidarNombreCategoriaNoExiste();

    private ValidarNombreCategoriaNoExiste() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        NombreCategoriaNoExisteRegla.obtenerInstancia().ejecutar(datos);
    }
}
