package co.edu.uco.imexcol.negocio.casouso.regla.producto;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ProductoNoTienePedidosAsociadosRegla implements Regla {

    private static final ProductoNoTienePedidosAsociadosRegla INSTANCIA = new ProductoNoTienePedidosAsociadosRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_PRODUCTO = 0;
    private static final int POSICION_FABRICA = 1;

    private ProductoNoTienePedidosAsociadosRegla() {
        super();
    }

    public static ProductoNoTienePedidosAsociadosRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var lineas = consultarLineasPedidoDelProducto(idProducto, fabrica);
        if (!lineas.isEmpty()) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PRODUCTO_CON_PEDIDOS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PRODUCTO_CON_PEDIDOS.getContenido()
                            .formatted(idProducto, lineas.size()));
        }
    }

    private static List<LineaPedidoEntidad> consultarLineasPedidoDelProducto(final UUID idProducto,
            final FabricaDAO fabrica) {
        final var filtro = new LineaPedidoEntidad();
        filtro.setProducto(new ProductoEntidad(idProducto));
        return fabrica.obtenerLineaPedidoDAO().consultarPorFiltro(filtro);
    }
}
