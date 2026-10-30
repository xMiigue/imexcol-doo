package co.edu.uco.imexcol.negocio.casouso.regla.pedido;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PedidoNoTieneLineasRegla implements Regla {

    private static final PedidoNoTieneLineasRegla INSTANCIA = new PedidoNoTieneLineasRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_PEDIDO = 0;
    private static final int POSICION_FABRICA = 1;

    private PedidoNoTieneLineasRegla() {
        super();
    }

    public static PedidoNoTieneLineasRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idPedido = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PEDIDO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var asociados = consultarLineasDelPedido(idPedido, fabrica);
        if (!asociados.isEmpty()) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PEDIDO_CON_LINEAS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PEDIDO_CON_LINEAS.getContenido()
                            .formatted(idPedido, asociados.size()));
        }
    }

    private static List<LineaPedidoEntidad> consultarLineasDelPedido(final UUID idPedido, final FabricaDAO fabrica) {
        final var filtro = new LineaPedidoEntidad();
        filtro.setPedido(new PedidoEntidad(idPedido));
        return fabrica.obtenerLineaPedidoDAO().consultarPorFiltro(filtro);
    }
}
