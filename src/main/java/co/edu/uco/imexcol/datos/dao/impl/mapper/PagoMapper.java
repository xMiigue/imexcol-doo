package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;
import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class PagoMapper {

    private PagoMapper() {
        super();
    }

    public static PagoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new PagoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("pago_id")));

            final var pedido = new PedidoEntidad();
            pedido.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("pago_pedido_id")));
            entidad.setPedido(pedido);

            final var metodoPago = new MetodoPagoEntidad();
            metodoPago.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString("pago_metodo_pago_id")));
            entidad.setMetodoPago(metodoPago);

            entidad.setMonto(resultSet.getDouble("pago_monto"));

            final Date fechaSql = resultSet.getDate("pago_fecha_pago");
            entidad.setFechaPago(UtilObjeto.esNulo(fechaSql)
                    ? UtilFecha.obtenerFechaPorDefecto()
                    : fechaSql.toLocalDate());

            entidad.setEstado(resultSet.getString("pago_estado"));
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
