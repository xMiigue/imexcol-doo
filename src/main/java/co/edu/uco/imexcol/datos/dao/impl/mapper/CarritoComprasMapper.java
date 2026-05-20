package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.CarritoComprasEntidad;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class CarritoComprasMapper {

    private CarritoComprasMapper() {
        super();
    }

    public static CarritoComprasEntidad mapear(final ResultSet resultSet) {
        final var entidad = new CarritoComprasEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("carrito_compras_id")));

            final var cliente = new ClienteEntidad();
            cliente.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("carrito_compras_cliente_id")));
            entidad.setCliente(cliente);

            final Date fechaSql = resultSet.getDate("carrito_compras_fecha_creacion");
            entidad.setFechaCreacion(UtilObjeto.esNulo(fechaSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaSql.toLocalDate());

            entidad.setEstado(resultSet.getString("carrito_compras_estado"));
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
