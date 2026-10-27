package co.edu.uco.imexcol.negocio.casouso.regla.lineapedido;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PrecioLineaPedidoCoincideConPrecioProductoRegla implements Regla {

    private static final PrecioLineaPedidoCoincideConPrecioProductoRegla INSTANCIA =
            new PrecioLineaPedidoCoincideConPrecioProductoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_PRECIO_LINEA = 0;
    private static final int POSICION_ID_PRODUCTO = 1;
    private static final int POSICION_FABRICA = 2;

    private PrecioLineaPedidoCoincideConPrecioProductoRegla() {
        super();
    }

    public static PrecioLineaPedidoCoincideConPrecioProductoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var precioLinea = UtilNumerico.obtenerValorDefecto((Double) datos[POSICION_PRECIO_LINEA]);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var precioProducto = fabrica.obtenerProductoDAO().consultarPorId(idProducto).getPrecio();
        if (Double.compare(precioLinea, precioProducto) != 0) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_LINEA_PEDIDO_PRECIO_DIFERENTE_AL_PRODUCTO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_LINEA_PEDIDO_PRECIO_DIFERENTE_AL_PRODUCTO.getContenido()
                            .formatted(precioLinea, precioProducto, idProducto));
        }
    }
}
