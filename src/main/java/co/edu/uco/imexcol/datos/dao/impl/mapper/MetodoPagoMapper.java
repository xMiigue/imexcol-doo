package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.MetodoPagoSql;
import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class MetodoPagoMapper {

    private MetodoPagoMapper() {
        super();
    }

    public static MetodoPagoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new MetodoPagoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(MetodoPagoSql.COLUMNA_ID)));
            entidad.setNombre(resultSet.getString(MetodoPagoSql.COLUMNA_NOMBRE));
            entidad.setDescripcion(resultSet.getString(MetodoPagoSql.COLUMNA_DESCRIPCION));
            entidad.setEstado(resultSet.getBoolean(MetodoPagoSql.COLUMNA_ESTADO));
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
