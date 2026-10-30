package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.PedidoSql;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class PedidoMapper {

    private PedidoMapper() {
        super();
    }

    public static PedidoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new PedidoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(PedidoSql.COLUMNA_ID)));

            final var cliente = new ClienteEntidad();
            cliente.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(PedidoSql.COLUMNA_CLIENTE_ID)));
            entidad.setCliente(cliente);

            final Date fechaSql = resultSet.getDate(PedidoSql.COLUMNA_FECHA_PEDIDO);
            entidad.setFechaPedido(UtilObjeto.esNulo(fechaSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaSql.toLocalDate());

            entidad.setTotal(resultSet.getDouble(PedidoSql.COLUMNA_TOTAL));
            entidad.setEstado(resultSet.getString(PedidoSql.COLUMNA_ESTADO));
            entidad.setDireccion(new DireccionEntidad(
                    UtilUUID.obtenerDesdeTexto(resultSet.getString(PedidoSql.COLUMNA_DIRECCION_ID))));
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
