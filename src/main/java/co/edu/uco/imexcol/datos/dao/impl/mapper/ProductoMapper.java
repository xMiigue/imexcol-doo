package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.ProductoSql;
import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ProductoMapper {

    private ProductoMapper() {
        super();
    }

    public static ProductoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ProductoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ProductoSql.COLUMNA_ID)));

            final var categoria = new CategoriaEntidad();
            categoria.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ProductoSql.COLUMNA_CATEGORIA_ID)));
            entidad.setCategoria(categoria);

            entidad.setNombre(resultSet.getString(ProductoSql.COLUMNA_NOMBRE));
            entidad.setDescripcion(resultSet.getString(ProductoSql.COLUMNA_DESCRIPCION));
            entidad.setPrecio(resultSet.getDouble(ProductoSql.COLUMNA_PRECIO));
            entidad.setStock(resultSet.getInt(ProductoSql.COLUMNA_STOCK));
            entidad.setImagenUrl(resultSet.getString(ProductoSql.COLUMNA_IMAGEN_URL));
            entidad.setEstado(resultSet.getBoolean(ProductoSql.COLUMNA_ESTADO));
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
