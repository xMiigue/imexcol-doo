package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.ImagenProductoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.ImagenProductoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ImagenProductoNegocioImpl implements ImagenProductoNegocio {

    private static final String CASO_USO = "ImagenProductoNegocio";

    public ImagenProductoNegocioImpl() {
        super();
    }

    @Override
    public void registrar(final ImagenProductoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ImagenProductoDominio());
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final ImagenProductoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ImagenProductoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "imagen de producto");
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "imagen de producto");
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<ImagenProductoDominio> consultar(final ImagenProductoDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new ImagenProductoDominio());
        return new ArrayList<>();
    }

    private void validarDatosComunes(final ImagenProductoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getProducto().getId(),
                "producto asociado a la imagen");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getUrl(), "URL de la imagen");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getUrl(), "URL de la imagen", 10, 500);

        if (!UtilTexto.estaVacia(dominio.getDescripcion())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(),
                    "descripción de la imagen", 0, 255);
        }
    }

    private static ImexcolNegocioException operacionPendiente(final String operacion) {
        return ImexcolNegocioException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO));
    }
}
