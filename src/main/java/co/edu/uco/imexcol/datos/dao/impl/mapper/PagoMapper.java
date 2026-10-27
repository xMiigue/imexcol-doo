package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.datos.dao.impl.sql.PagoSql;
import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;
import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class PagoMapper {

    private PagoMapper() {
        super();
    }

    public static PagoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new PagoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(PagoSql.COLUMNA_ID)));

            final var pedido = new PedidoEntidad();
            pedido.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(PagoSql.COLUMNA_PEDIDO_ID)));
            entidad.setPedido(pedido);

            final var metodoPago = new MetodoPagoEntidad();
            metodoPago.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(PagoSql.COLUMNA_METODO_PAGO_ID)));
            entidad.setMetodoPago(metodoPago);

            entidad.setMonto(resultSet.getDouble(PagoSql.COLUMNA_MONTO));

            final Date fechaSql = resultSet.getDate(PagoSql.COLUMNA_FECHA_PAGO);
            entidad.setFechaPago(UtilObjeto.esNulo(fechaSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaSql.toLocalDate());

            entidad.setEstado(resultSet.getString(PagoSql.COLUMNA_ESTADO));
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
