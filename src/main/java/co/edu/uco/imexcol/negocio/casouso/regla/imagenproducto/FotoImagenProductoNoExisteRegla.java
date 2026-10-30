package co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ImagenProductoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class FotoImagenProductoNoExisteRegla implements Regla {

    private static final FotoImagenProductoNoExisteRegla INSTANCIA = new FotoImagenProductoNoExisteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_FOTO = 0;
    private static final int POSICION_ID_IMAGEN = 1;
    private static final int POSICION_FABRICA = 2;

    private FotoImagenProductoNoExisteRegla() {
        super();
    }

    public static FotoImagenProductoNoExisteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var foto = UtilTexto.aplicarTrim((String) datos[POSICION_FOTO]);
        final var idImagen = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_IMAGEN]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var imagenConLaFoto = buscarOtraImagenConLaFoto(foto, idImagen, fabrica);
        if (!UtilUUID.esUUIDPorDefecto(imagenConLaFoto.getId())) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_IMAGEN_PRODUCTO_FOTO_DUPLICADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_IMAGEN_PRODUCTO_FOTO_DUPLICADA.getContenido()
                            .formatted(foto, imagenConLaFoto.getId()));
        }
    }

    private static ImagenProductoEntidad buscarOtraImagenConLaFoto(final String foto, final UUID idImagen,
            final FabricaDAO fabrica) {
        final var filtro = new ImagenProductoEntidad();
        filtro.setFoto(foto);
        return fabrica.obtenerImagenProductoDAO().consultarPorFiltro(filtro).stream()
                .filter(imagen -> !idImagen.equals(imagen.getId()))
                .findFirst()
                .orElse(new ImagenProductoEntidad());
    }
}
