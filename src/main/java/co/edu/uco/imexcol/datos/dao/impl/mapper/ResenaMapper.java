package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import co.edu.uco.imexcol.datos.dao.impl.sql.ResenaSql;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.entidad.ResenaEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ResenaMapper {

    private ResenaMapper() {
        super();
    }

    public static ResenaEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ResenaEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ResenaSql.COLUMNA_ID)));

            final var cliente = new ClienteEntidad();
            cliente.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ResenaSql.COLUMNA_CLIENTE_ID)));
            entidad.setCliente(cliente);

            final var producto = new ProductoEntidad();
            producto.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ResenaSql.COLUMNA_PRODUCTO_ID)));
            entidad.setProducto(producto);

            entidad.setCalificacion(resultSet.getInt(ResenaSql.COLUMNA_CALIFICACION));
            entidad.setComentario(resultSet.getString(ResenaSql.COLUMNA_COMENTARIO));

            final Timestamp fechaSql = resultSet.getTimestamp(ResenaSql.COLUMNA_FECHA);
            entidad.setFecha(UtilObjeto.esNulo(fechaSql)
                    ? UtilFecha.obtenerFechaHoraPorDefecto()
                    : fechaSql.toLocalDateTime());
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
