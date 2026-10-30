package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.DireccionSql;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class DireccionMapper {

    private DireccionMapper() {
        super();
    }

    public static DireccionEntidad mapear(final ResultSet resultSet) {
        final var entidad = new DireccionEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(DireccionSql.COLUMNA_ID)));

            final var cliente = new ClienteEntidad();
            cliente.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(DireccionSql.COLUMNA_CLIENTE_ID)));
            entidad.setCliente(cliente);

            entidad.setCalle(resultSet.getString(DireccionSql.COLUMNA_CALLE));
            entidad.setCiudad(resultSet.getString(DireccionSql.COLUMNA_CIUDAD));
            entidad.setDepartamento(resultSet.getString(DireccionSql.COLUMNA_DEPARTAMENTO));
            entidad.setCodigoPostal(resultSet.getString(DireccionSql.COLUMNA_CODIGO_POSTAL));
            entidad.setPais(resultSet.getString(DireccionSql.COLUMNA_PAIS));
            entidad.setEsPrincipal(resultSet.getBoolean(DireccionSql.COLUMNA_ES_PRINCIPAL));
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
