package co.edu.uco.imexcol.negocio.casouso.validador.categoria;

import co.edu.uco.imexcol.negocio.casouso.regla.categoria.CategoriaExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCategoriaExistePorId implements Validador {

    private static final ValidarCategoriaExistePorId INSTANCIA = new ValidarCategoriaExistePorId();

    private ValidarCategoriaExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CategoriaExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
