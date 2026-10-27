package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.ItemCarritoSql;
import co.edu.uco.imexcol.entidad.CarritoComprasEntidad;
import co.edu.uco.imexcol.entidad.ItemCarritoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ItemCarritoMapper {

    private ItemCarritoMapper() {
        super();
    }

    public static ItemCarritoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ItemCarritoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ItemCarritoSql.COLUMNA_ID)));

            final var carrito = new CarritoComprasEntidad();
            carrito.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ItemCarritoSql.COLUMNA_CARRITO_ID)));
            entidad.setCarrito(carrito);

            final var producto = new ProductoEntidad();
            producto.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ItemCarritoSql.COLUMNA_PRODUCTO_ID)));
            entidad.setProducto(producto);

            entidad.setCantidad(resultSet.getInt(ItemCarritoSql.COLUMNA_CANTIDAD));
            entidad.setPrecioUnitario(resultSet.getDouble(ItemCarritoSql.COLUMNA_PRECIO_UNITARIO));
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
