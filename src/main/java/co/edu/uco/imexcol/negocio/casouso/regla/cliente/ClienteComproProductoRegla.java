package co.edu.uco.imexcol.negocio.casouso.regla.cliente;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPedidoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ClienteComproProductoRegla implements Regla {

    private static final ClienteComproProductoRegla INSTANCIA = new ClienteComproProductoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_CLIENTE = 0;
    private static final int POSICION_ID_PRODUCTO = 1;
    private static final int POSICION_FABRICA = 2;

    private ClienteComproProductoRegla() {
        super();
    }

    public static ClienteComproProductoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (!comproProducto(idCliente, idProducto, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_RESENA_COMPRA_NO_VERIFICADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_RESENA_COMPRA_NO_VERIFICADA.getContenido()
                            .formatted(idCliente, idProducto));
        }
    }

    private static boolean comproProducto(final UUID idCliente, final UUID idProducto, final FabricaDAO fabrica) {
        return consultarPedidosConCompraExitosa(idCliente, fabrica).stream()
                .anyMatch(pedido -> pedidoIncluyeProducto(pedido.getId(), idProducto, fabrica));
    }

    private static List<PedidoEntidad> consultarPedidosConCompraExitosa(final UUID idCliente,
            final FabricaDAO fabrica) {
        final var filtro = new PedidoEntidad();
        filtro.setCliente(new ClienteEntidad(idCliente));
        return fabrica.obtenerPedidoDAO().consultarPorFiltro(filtro).stream()
                .filter(pedido -> EstadoPedidoEnum.representaCompraExitosa(pedido.getEstado()))
                .toList();
    }

    private static boolean pedidoIncluyeProducto(final UUID idPedido, final UUID idProducto,
            final FabricaDAO fabrica) {
        final var filtro = new LineaPedidoEntidad();
        filtro.setPedido(new PedidoEntidad(idPedido));
        filtro.setProducto(new ProductoEntidad(idProducto));
        return !fabrica.obtenerLineaPedidoDAO().consultarPorFiltro(filtro).isEmpty();
    }
}
