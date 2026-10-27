package co.edu.uco.imexcol.negocio.casouso.regla.lineapedido;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PrecioLineaPedidoNoCambiaRegla implements Regla {

    private static final PrecioLineaPedidoNoCambiaRegla INSTANCIA = new PrecioLineaPedidoNoCambiaRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_PRECIO_NUEVO = 0;
    private static final int POSICION_ID_LINEA = 1;
    private static final int POSICION_FABRICA = 2;

    private PrecioLineaPedidoNoCambiaRegla() {
        super();
    }

    public static PrecioLineaPedidoNoCambiaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var precioNuevo = UtilNumerico.obtenerValorDefecto((Double) datos[POSICION_PRECIO_NUEVO]);
        final var idLinea = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_LINEA]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var precioCongelado = fabrica.obtenerLineaPedidoDAO().consultarPorId(idLinea).getPrecioUnitario();
        if (Double.compare(precioNuevo, precioCongelado) != 0) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_LINEA_PEDIDO_PRECIO_CONGELADO_MODIFICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_LINEA_PEDIDO_PRECIO_CONGELADO_MODIFICADO.getContenido()
                            .formatted(idLinea, precioCongelado, precioNuevo));
        }
    }
}
