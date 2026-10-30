package co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ImagenProductoPerteneceAlProductoRegla implements Regla {

    private static final ImagenProductoPerteneceAlProductoRegla INSTANCIA =
            new ImagenProductoPerteneceAlProductoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_IMAGEN = 0;
    private static final int POSICION_ID_PRODUCTO = 1;
    private static final int POSICION_FABRICA = 2;

    private ImagenProductoPerteneceAlProductoRegla() {
        super();
    }

    public static ImagenProductoPerteneceAlProductoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idImagen = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_IMAGEN]);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var idProductoPropietario = consultarIdProductoPropietario(idImagen, fabrica);
        if (!idProductoPropietario.equals(idProducto)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_IMAGEN_PRODUCTO_NO_PERTENECE_AL_PRODUCTO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_IMAGEN_PRODUCTO_NO_PERTENECE_AL_PRODUCTO.getContenido()
                            .formatted(idImagen, idProductoPropietario, idProducto));
        }
    }

    private static UUID consultarIdProductoPropietario(final UUID idImagen, final FabricaDAO fabrica) {
        return fabrica.obtenerImagenProductoDAO().consultarPorId(idImagen).getProducto().getId();
    }
}
