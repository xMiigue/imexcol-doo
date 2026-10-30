package co.edu.uco.imexcol.negocio.casouso.regla.producto;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ImagenProductoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ProductoConservaAlMenosUnaImagenRegla implements Regla {

    private static final ProductoConservaAlMenosUnaImagenRegla INSTANCIA =
            new ProductoConservaAlMenosUnaImagenRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_PRODUCTO = 0;
    private static final int POSICION_FABRICA = 1;
    private static final int CANTIDAD_MINIMA_IMAGENES = 1;

    private ProductoConservaAlMenosUnaImagenRegla() {
        super();
    }

    public static ProductoConservaAlMenosUnaImagenRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var cantidadImagenes = contarImagenes(idProducto, fabrica);
        if (cantidadImagenes <= CANTIDAD_MINIMA_IMAGENES) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PRODUCTO_DEBE_CONSERVAR_IMAGEN.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PRODUCTO_DEBE_CONSERVAR_IMAGEN.getContenido()
                            .formatted(idProducto, cantidadImagenes, CANTIDAD_MINIMA_IMAGENES));
        }
    }

    private static int contarImagenes(final UUID idProducto, final FabricaDAO fabrica) {
        final var filtro = new ImagenProductoEntidad();
        filtro.setProducto(new ProductoEntidad(idProducto));
        return fabrica.obtenerImagenProductoDAO().consultarPorFiltro(filtro).size();
    }
}
