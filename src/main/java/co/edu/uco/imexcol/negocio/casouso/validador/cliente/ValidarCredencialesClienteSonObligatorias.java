package co.edu.uco.imexcol.negocio.casouso.validador.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.cliente.CredencialesClienteSonObligatoriasRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCredencialesClienteSonObligatorias implements Validador {

    private static final ValidarCredencialesClienteSonObligatorias INSTANCIA = new ValidarCredencialesClienteSonObligatorias();

    private ValidarCredencialesClienteSonObligatorias() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CredencialesClienteSonObligatoriasRegla.obtenerInstancia().ejecutar(datos);
    }
}
