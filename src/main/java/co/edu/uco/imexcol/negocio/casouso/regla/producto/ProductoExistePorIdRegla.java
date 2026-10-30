package co.edu.uco.imexcol.negocio.casouso.regla.producto;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ProductoExistePorIdRegla implements Regla {

    private static final ProductoExistePorIdRegla INSTANCIA = new ProductoExistePorIdRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID = 0;
    private static final int POSICION_FABRICA = 1;

    private ProductoExistePorIdRegla() {
        super();
    }

    public static ProductoExistePorIdRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var id = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (!existe(id, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PRODUCTO_NO_ENCONTRADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PRODUCTO_NO_ENCONTRADO.getContenido().formatted(id));
        }
    }

    private static boolean existe(final UUID id, final FabricaDAO fabrica) {
        return !UtilUUID.esUUIDPorDefecto(id)
                && !UtilUUID.esUUIDPorDefecto(fabrica.obtenerProductoDAO().consultarPorId(id).getId());
    }
}
