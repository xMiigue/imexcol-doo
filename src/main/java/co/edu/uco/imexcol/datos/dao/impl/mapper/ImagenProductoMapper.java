package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.ImagenProductoSql;
import co.edu.uco.imexcol.entidad.ImagenProductoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ImagenProductoMapper {

    private ImagenProductoMapper() {
        super();
    }

    public static ImagenProductoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ImagenProductoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ImagenProductoSql.COLUMNA_ID)));

            final var producto = new ProductoEntidad();
            producto.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ImagenProductoSql.COLUMNA_PRODUCTO_ID)));
            entidad.setProducto(producto);

            entidad.setFoto(resultSet.getString(ImagenProductoSql.COLUMNA_FOTO));
            entidad.setOrdenVisualizacion(resultSet.getInt(ImagenProductoSql.COLUMNA_ORDEN_VISUALIZACION));
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
