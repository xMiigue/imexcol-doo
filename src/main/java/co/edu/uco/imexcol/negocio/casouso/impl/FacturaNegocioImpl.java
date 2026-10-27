package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.FacturaEntidad;
import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.FacturaEntidadAssembler;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.PagoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.FacturaNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.factura.ValidarNumeroFacturaNoExiste;
import co.edu.uco.imexcol.negocio.casouso.validador.factura.ValidarTotalFacturadoEsSumaDeSubtotalEImpuestos;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarPagoEstaAprobado;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarPagoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarPagoNoTieneFactura;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoNoTieneFactura;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoPerteneceAlCliente;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoTieneFactura;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoTienePagoAprobadoParaFacturar;
import co.edu.uco.imexcol.negocio.dominio.FacturaDominio;
import co.edu.uco.imexcol.negocio.dominio.PagoDominio;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPagoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilNumerico;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class FacturaNegocioImpl implements FacturaNegocio {

    private static final String DATO_PAGO = "pago asociado a la factura";
    private static final String DATO_CLIENTE = "cliente que consulta la factura";
    private static final String DATO_NUMERO_FACTURA = "número de factura";
    private static final String DATO_SUBTOTAL = "subtotal de la factura";
    private static final String DATO_TOTAL_IMPUESTOS = "total de impuestos de la factura";
    private static final String DATO_TOTAL_FACTURADO = "total facturado";
    private static final int LONGITUD_MINIMA_NUMERO_FACTURA = 5;
    private static final int LONGITUD_MAXIMA_NUMERO_FACTURA = 20;
    private static final String PREFIJO_NUMERO_FACTURA = "FE-";
    private static final String FORMATO_CONSECUTIVO_FACTURA = "%012X";
    private static final long MASCARA_CONSECUTIVO_FACTURA = 0xFFFFFFFFFFFFL;
    private static final double FACTOR_PRECIO_CON_IVA = 1.19;

    private final FabricaDAO fabrica;
    private final FacturaEntidadAssembler ensamblador;

    public FacturaNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = FacturaEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final FacturaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new FacturaDominio());
        generar(dominioSeguro);
    }

    @Override
    public void registrarParaPedido(final UUID idPedido, final double totalFacturado) {
        asegurarPedidoExista(idPedido);
        asegurarPedidoNoTengaFactura(idPedido);
        asegurarPedidoTengaPagoAprobado(idPedido);
        final var factura = construirFacturaDelPedido(idPedido, totalFacturado);
        generar(factura);
    }

    @Override
    public FacturaDominio consultarPorPedido(final UUID idPedido) {
        asegurarPedidoExista(idPedido);
        asegurarPedidoTengaFactura(idPedido);
        return buscarFacturaDelPedido(idPedido);
    }

    @Override
    public FacturaDominio consultarPorPedidoDelCliente(final UUID idPedido, final UUID idCliente) {
        validarIdentificadorCliente(idCliente);
        asegurarPedidoExista(idPedido);
        asegurarPedidoPertenezcaAlCliente(idPedido, idCliente);
        asegurarPedidoTengaFactura(idPedido);
        return buscarFacturaDelPedido(idPedido);
    }

    @Override
    public List<FacturaDominio> consultar(final FacturaDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new FacturaDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerFacturaDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void generar(final FacturaDominio factura) {
        validarDatosComunes(factura);
        asegurarPagoExista(factura);
        asegurarPagoEsteAprobado(factura);
        asegurarPagoNoTengaFactura(factura);
        asegurarTotalSeaSumaDeSubtotalEImpuestos(factura);
        asignarNuevoIdentificador(factura);
        asignarNumeroFactura(factura);
        asignarFechaEmision(factura);
        validarNumeroFactura(factura);
        asegurarNumeroFacturaNoExista(factura);
        guardar(factura);
    }

    private void validarDatosComunes(final FacturaDominio factura) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(factura.getPago().getId(), DATO_PAGO);
        ValidarNumeroEsPositivo.ejecutarValidacion(factura.getSubtotal(), DATO_SUBTOTAL);
        ValidarNumeroEsPositivo.ejecutarValidacion(factura.getTotalImpuestos(), DATO_TOTAL_IMPUESTOS);
        ValidarNumeroEsMayorACero.ejecutarValidacion(factura.getTotalFacturado(), DATO_TOTAL_FACTURADO);
    }

    private void validarIdentificadorCliente(final UUID idCliente) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(UtilUUID.obtenerValorDefecto(idCliente), DATO_CLIENTE);
    }

    private void validarNumeroFactura(final FacturaDominio factura) {
        ValidarLongitudTextoEsValida.ejecutarValidacion(factura.getNumeroFactura(), DATO_NUMERO_FACTURA,
                LONGITUD_MINIMA_NUMERO_FACTURA, LONGITUD_MAXIMA_NUMERO_FACTURA);
    }

    private void asegurarPedidoExista(final UUID idPedido) {
        ValidarPedidoExistePorId.ejecutarValidacion(idPedido, fabrica);
    }

    private void asegurarPedidoNoTengaFactura(final UUID idPedido) {
        ValidarPedidoNoTieneFactura.ejecutarValidacion(idPedido, fabrica);
    }

    private void asegurarPedidoTengaFactura(final UUID idPedido) {
        ValidarPedidoTieneFactura.ejecutarValidacion(idPedido, fabrica);
    }

    private void asegurarPedidoTengaPagoAprobado(final UUID idPedido) {
        ValidarPedidoTienePagoAprobadoParaFacturar.ejecutarValidacion(idPedido, fabrica);
    }

    private void asegurarPedidoPertenezcaAlCliente(final UUID idPedido, final UUID idCliente) {
        ValidarPedidoPerteneceAlCliente.ejecutarValidacion(idPedido, idCliente, fabrica);
    }

    private void asegurarPagoExista(final FacturaDominio factura) {
        ValidarPagoExistePorId.ejecutarValidacion(factura.getPago().getId(), fabrica);
    }

    private void asegurarPagoEsteAprobado(final FacturaDominio factura) {
        ValidarPagoEstaAprobado.ejecutarValidacion(factura.getPago().getId(), fabrica);
    }

    private void asegurarPagoNoTengaFactura(final FacturaDominio factura) {
        ValidarPagoNoTieneFactura.ejecutarValidacion(factura.getPago().getId(), fabrica);
    }

    private void asegurarTotalSeaSumaDeSubtotalEImpuestos(final FacturaDominio factura) {
        ValidarTotalFacturadoEsSumaDeSubtotalEImpuestos.ejecutarValidacion(factura.getSubtotal(),
                factura.getTotalImpuestos(), factura.getTotalFacturado());
    }

    private void asegurarNumeroFacturaNoExista(final FacturaDominio factura) {
        ValidarNumeroFacturaNoExiste.ejecutarValidacion(factura.getNumeroFactura(), fabrica);
    }

    private FacturaDominio construirFacturaDelPedido(final UUID idPedido, final double totalFacturado) {
        final var totalRedondeado = UtilNumerico.redondearADosDecimales(totalFacturado);
        final var subtotal = UtilNumerico.dividirTruncandoADosDecimales(totalRedondeado, FACTOR_PRECIO_CON_IVA);
        final var totalImpuestos = UtilNumerico.redondearADosDecimales(totalRedondeado - subtotal);

        final var factura = new FacturaDominio();
        factura.setPago(consultarPagoAprobadoDelPedido(idPedido));
        factura.setSubtotal(subtotal);
        factura.setTotalImpuestos(totalImpuestos);
        factura.setTotalFacturado(totalRedondeado);
        return factura;
    }

    private PagoDominio consultarPagoAprobadoDelPedido(final UUID idPedido) {
        final var filtro = new PagoEntidad();
        filtro.setPedido(new PedidoEntidad(idPedido));
        filtro.setEstado(EstadoPagoEnum.APROBADO.getValor());
        final var pagoAprobado = fabrica.obtenerPagoDAO().consultarPorFiltro(filtro).stream()
                .findFirst()
                .orElse(new PagoEntidad());
        return PagoEntidadAssembler.obtenerInstancia().ensamblarDominio(pagoAprobado);
    }

    private FacturaDominio buscarFacturaDelPedido(final UUID idPedido) {
        final var pago = new PagoEntidad();
        pago.setPedido(new PedidoEntidad(idPedido));
        final var filtro = new FacturaEntidad();
        filtro.setPago(pago);
        final var factura = fabrica.obtenerFacturaDAO().consultarPorFiltro(filtro).stream()
                .findFirst()
                .orElse(new FacturaEntidad());
        return ensamblador.ensamblarDominio(factura);
    }

    private void asignarNuevoIdentificador(final FacturaDominio factura) {
        factura.setId(UtilUUID.generarNuevoUUID());
    }

    private void asignarNumeroFactura(final FacturaDominio factura) {
        final var consecutivo = UtilUUID.generarNuevoUUID().getLeastSignificantBits() & MASCARA_CONSECUTIVO_FACTURA;
        factura.setNumeroFactura(PREFIJO_NUMERO_FACTURA + FORMATO_CONSECUTIVO_FACTURA.formatted(consecutivo));
    }

    private void asignarFechaEmision(final FacturaDominio factura) {
        factura.setFechaEmision(UtilFecha.obtenerFechaHoraActual());
    }

    private void guardar(final FacturaDominio factura) {
        fabrica.obtenerFacturaDAO().acceder(ensamblador.ensamblarEntidad(factura));
    }
}
