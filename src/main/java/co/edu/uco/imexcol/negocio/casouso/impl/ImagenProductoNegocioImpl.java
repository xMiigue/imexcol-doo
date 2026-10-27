package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.ImagenProductoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.ImagenProductoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto.ValidarExtensionFotoPermitida;
import co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto.ValidarFotoImagenProductoNoExiste;
import co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto.ValidarImagenProductoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto.ValidarImagenProductoPerteneceAlProducto;
import co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto.ValidarOrdenVisualizacionDisponible;
import co.edu.uco.imexcol.negocio.casouso.validador.imagenproducto.ValidarOrdenVisualizacionEnRango;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoConservaAlMenosUnaImagen;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoExistePorId;
import co.edu.uco.imexcol.negocio.dominio.ImagenProductoDominio;
import co.edu.uco.imexcol.negocio.dominio.ProductoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ImagenProductoNegocioImpl implements ImagenProductoNegocio {

    private static final String DATO_PRODUCTO = "producto al que pertenece la imagen";
    private static final String DATO_FOTO = "foto del producto";
    private static final int LONGITUD_MINIMA_FOTO = 5;
    private static final int LONGITUD_MAXIMA_FOTO = 255;

    private final FabricaDAO fabrica;
    private final ImagenProductoEntidadAssembler ensamblador;

    public ImagenProductoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = ImagenProductoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ImagenProductoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ImagenProductoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarOrdenEnRango(dominioSeguro);
        asegurarExtensionPermitida(dominioSeguro);
        asegurarProductoExista(dominioSeguro.getProducto().getId());
        asegurarOrdenDisponible(dominioSeguro);
        asegurarFotoNoRegistrada(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final ImagenProductoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ImagenProductoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarOrdenEnRango(dominioSeguro);
        asegurarExtensionPermitida(dominioSeguro);
        asegurarImagenExista(dominioSeguro.getId());
        asegurarProductoExista(dominioSeguro.getProducto().getId());
        asegurarImagenPertenezcaAlProducto(dominioSeguro.getId(), dominioSeguro.getProducto().getId());
        asegurarOrdenDisponible(dominioSeguro);
        asegurarFotoNoRegistrada(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID idProducto, final UUID idImagen) {
        validarIdentificadorProducto(idProducto);
        asegurarImagenExista(idImagen);
        asegurarImagenPertenezcaAlProducto(idImagen, idProducto);
        asegurarProductoConserveAlMenosUnaImagen(idProducto);
        borrar(idImagen);
    }

    @Override
    public List<ImagenProductoDominio> consultarPorProducto(final UUID idProducto) {
        validarIdentificadorProducto(idProducto);
        asegurarProductoExista(idProducto);
        final var filtro = new ImagenProductoDominio();
        filtro.setProducto(new ProductoDominio(idProducto));
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtro);
        final var entidades = fabrica.obtenerImagenProductoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final ImagenProductoDominio dominio) {
        validarIdentificadorProducto(dominio.getProducto().getId());
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getFoto(), DATO_FOTO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getFoto(), DATO_FOTO,
                LONGITUD_MINIMA_FOTO, LONGITUD_MAXIMA_FOTO);
    }

    private void validarIdentificadorProducto(final UUID idProducto) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(UtilUUID.obtenerValorDefecto(idProducto), DATO_PRODUCTO);
    }

    private void asegurarOrdenEnRango(final ImagenProductoDominio dominio) {
        ValidarOrdenVisualizacionEnRango.ejecutarValidacion(dominio.getOrdenVisualizacion());
    }

    private void asegurarExtensionPermitida(final ImagenProductoDominio dominio) {
        ValidarExtensionFotoPermitida.ejecutarValidacion(dominio.getFoto());
    }

    private void asegurarProductoExista(final UUID idProducto) {
        ValidarProductoExistePorId.ejecutarValidacion(idProducto, fabrica);
    }

    private void asegurarImagenExista(final UUID idImagen) {
        ValidarImagenProductoExistePorId.ejecutarValidacion(idImagen, fabrica);
    }

    private void asegurarImagenPertenezcaAlProducto(final UUID idImagen, final UUID idProducto) {
        ValidarImagenProductoPerteneceAlProducto.ejecutarValidacion(idImagen, idProducto, fabrica);
    }

    private void asegurarOrdenDisponible(final ImagenProductoDominio dominio) {
        ValidarOrdenVisualizacionDisponible.ejecutarValidacion(dominio.getProducto().getId(),
                dominio.getOrdenVisualizacion(), dominio.getId(), fabrica);
    }

    private void asegurarFotoNoRegistrada(final ImagenProductoDominio dominio) {
        ValidarFotoImagenProductoNoExiste.ejecutarValidacion(dominio.getFoto(), dominio.getId(), fabrica);
    }

    private void asegurarProductoConserveAlMenosUnaImagen(final UUID idProducto) {
        ValidarProductoConservaAlMenosUnaImagen.ejecutarValidacion(idProducto, fabrica);
    }

    private void asignarNuevoIdentificador(final ImagenProductoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final ImagenProductoDominio dominio) {
        fabrica.obtenerImagenProductoDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final ImagenProductoDominio dominio) {
        fabrica.obtenerImagenProductoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID idImagen) {
        fabrica.obtenerImagenProductoDAO().eliminar(idImagen);
    }
}
