package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.CarritoComprasEntidad;
import co.edu.uco.imexcol.entidad.ItemCarritoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class ItemCarritoMapper {

    private ItemCarritoMapper() {
        super();
    }

    public static ItemCarritoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ItemCarritoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("item_carrito_id")));

            final var carrito = new CarritoComprasEntidad();
            carrito.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("item_carrito_carrito_id")));
            entidad.setCarrito(carrito);

            final var producto = new ProductoEntidad();
            producto.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("item_carrito_producto_id")));
            entidad.setProducto(producto);

            entidad.setCantidad(resultSet.getInt("item_carrito_cantidad"));
            entidad.setPrecioUnitario(resultSet.getDouble("item_carrito_precio_unitario"));
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
