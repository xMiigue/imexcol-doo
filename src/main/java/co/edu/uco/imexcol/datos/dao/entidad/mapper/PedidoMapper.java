package co.edu.uco.imexcol.datos.dao.entidad.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class PedidoMapper {

    private PedidoMapper() {
        super();
    }

    public static PedidoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new PedidoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("pedido_id")));

            final var cliente = new ClienteEntidad();
            cliente.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("pedido_cliente_id")));
            entidad.setCliente(cliente);

            final Date fechaSql = resultSet.getDate("pedido_fecha_pedido");
            entidad.setFechaPedido(UtilObjeto.esNulo(fechaSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaSql.toLocalDate());

            entidad.setTotal(resultSet.getDouble("pedido_total"));
            entidad.setEstado(resultSet.getString("pedido_estado"));
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
