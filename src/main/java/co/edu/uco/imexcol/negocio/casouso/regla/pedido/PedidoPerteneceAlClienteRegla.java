package co.edu.uco.imexcol.negocio.casouso.regla.pedido;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PedidoPerteneceAlClienteRegla implements Regla {

    private static final PedidoPerteneceAlClienteRegla INSTANCIA = new PedidoPerteneceAlClienteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_PEDIDO = 0;
    private static final int POSICION_ID_CLIENTE = 1;
    private static final int POSICION_FABRICA = 2;

    private PedidoPerteneceAlClienteRegla() {
        super();
    }

    public static PedidoPerteneceAlClienteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idPedido = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PEDIDO]);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var idPropietario = consultarIdClientePropietario(idPedido, fabrica);
        if (!idPropietario.equals(idCliente)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PEDIDO_NO_PERTENECE_AL_CLIENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PEDIDO_NO_PERTENECE_AL_CLIENTE.getContenido()
                            .formatted(idPedido, idPropietario, idCliente));
        }
    }

    private static UUID consultarIdClientePropietario(final UUID idPedido, final FabricaDAO fabrica) {
        return fabrica.obtenerPedidoDAO().consultarPorId(idPedido).getCliente().getId();
    }
}
