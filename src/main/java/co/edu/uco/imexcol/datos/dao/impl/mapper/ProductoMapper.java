package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class ProductoMapper {

    private ProductoMapper() {
        super();
    }

    public static ProductoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ProductoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("producto_id")));

            final var categoria = new CategoriaEntidad();
            categoria.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("producto_categoria_id")));
            entidad.setCategoria(categoria);

            entidad.setNombre(resultSet.getString("producto_nombre"));
            entidad.setDescripcion(resultSet.getString("producto_descripcion"));
            entidad.setPrecio(resultSet.getDouble("producto_precio"));
            entidad.setStock(resultSet.getInt("producto_stock"));
            entidad.setEstado(resultSet.getBoolean("producto_estado"));
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
