package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.EnvioEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class EnvioMapper {

    private EnvioMapper() {
        super();
    }

    public static EnvioEntidad mapear(final ResultSet resultSet) {
        final var entidad = new EnvioEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("envio_id")));

            final var pedido = new PedidoEntidad();
            pedido.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("envio_pedido_id")));
            entidad.setPedido(pedido);

            final Date fechaEnvioSql = resultSet.getDate("envio_fecha_envio");
            entidad.setFechaEnvio(UtilObjeto.esNulo(fechaEnvioSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaEnvioSql.toLocalDate());

            final Date fechaEntregaSql = resultSet.getDate("envio_fecha_entrega");
            entidad.setFechaEntrega(UtilObjeto.esNulo(fechaEntregaSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaEntregaSql.toLocalDate());

            entidad.setTransportadora(resultSet.getString("envio_transportadora"));
            entidad.setNumeroGuia(resultSet.getString("envio_numero_guia"));
            entidad.setEstado(resultSet.getString("envio_estado"));
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
