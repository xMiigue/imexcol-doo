package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.ProductoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.ProductoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.categoria.ValidarCategoriaExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoNoTienePedidosAsociados;
import co.edu.uco.imexcol.negocio.dominio.ProductoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ProductoNegocioImpl implements ProductoNegocio {

    private static final String DATO_CATEGORIA = "categoría asociada al producto";
    private static final String DATO_NOMBRE = "nombre del producto";
    private static final String DATO_DESCRIPCION = "descripción del producto";
    private static final String DATO_IMAGEN_URL = "imagen principal del producto";
    private static final String DATO_PRECIO = "precio del producto";
    private static final String DATO_STOCK = "stock del producto";
    private static final int LONGITUD_MINIMA_NOMBRE = 3;
    private static final int LONGITUD_MAXIMA_NOMBRE = 100;
    private static final int LONGITUD_MINIMA_DESCRIPCION = 0;
    private static final int LONGITUD_MAXIMA_DESCRIPCION = 1000;
    private static final int LONGITUD_MINIMA_IMAGEN_URL = 0;
    private static final int LONGITUD_MAXIMA_IMAGEN_URL = 255;

    private final FabricaDAO fabrica;
    private final ProductoEntidadAssembler ensamblador;

    public ProductoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = ProductoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ProductoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ProductoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarCategoriaExista(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final ProductoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ProductoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarProductoExista(dominioSeguro.getId());
        asegurarCategoriaExista(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarProductoExista(id);
        asegurarProductoNoTengaPedidosAsociados(id);
        borrarImagenes(id);
        borrar(id);
    }

    @Override
    public List<ProductoDominio> consultar(final ProductoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ProductoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerProductoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final ProductoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCategoria().getId(), DATO_CATEGORIA);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), DATO_NOMBRE);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), DATO_NOMBRE,
                LONGITUD_MINIMA_NOMBRE, LONGITUD_MAXIMA_NOMBRE);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(), DATO_DESCRIPCION,
                LONGITUD_MINIMA_DESCRIPCION, LONGITUD_MAXIMA_DESCRIPCION);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getImagenUrl(), DATO_IMAGEN_URL,
                LONGITUD_MINIMA_IMAGEN_URL, LONGITUD_MAXIMA_IMAGEN_URL);
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getPrecio(), DATO_PRECIO);
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getStock(), DATO_STOCK);
    }

    private void asegurarCategoriaExista(final ProductoDominio dominio) {
        ValidarCategoriaExistePorId.ejecutarValidacion(dominio.getCategoria().getId(), fabrica);
    }

    private void asegurarProductoExista(final UUID id) {
        ValidarProductoExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asegurarProductoNoTengaPedidosAsociados(final UUID id) {
        ValidarProductoNoTienePedidosAsociados.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final ProductoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final ProductoDominio dominio) {
        fabrica.obtenerProductoDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final ProductoDominio dominio) {
        fabrica.obtenerProductoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrarImagenes(final UUID id) {
        fabrica.obtenerImagenProductoDAO().eliminarPorProducto(id);
    }

    private void borrar(final UUID id) {
        fabrica.obtenerProductoDAO().eliminar(id);
    }
}
