package co.edu.uco.imexcol.negocio.casouso.regla.categoria;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CategoriaNoTieneProductosRegla implements Regla {

    private static final CategoriaNoTieneProductosRegla INSTANCIA = new CategoriaNoTieneProductosRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_CATEGORIA = 0;
    private static final int POSICION_FABRICA = 1;

    private CategoriaNoTieneProductosRegla() {
        super();
    }

    public static CategoriaNoTieneProductosRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idCategoria = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CATEGORIA]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var productos = consultarProductosDeCategoria(idCategoria, fabrica);
        if (!productos.isEmpty()) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_CATEGORIA_CON_PRODUCTOS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CATEGORIA_CON_PRODUCTOS.getContenido()
                            .formatted(idCategoria, productos.size()));
        }
    }

    private static List<ProductoEntidad> consultarProductosDeCategoria(final UUID idCategoria,
            final FabricaDAO fabrica) {
        final var filtro = new ProductoEntidad();
        filtro.setCategoria(new CategoriaEntidad(idCategoria));
        return fabrica.obtenerProductoDAO().consultarPorFiltro(filtro);
    }
}
