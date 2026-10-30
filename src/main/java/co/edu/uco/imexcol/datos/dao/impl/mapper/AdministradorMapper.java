package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.AdministradorSql;
import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class AdministradorMapper {

    private AdministradorMapper() {
        super();
    }

    public static AdministradorEntidad mapear(final ResultSet resultSet) {
        final var entidad = new AdministradorEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(AdministradorSql.COLUMNA_ID)));
            entidad.setNombreUsuario(resultSet.getString(AdministradorSql.COLUMNA_NOMBRE_USUARIO));
            entidad.setCorreoElectronico(resultSet.getString(AdministradorSql.COLUMNA_CORREO_ELECTRONICO));
            entidad.setContrasena(resultSet.getString(AdministradorSql.COLUMNA_CONTRASENA));
            entidad.setEstado(resultSet.getBoolean(AdministradorSql.COLUMNA_ESTADO));
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
