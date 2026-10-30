package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.ClienteSql;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ClienteMapper {

    private ClienteMapper() {
        super();
    }

    public static ClienteEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ClienteEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(ClienteSql.COLUMNA_ID)));
            entidad.setNombre(resultSet.getString(ClienteSql.COLUMNA_NOMBRE));
            entidad.setApellido(resultSet.getString(ClienteSql.COLUMNA_APELLIDO));
            entidad.setCorreoElectronico(resultSet.getString(ClienteSql.COLUMNA_CORREO_ELECTRONICO));
            entidad.setContrasena(resultSet.getString(ClienteSql.COLUMNA_CONTRASENA));
            entidad.setTelefono(resultSet.getString(ClienteSql.COLUMNA_TELEFONO));
            entidad.setEstado(resultSet.getBoolean(ClienteSql.COLUMNA_ESTADO));
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
