package co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto;

import java.util.List;
import java.util.Locale;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ExtensionFotoPermitidaRegla implements Regla {

    private static final ExtensionFotoPermitidaRegla INSTANCIA = new ExtensionFotoPermitidaRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 1;
    private static final int POSICION_FOTO = 0;
    private static final List<String> EXTENSIONES_PERMITIDAS = List.of(".jpg", ".jpeg", ".png", ".webp");
    private static final String SEPARADOR_EXTENSIONES = ", ";

    private ExtensionFotoPermitidaRegla() {
        super();
    }

    public static ExtensionFotoPermitidaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var foto = UtilTexto.aplicarTrim((String) datos[POSICION_FOTO]);

        if (!tieneExtensionPermitida(foto)) {
            final var extensiones = String.join(SEPARADOR_EXTENSIONES, EXTENSIONES_PERMITIDAS);
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_IMAGEN_PRODUCTO_EXTENSION_NO_PERMITIDA.getContenido()
                            .formatted(extensiones),
                    MensajesEnum.ERROR_TECNICO_IMAGEN_PRODUCTO_EXTENSION_NO_PERMITIDA.getContenido()
                            .formatted(foto, extensiones));
        }
    }

    private static boolean tieneExtensionPermitida(final String foto) {
        final var fotoEnMinusculas = foto.toLowerCase(Locale.ROOT);
        return EXTENSIONES_PERMITIDAS.stream().anyMatch(fotoEnMinusculas::endsWith);
    }
}
