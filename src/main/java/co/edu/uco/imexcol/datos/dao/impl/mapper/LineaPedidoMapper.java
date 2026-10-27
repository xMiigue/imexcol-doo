package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.LineaPedidoSql;
import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class LineaPedidoMapper {

    private LineaPedidoMapper() {
        super();
    }

    public static LineaPedidoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new LineaPedidoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(LineaPedidoSql.COLUMNA_ID)));

            final var pedido = new PedidoEntidad();
            pedido.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(LineaPedidoSql.COLUMNA_PEDIDO_ID)));
            entidad.setPedido(pedido);

            final var producto = new ProductoEntidad();
            producto.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(LineaPedidoSql.COLUMNA_PRODUCTO_ID)));
            entidad.setProducto(producto);

            entidad.setCantidad(resultSet.getInt(LineaPedidoSql.COLUMNA_CANTIDAD));
            entidad.setPrecioUnitario(resultSet.getDouble(LineaPedidoSql.COLUMNA_PRECIO_UNITARIO));
            entidad.setSubtotal(resultSet.getDouble(LineaPedidoSql.COLUMNA_SUBTOTAL));
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_MAPPER_GENERICO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_MAPPER_GENERICO.getContenido());
        } catch (final Exception excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_MAPPER_INESPERADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_MAPPER_INESPERADO.getContenido());
        }
        return entidad;
    }
}
