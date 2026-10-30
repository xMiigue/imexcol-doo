package co.edu.uco.imexcol.negocio.casouso.regla.pedido;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.FacturaEntidad;
import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PedidoNoTieneFacturaRegla implements Regla {

    private static final PedidoNoTieneFacturaRegla INSTANCIA = new PedidoNoTieneFacturaRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_PEDIDO = 0;
    private static final int POSICION_FABRICA = 1;

    private PedidoNoTieneFacturaRegla() {
        super();
    }

    public static PedidoNoTieneFacturaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idPedido = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PEDIDO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var facturaExistente = buscarFacturaDelPedido(idPedido, fabrica);
        if (!UtilUUID.esUUIDPorDefecto(facturaExistente.getId())) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PEDIDO_CON_FACTURA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PEDIDO_CON_FACTURA.getContenido()
                            .formatted(idPedido, facturaExistente.getId()));
        }
    }

    private static FacturaEntidad buscarFacturaDelPedido(final UUID idPedido, final FabricaDAO fabrica) {
        final var pago = new PagoEntidad();
        pago.setPedido(new PedidoEntidad(idPedido));
        final var filtro = new FacturaEntidad();
        filtro.setPago(pago);
        return fabrica.obtenerFacturaDAO().consultarPorFiltro(filtro).stream()
                .findFirst()
                .orElse(new FacturaEntidad());
    }
}
