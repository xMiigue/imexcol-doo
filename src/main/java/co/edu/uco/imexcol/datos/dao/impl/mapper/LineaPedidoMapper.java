package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class LineaPedidoMapper {

    private LineaPedidoMapper() {
        super();
    }

    public static LineaPedidoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new LineaPedidoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("linea_pedido_id")));

            final var pedido = new PedidoEntidad();
            pedido.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("linea_pedido_pedido_id")));
            entidad.setPedido(pedido);

            final var producto = new ProductoEntidad();
            producto.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("linea_pedido_producto_id")));
            entidad.setProducto(producto);

            entidad.setCantidad(resultSet.getInt("linea_pedido_cantidad"));
            entidad.setPrecioUnitario(resultSet.getDouble("linea_pedido_precio_unitario"));
            entidad.setSubtotal(resultSet.getDouble("linea_pedido_subtotal"));
        } catch (final SQLException excepcion) {
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_MAPPER_GENERICO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_MAPPER_GENERICO.getContenido(),
                    Lugar.DATOS);
        } catch (final Exception excepcion) {
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_MAPPER_INESPERADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_MAPPER_INESPERADO.getContenido(),
                    Lugar.DATOS);
        }
        return entidad;
    }
}
