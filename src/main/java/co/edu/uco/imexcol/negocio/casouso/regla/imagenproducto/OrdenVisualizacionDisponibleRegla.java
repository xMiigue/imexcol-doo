package co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ImagenProductoEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class OrdenVisualizacionDisponibleRegla implements Regla {

    private static final OrdenVisualizacionDisponibleRegla INSTANCIA = new OrdenVisualizacionDisponibleRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 4;
    private static final int POSICION_ID_PRODUCTO = 0;
    private static final int POSICION_ORDEN = 1;
    private static final int POSICION_ID_IMAGEN = 2;
    private static final int POSICION_FABRICA = 3;

    private OrdenVisualizacionDisponibleRegla() {
        super();
    }

    public static OrdenVisualizacionDisponibleRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var orden = ((Number) datos[POSICION_ORDEN]).intValue();
        final var idImagen = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_IMAGEN]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var imagenQueOcupaElOrden = buscarOtraImagenConElOrden(idProducto, orden, idImagen, fabrica);
        if (!UtilUUID.esUUIDPorDefecto(imagenQueOcupaElOrden.getId())) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_IMAGEN_PRODUCTO_ORDEN_OCUPADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_IMAGEN_PRODUCTO_ORDEN_OCUPADO.getContenido()
                            .formatted(idProducto, imagenQueOcupaElOrden.getId(), orden));
        }
    }

    private static ImagenProductoEntidad buscarOtraImagenConElOrden(final UUID idProducto, final int orden,
            final UUID idImagen, final FabricaDAO fabrica) {
        final var filtro = new ImagenProductoEntidad();
        filtro.setProducto(new ProductoEntidad(idProducto));
        return fabrica.obtenerImagenProductoDAO().consultarPorFiltro(filtro).stream()
                .filter(imagen -> !idImagen.equals(imagen.getId()))
                .filter(imagen -> imagen.getOrdenVisualizacion() == orden)
                .findFirst()
                .orElse(new ImagenProductoEntidad());
    }
}
