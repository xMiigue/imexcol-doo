package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.CategoriaSql;
import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class CategoriaMapper {

    private CategoriaMapper() {
        super();
    }

    public static CategoriaEntidad mapear(final ResultSet resultSet) {
        final var entidad = new CategoriaEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(CategoriaSql.COLUMNA_ID)));
            entidad.setNombre(resultSet.getString(CategoriaSql.COLUMNA_NOMBRE));
            entidad.setDescripcion(resultSet.getString(CategoriaSql.COLUMNA_DESCRIPCION));
            entidad.setEstado(resultSet.getBoolean(CategoriaSql.COLUMNA_ESTADO));
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
