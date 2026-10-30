package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.carritocompras.CarritoComprasNoEstaConvertidoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCarritoComprasNoEstaConvertido implements Validador {

    private static final ValidarCarritoComprasNoEstaConvertido INSTANCIA = new ValidarCarritoComprasNoEstaConvertido();

    private ValidarCarritoComprasNoEstaConvertido() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CarritoComprasNoEstaConvertidoRegla.obtenerInstancia().ejecutar(datos);
    }
}
