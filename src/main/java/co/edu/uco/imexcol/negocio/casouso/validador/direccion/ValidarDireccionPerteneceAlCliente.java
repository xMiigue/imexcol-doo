package co.edu.uco.imexcol.negocio.casouso.validador.direccion;

import co.edu.uco.imexcol.negocio.casouso.regla.direccion.DireccionPerteneceAlClienteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarDireccionPerteneceAlCliente implements Validador {

    private static final ValidarDireccionPerteneceAlCliente INSTANCIA = new ValidarDireccionPerteneceAlCliente();

    private ValidarDireccionPerteneceAlCliente() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        DireccionPerteneceAlClienteRegla.obtenerInstancia().ejecutar(datos);
    }
}
