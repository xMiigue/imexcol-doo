package co.edu.uco.imexcol.negocio.casouso.validador.direccion;

import co.edu.uco.imexcol.negocio.casouso.regla.direccion.DireccionNoEstaDuplicadaParaClienteRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarDireccionNoEstaDuplicadaParaCliente implements Validador {

    private static final ValidarDireccionNoEstaDuplicadaParaCliente INSTANCIA = new ValidarDireccionNoEstaDuplicadaParaCliente();

    private ValidarDireccionNoEstaDuplicadaParaCliente() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        DireccionNoEstaDuplicadaParaClienteRegla.obtenerInstancia().ejecutar(datos);
    }
}
