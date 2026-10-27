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
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ProductoNegocioImpl implements ProductoNegocio {

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
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCategoria().getId(),
                "categoría asociada al producto");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), "nombre del producto");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), "nombre del producto", 3, 100);

        if (!UtilTexto.estaVacia(dominio.getDescripcion())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(),
                    "descripción del producto", 0, 1000);
        }

        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getPrecio(), "precio del producto");
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getStock(), "stock del producto");
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

    private void borrar(final UUID id) {
        fabrica.obtenerProductoDAO().eliminar(id);
    }
}
