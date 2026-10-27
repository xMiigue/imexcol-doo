package co.edu.uco.imexcol.negocio.casouso.regla.carritocompras;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.CarritoComprasEntidad;
import co.edu.uco.imexcol.entidad.ItemCarritoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoCarritoComprasEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CarritoComprasTieneItemsParaConvertirseRegla implements Regla {

    private static final CarritoComprasTieneItemsParaConvertirseRegla INSTANCIA =
            new CarritoComprasTieneItemsParaConvertirseRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_CARRITO = 0;
    private static final int POSICION_FABRICA = 1;

    private CarritoComprasTieneItemsParaConvertirseRegla() {
        super();
    }

    public static CarritoComprasTieneItemsParaConvertirseRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var carrito = UtilObjeto.obtenerValorDefecto((CarritoComprasDominio) datos[POSICION_CARRITO],
                new CarritoComprasDominio());
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (EstadoCarritoComprasEnum.CONVERTIDO.esIgualA(carrito.getEstado()) && !tieneItems(carrito, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_CARRITO_COMPRAS_SIN_ITEMS_PARA_CONVERTIR.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CARRITO_COMPRAS_SIN_ITEMS_PARA_CONVERTIR.getContenido()
                            .formatted(carrito.getId(), EstadoCarritoComprasEnum.CONVERTIDO.getValor()));
        }
    }

    private static boolean tieneItems(final CarritoComprasDominio carrito, final FabricaDAO fabrica) {
        final var filtro = new ItemCarritoEntidad();
        filtro.setCarrito(new CarritoComprasEntidad(carrito.getId()));
        return !fabrica.obtenerItemCarritoDAO().consultarPorFiltro(filtro).isEmpty();
    }
}
