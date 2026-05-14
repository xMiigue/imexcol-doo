package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class ClienteMapper {

    private ClienteMapper() {
        super();
    }

    public static ClienteEntidad mapear(final ResultSet resultSet) {
        final var entidad = new ClienteEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("cliente_id")));
            entidad.setNombre(resultSet.getString("cliente_nombre"));
            entidad.setApellido(resultSet.getString("cliente_apellido"));
            entidad.setCorreoElectronico(resultSet.getString("cliente_correo_electronico"));
            entidad.setContrasena(resultSet.getString("cliente_contrasena"));
            entidad.setTelefono(resultSet.getString("cliente_telefono"));
            entidad.setEstado(resultSet.getBoolean("cliente_estado"));
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
