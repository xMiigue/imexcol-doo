package co.edu.uco.imexcol.negocio.casouso.regla.pedido;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPedidoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class TransicionEstadoPedidoEsValidaRegla implements Regla {

    private static final TransicionEstadoPedidoEsValidaRegla INSTANCIA = new TransicionEstadoPedidoEsValidaRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_PEDIDO = 0;
    private static final int POSICION_ESTADO_NUEVO = 1;
    private static final int POSICION_FABRICA = 2;

    private TransicionEstadoPedidoEsValidaRegla() {
        super();
    }

    public static TransicionEstadoPedidoEsValidaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idPedido = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PEDIDO]);
        final var estadoNuevo = UtilTexto.aplicarTrim((String) datos[POSICION_ESTADO_NUEVO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var estadoActual = fabrica.obtenerPedidoDAO().consultarPorId(idPedido).getEstado();
        if (!EstadoPedidoEnum.esTransicionValida(estadoActual, estadoNuevo)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PEDIDO_TRANSICION_ESTADO_INVALIDA.getContenido()
                            .formatted(estadoActual, estadoNuevo),
                    MensajesEnum.ERROR_TECNICO_PEDIDO_TRANSICION_ESTADO_INVALIDA.getContenido()
                            .formatted(idPedido, estadoActual, estadoNuevo));
        }
    }
}
