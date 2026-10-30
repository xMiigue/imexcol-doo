package co.edu.uco.imexcol.negocio.casouso.validador.carritocompras;

import co.edu.uco.imexcol.negocio.casouso.regla.carritocompras.CarritoComprasTieneItemsParaConvertirseRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarCarritoComprasTieneItemsParaConvertirse implements Validador {

    private static final ValidarCarritoComprasTieneItemsParaConvertirse INSTANCIA =
            new ValidarCarritoComprasTieneItemsParaConvertirse();

    private ValidarCarritoComprasTieneItemsParaConvertirse() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        CarritoComprasTieneItemsParaConvertirseRegla.obtenerInstancia().ejecutar(datos);
    }
}
