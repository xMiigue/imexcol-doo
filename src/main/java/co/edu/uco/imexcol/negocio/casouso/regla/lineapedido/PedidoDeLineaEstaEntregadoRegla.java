package co.edu.uco.imexcol.negocio.casouso.regla.lineapedido;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPedidoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PedidoDeLineaEstaEntregadoRegla implements Regla {

    private static final PedidoDeLineaEstaEntregadoRegla INSTANCIA = new PedidoDeLineaEstaEntregadoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_LINEA = 0;
    private static final int POSICION_FABRICA = 1;

    private PedidoDeLineaEstaEntregadoRegla() {
        super();
    }

    public static PedidoDeLineaEstaEntregadoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idLinea = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_LINEA]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var idPedido = fabrica.obtenerLineaPedidoDAO().consultarPorId(idLinea).getPedido().getId();
        final var estadoPedido = fabrica.obtenerPedidoDAO().consultarPorId(idPedido).getEstado();
        if (!EstadoPedidoEnum.ENTREGADO.esIgualA(estadoPedido)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_LINEA_PEDIDO_PEDIDO_NO_ENTREGADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_LINEA_PEDIDO_PEDIDO_NO_ENTREGADO.getContenido()
                            .formatted(idLinea, idPedido, estadoPedido, EstadoPedidoEnum.ENTREGADO.getValor()));
        }
    }
}
