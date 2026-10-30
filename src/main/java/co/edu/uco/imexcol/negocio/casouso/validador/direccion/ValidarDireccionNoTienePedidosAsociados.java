package co.edu.uco.imexcol.negocio.casouso.validador.direccion;

import co.edu.uco.imexcol.negocio.casouso.regla.direccion.DireccionNoTienePedidosAsociadosRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarDireccionNoTienePedidosAsociados implements Validador {

    private static final ValidarDireccionNoTienePedidosAsociados INSTANCIA =
            new ValidarDireccionNoTienePedidosAsociados();

    private ValidarDireccionNoTienePedidosAsociados() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        DireccionNoTienePedidosAsociadosRegla.obtenerInstancia().ejecutar(datos);
    }
}
