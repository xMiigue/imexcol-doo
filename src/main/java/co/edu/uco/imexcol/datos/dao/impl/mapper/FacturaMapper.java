package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import co.edu.uco.imexcol.datos.dao.impl.sql.FacturaSql;
import co.edu.uco.imexcol.entidad.FacturaEntidad;
import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class FacturaMapper {

    private FacturaMapper() {
        super();
    }

    public static FacturaEntidad mapear(final ResultSet resultSet) {
        final var entidad = new FacturaEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(FacturaSql.COLUMNA_ID)));

            final var pedido = new PedidoEntidad();
            pedido.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(FacturaSql.COLUMNA_PEDIDO_ID)));

            final var pago = new PagoEntidad();
            pago.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(FacturaSql.COLUMNA_PAGO_ID)));
            pago.setPedido(pedido);
            entidad.setPago(pago);

            entidad.setNumeroFactura(resultSet.getString(FacturaSql.COLUMNA_NUMERO_FACTURA));

            final Timestamp fechaEmisionSql = resultSet.getTimestamp(FacturaSql.COLUMNA_FECHA_EMISION);
            entidad.setFechaEmision(UtilObjeto.esNulo(fechaEmisionSql)
                    ? UtilFecha.obtenerFechaHoraPorDefecto()
                    : fechaEmisionSql.toLocalDateTime());

            entidad.setSubtotal(resultSet.getDouble(FacturaSql.COLUMNA_SUBTOTAL));
            entidad.setTotalImpuestos(resultSet.getDouble(FacturaSql.COLUMNA_TOTAL_IMPUESTOS));
            entidad.setTotalFacturado(resultSet.getDouble(FacturaSql.COLUMNA_TOTAL_FACTURADO));
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
