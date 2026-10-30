package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.EnvioSql;
import co.edu.uco.imexcol.entidad.EnvioEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class EnvioMapper {

    private EnvioMapper() {
        super();
    }

    public static EnvioEntidad mapear(final ResultSet resultSet) {
        final var entidad = new EnvioEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(EnvioSql.COLUMNA_ID)));

            final var pedido = new PedidoEntidad();
            pedido.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(EnvioSql.COLUMNA_PEDIDO_ID)));
            entidad.setPedido(pedido);

            final Date fechaEnvioSql = resultSet.getDate(EnvioSql.COLUMNA_FECHA_ENVIO);
            entidad.setFechaEnvio(UtilObjeto.esNulo(fechaEnvioSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaEnvioSql.toLocalDate());

            final Date fechaEntregaSql = resultSet.getDate(EnvioSql.COLUMNA_FECHA_ENTREGA);
            entidad.setFechaEntrega(UtilObjeto.esNulo(fechaEntregaSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaEntregaSql.toLocalDate());

            entidad.setTransportadora(resultSet.getString(EnvioSql.COLUMNA_TRANSPORTADORA));
            entidad.setNumeroGuia(resultSet.getString(EnvioSql.COLUMNA_NUMERO_GUIA));
            entidad.setEstado(resultSet.getString(EnvioSql.COLUMNA_ESTADO));
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
