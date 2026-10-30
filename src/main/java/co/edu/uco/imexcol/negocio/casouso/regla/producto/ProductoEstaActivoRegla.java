package co.edu.uco.imexcol.negocio.casouso.regla.producto;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ProductoEstaActivoRegla implements Regla {

    private static final ProductoEstaActivoRegla INSTANCIA = new ProductoEstaActivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_PRODUCTO = 0;
    private static final int POSICION_FABRICA = 1;

    private ProductoEstaActivoRegla() {
        super();
    }

    public static ProductoEstaActivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var producto = fabrica.obtenerProductoDAO().consultarPorId(idProducto);
        if (!producto.isEstado()) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PRODUCTO_INACTIVO.getContenido().formatted(producto.getNombre()),
                    MensajesEnum.ERROR_TECNICO_PRODUCTO_INACTIVO.getContenido().formatted(idProducto));
        }
    }
}
