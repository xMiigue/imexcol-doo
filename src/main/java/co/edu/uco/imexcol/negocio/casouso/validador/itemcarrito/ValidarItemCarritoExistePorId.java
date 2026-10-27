package co.edu.uco.imexcol.negocio.casouso.validador.itemcarrito;

import co.edu.uco.imexcol.negocio.casouso.regla.itemcarrito.ItemCarritoExistePorIdRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarItemCarritoExistePorId implements Validador {

    private static final ValidarItemCarritoExistePorId INSTANCIA = new ValidarItemCarritoExistePorId();

    private ValidarItemCarritoExistePorId() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        ItemCarritoExistePorIdRegla.obtenerInstancia().ejecutar(datos);
    }
}
