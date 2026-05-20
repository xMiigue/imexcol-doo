package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class DireccionMapper {

    private DireccionMapper() {
        super();
    }

    public static DireccionEntidad mapear(final ResultSet resultSet) {
        final var entidad = new DireccionEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("direccion_id")));

            final var cliente = new ClienteEntidad();
            cliente.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("direccion_cliente_id")));
            entidad.setCliente(cliente);

            entidad.setCalle(resultSet.getString("direccion_calle"));
            entidad.setCiudad(resultSet.getString("direccion_ciudad"));
            entidad.setDepartamento(resultSet.getString("direccion_departamento"));
            entidad.setCodigoPostal(resultSet.getString("direccion_codigo_postal"));
            entidad.setPais(resultSet.getString("direccion_pais"));
            entidad.setEsPrincipal(resultSet.getBoolean("direccion_es_principal"));
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
