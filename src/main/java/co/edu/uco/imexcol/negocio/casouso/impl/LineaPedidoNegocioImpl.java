package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.LineaPedidoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.LineaPedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.casouso.validador.lineapedido.ValidarLineaPedidoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.lineapedido.ValidarPedidoDeLineaEstaEntregado;
import co.edu.uco.imexcol.negocio.casouso.validador.lineapedido.ValidarPrecioLineaPedidoCoincideConPrecioProducto;
import co.edu.uco.imexcol.negocio.casouso.validador.lineapedido.ValidarPrecioLineaPedidoNoCambia;
import co.edu.uco.imexcol.negocio.casouso.validador.lineapedido.ValidarSubtotalLineaPedidoEsCoherente;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoExistePorId;
import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class LineaPedidoNegocioImpl implements LineaPedidoNegocio {

    private static final String DATO_PEDIDO = "pedido asociado a la línea";
    private static final String DATO_PRODUCTO = "producto asociado a la línea";
    private static final String DATO_CANTIDAD = "cantidad de la línea del pedido";
    private static final String DATO_PRECIO_UNITARIO = "precio unitario de la línea del pedido";
    private static final String DATO_SUBTOTAL = "subtotal de la línea del pedido";

    private final FabricaDAO fabrica;
    private final LineaPedidoEntidadAssembler ensamblador;

    public LineaPedidoNegocioImpl(final FabricaDAO fabrica) {
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        super();
        this.fabrica = fabrica;
        this.ensamblador = LineaPedidoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarPedidoExista(dominioSeguro);
        asegurarProductoExista(dominioSeguro);
        asegurarPrecioCongeladoIgualAlDelProducto(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarLineaExista(dominioSeguro.getId());
        asegurarPedidoExista(dominioSeguro);
        asegurarProductoExista(dominioSeguro);
        asegurarPrecioCongeladoNoCambie(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarLineaExista(id);
        asegurarPedidoDeLineaEsteEntregado(id);
        borrar(id);
    }

    @Override
    public List<LineaPedidoDominio> consultar(final LineaPedidoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new LineaPedidoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerLineaPedidoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final LineaPedidoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(), DATO_PEDIDO);
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getProducto().getId(), DATO_PRODUCTO);
        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getCantidad(), DATO_CANTIDAD);
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getPrecioUnitario(), DATO_PRECIO_UNITARIO);
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getSubtotal(), DATO_SUBTOTAL);
    }

    private void validarCoherenciaSubtotal(final LineaPedidoDominio dominio) {
        ValidarSubtotalLineaPedidoEsCoherente.ejecutarValidacion(dominio.getCantidad(), dominio.getPrecioUnitario(),
                dominio.getSubtotal());
    }

    private void asegurarPedidoExista(final LineaPedidoDominio dominio) {
        ValidarPedidoExistePorId.ejecutarValidacion(dominio.getPedido().getId(), fabrica);
    }

    private void asegurarProductoExista(final LineaPedidoDominio dominio) {
        ValidarProductoExistePorId.ejecutarValidacion(dominio.getProducto().getId(), fabrica);
    }

    private void asegurarPrecioCongeladoIgualAlDelProducto(final LineaPedidoDominio dominio) {
        ValidarPrecioLineaPedidoCoincideConPrecioProducto.ejecutarValidacion(dominio.getPrecioUnitario(),
                dominio.getProducto().getId(), fabrica);
    }

    private void asegurarPrecioCongeladoNoCambie(final LineaPedidoDominio dominio) {
        ValidarPrecioLineaPedidoNoCambia.ejecutarValidacion(dominio.getPrecioUnitario(), dominio.getId(), fabrica);
    }

    private void asegurarLineaExista(final UUID id) {
        ValidarLineaPedidoExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asegurarPedidoDeLineaEsteEntregado(final UUID id) {
        ValidarPedidoDeLineaEstaEntregado.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final LineaPedidoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final LineaPedidoDominio dominio) {
        fabrica.obtenerLineaPedidoDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final LineaPedidoDominio dominio) {
        fabrica.obtenerLineaPedidoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerLineaPedidoDAO().eliminar(id);
    }
}
