package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class AdministradorMapper {

    private AdministradorMapper() {
        super();
    }

    public static AdministradorEntidad mapear(final ResultSet resultSet) {
        final var entidad = new AdministradorEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("administrador_id")));
            entidad.setNombreUsuario(resultSet.getString("administrador_nombre_usuario"));
            entidad.setCorreoElectronico(resultSet.getString("administrador_correo_electronico"));
            entidad.setContrasena(resultSet.getString("administrador_contrasena"));
            entidad.setEstado(resultSet.getBoolean("administrador_estado"));
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
