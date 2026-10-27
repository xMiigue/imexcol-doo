package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.MetodoPagoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.CarritoComprasNegocio;
import co.edu.uco.imexcol.negocio.casouso.CompraNegocio;
import co.edu.uco.imexcol.negocio.casouso.FacturaNegocio;
import co.edu.uco.imexcol.negocio.casouso.ItemCarritoNegocio;
import co.edu.uco.imexcol.negocio.casouso.LineaPedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.PagoNegocio;
import co.edu.uco.imexcol.negocio.casouso.PedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasEstaActivo;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasPerteneceAlCliente;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasTieneItemsParaConvertirse;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.direccion.ValidarDireccionExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.direccion.ValidarDireccionPerteneceAlCliente;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.metodopago.ValidarMetodoPagoEstaActivo;
import co.edu.uco.imexcol.negocio.casouso.validador.metodopago.ValidarMetodoPagoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoExistePorId;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.negocio.dominio.ClienteDominio;
import co.edu.uco.imexcol.negocio.dominio.FacturaDominio;
import co.edu.uco.imexcol.negocio.dominio.ItemCarritoDominio;
import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;
import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.negocio.dominio.PagoDominio;
import co.edu.uco.imexcol.negocio.dominio.PedidoDominio;
import co.edu.uco.imexcol.negocio.dominio.ResultadoCompraDominio;
import co.edu.uco.imexcol.negocio.dominio.SolicitudCompraDominio;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoCarritoComprasEnum;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPagoEnum;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPedidoEnum;
import co.edu.uco.imexcol.negocio.pasarela.PasarelaPagos;
import co.edu.uco.imexcol.negocio.pasarela.impl.PasarelaPagosSimulada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CompraNegocioImpl implements CompraNegocio {

    private static final String DATO_CLIENTE = "cliente que realiza la compra";
    private static final String DATO_CARRITO = "carrito de compras";
    private static final String DATO_DIRECCION = "dirección de entrega";
    private static final String DATO_METODO_PAGO = "método de pago";

    private final FabricaDAO fabrica;
    private final PasarelaPagos pasarela;
    private final CarritoComprasNegocio carritoNegocio;
    private final ItemCarritoNegocio itemCarritoNegocio;
    private final PedidoNegocio pedidoNegocio;
    private final LineaPedidoNegocio lineaPedidoNegocio;
    private final PagoNegocio pagoNegocio;
    private final FacturaNegocio facturaNegocio;

    public CompraNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.pasarela = PasarelaPagosSimulada.obtenerInstancia();
        this.carritoNegocio = new CarritoComprasNegocioImpl(fabrica);
        this.itemCarritoNegocio = new ItemCarritoNegocioImpl(fabrica);
        this.pedidoNegocio = new PedidoNegocioImpl(fabrica);
        this.lineaPedidoNegocio = new LineaPedidoNegocioImpl(fabrica);
        this.pagoNegocio = new PagoNegocioImpl(fabrica);
        this.facturaNegocio = new FacturaNegocioImpl(fabrica);
    }

    @Override
    public ResultadoCompraDominio realizarCompra(final SolicitudCompraDominio solicitud) {
        final var solicitudSegura = UtilObjeto.obtenerValorDefecto(solicitud, new SolicitudCompraDominio());
        validarDatosSolicitud(solicitudSegura);
        asegurarClienteExista(solicitudSegura);
        asegurarCarritoListoParaComprar(solicitudSegura);
        asegurarDireccionDelCliente(solicitudSegura);
        asegurarMetodoPagoActivo(solicitudSegura);
        final var items = consultarItemsDelCarrito(solicitudSegura);
        asegurarProductosExistan(items);
        final var pedido = registrarPedidoConPreciosCongelados(solicitudSegura, items);
        marcarCarritoComoConvertido(solicitudSegura);
        final var estadoCobro = solicitarCobro(solicitudSegura, pedido);
        asegurarCobroAprobado(estadoCobro, pedido);
        registrarPagoAprobado(solicitudSegura, pedido);
        confirmarPedido(pedido);
        final var factura = generarFactura(pedido);
        return construirResultado(pedido, factura);
    }

    private static void validarDatosSolicitud(final SolicitudCompraDominio solicitud) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(solicitud.getIdCliente(), DATO_CLIENTE);
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(solicitud.getIdCarrito(), DATO_CARRITO);
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(solicitud.getIdDireccion(), DATO_DIRECCION);
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(solicitud.getIdMetodoPago(), DATO_METODO_PAGO);
    }

    private void asegurarClienteExista(final SolicitudCompraDominio solicitud) {
        ValidarClienteExistePorId.ejecutarValidacion(solicitud.getIdCliente(), fabrica);
    }

    private void asegurarCarritoListoParaComprar(final SolicitudCompraDominio solicitud) {
        ValidarCarritoComprasExistePorId.ejecutarValidacion(solicitud.getIdCarrito(), fabrica);
        ValidarCarritoComprasPerteneceAlCliente.ejecutarValidacion(solicitud.getIdCarrito(),
                solicitud.getIdCliente(), fabrica);
        ValidarCarritoComprasEstaActivo.ejecutarValidacion(solicitud.getIdCarrito(), fabrica);
        ValidarCarritoComprasTieneItemsParaConvertirse.ejecutarValidacion(
                construirCarritoAConvertir(solicitud.getIdCarrito()), fabrica);
    }

    private void asegurarDireccionDelCliente(final SolicitudCompraDominio solicitud) {
        ValidarDireccionExistePorId.ejecutarValidacion(solicitud.getIdDireccion(), fabrica);
        ValidarDireccionPerteneceAlCliente.ejecutarValidacion(solicitud.getIdDireccion(),
                solicitud.getIdCliente(), fabrica);
    }

    private void asegurarMetodoPagoActivo(final SolicitudCompraDominio solicitud) {
        ValidarMetodoPagoExistePorId.ejecutarValidacion(solicitud.getIdMetodoPago(), fabrica);
        ValidarMetodoPagoEstaActivo.ejecutarValidacion(solicitud.getIdMetodoPago(), fabrica);
    }

    private List<ItemCarritoDominio> consultarItemsDelCarrito(final SolicitudCompraDominio solicitud) {
        final var filtro = new ItemCarritoDominio();
        filtro.setCarrito(new CarritoComprasDominio(solicitud.getIdCarrito()));
        return itemCarritoNegocio.consultar(filtro);
    }

    private void asegurarProductosExistan(final List<ItemCarritoDominio> items) {
        items.forEach(item -> ValidarProductoExistePorId.ejecutarValidacion(item.getProducto().getId(), fabrica));
    }

    private PedidoDominio registrarPedidoConPreciosCongelados(final SolicitudCompraDominio solicitud,
            final List<ItemCarritoDominio> items) {
        final var lineas = items.stream().map(this::construirLineaConPrecioCongelado).toList();
        final var pedido = construirPedidoPendiente(solicitud, calcularTotal(lineas));
        pedidoNegocio.registrar(pedido);
        lineas.forEach(linea -> registrarLinea(pedido, linea));
        return pedido;
    }

    private LineaPedidoDominio construirLineaConPrecioCongelado(final ItemCarritoDominio item) {
        final var precioCongelado = consultarPrecioActual(item.getProducto().getId());
        final var linea = new LineaPedidoDominio();
        linea.setProducto(item.getProducto());
        linea.setCantidad(item.getCantidad());
        linea.setPrecioUnitario(precioCongelado);
        linea.setSubtotal(UtilNumerico.multiplicarADosDecimales(precioCongelado, item.getCantidad()));
        return linea;
    }

    private double consultarPrecioActual(final UUID idProducto) {
        return fabrica.obtenerProductoDAO().consultarPorId(idProducto).getPrecio();
    }

    private static double calcularTotal(final List<LineaPedidoDominio> lineas) {
        return UtilNumerico.redondearADosDecimales(
                lineas.stream().mapToDouble(LineaPedidoDominio::getSubtotal).sum());
    }

    private static PedidoDominio construirPedidoPendiente(final SolicitudCompraDominio solicitud,
            final double total) {
        final var pedido = new PedidoDominio();
        pedido.setCliente(new ClienteDominio(solicitud.getIdCliente()));
        pedido.setFechaPedido(UtilFecha.obtenerFechaActual());
        pedido.setTotal(total);
        pedido.setEstado(EstadoPedidoEnum.PENDIENTE.getValor());
        return pedido;
    }

    private void registrarLinea(final PedidoDominio pedido, final LineaPedidoDominio linea) {
        linea.setPedido(pedido);
        lineaPedidoNegocio.registrar(linea);
    }

    private void marcarCarritoComoConvertido(final SolicitudCompraDominio solicitud) {
        final var carrito = consultarCarrito(solicitud.getIdCarrito());
        carrito.setEstado(EstadoCarritoComprasEnum.CONVERTIDO.getValor());
        carritoNegocio.actualizar(carrito);
    }

    private CarritoComprasDominio consultarCarrito(final UUID idCarrito) {
        return carritoNegocio.consultar(new CarritoComprasDominio(idCarrito)).stream()
                .findFirst()
                .orElse(new CarritoComprasDominio());
    }

    private EstadoPagoEnum solicitarCobro(final SolicitudCompraDominio solicitud, final PedidoDominio pedido) {
        final var metodoPago = consultarMetodoPago(solicitud.getIdMetodoPago());
        final var respuestaPasarela = pasarela.solicitarCobro(metodoPago, pedido.getTotal());
        return UtilObjeto.obtenerValorDefecto(respuestaPasarela, EstadoPagoEnum.RECHAZADO);
    }

    private MetodoPagoDominio consultarMetodoPago(final UUID idMetodoPago) {
        final var entidad = fabrica.obtenerMetodoPagoDAO().consultarPorId(idMetodoPago);
        return MetodoPagoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidad);
    }

    private static void asegurarCobroAprobado(final EstadoPagoEnum estadoCobro, final PedidoDominio pedido) {
        if (!EstadoPagoEnum.APROBADO.equals(estadoCobro)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_COMPRA_PAGO_RECHAZADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_COMPRA_PAGO_RECHAZADO.getContenido()
                            .formatted(estadoCobro.getValor(), pedido.getTotal()));
        }
    }

    private void registrarPagoAprobado(final SolicitudCompraDominio solicitud, final PedidoDominio pedido) {
        final var pago = new PagoDominio();
        pago.setPedido(pedido);
        pago.setMetodoPago(new MetodoPagoDominio(solicitud.getIdMetodoPago()));
        pago.setMonto(pedido.getTotal());
        pago.setFechaPago(UtilFecha.obtenerFechaActual());
        pago.setEstado(EstadoPagoEnum.APROBADO.getValor());
        pagoNegocio.registrar(pago);
    }

    private void confirmarPedido(final PedidoDominio pedido) {
        pedido.setEstado(EstadoPedidoEnum.CONFIRMADO.getValor());
        pedidoNegocio.actualizar(pedido);
    }

    private FacturaDominio generarFactura(final PedidoDominio pedido) {
        facturaNegocio.registrarParaPedido(pedido.getId(), pedido.getTotal());
        return facturaNegocio.consultarPorPedido(pedido.getId());
    }

    private static CarritoComprasDominio construirCarritoAConvertir(final UUID idCarrito) {
        final var carrito = new CarritoComprasDominio(idCarrito);
        carrito.setEstado(EstadoCarritoComprasEnum.CONVERTIDO.getValor());
        return carrito;
    }

    private static ResultadoCompraDominio construirResultado(final PedidoDominio pedido,
            final FacturaDominio factura) {
        return new ResultadoCompraDominio(pedido.getId(), factura.getNumeroFactura(), pedido.getTotal(),
                pedido.getEstado());
    }
}
