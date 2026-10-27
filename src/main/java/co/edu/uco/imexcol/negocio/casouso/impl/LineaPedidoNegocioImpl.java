package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.LineaPedidoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.LineaPedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class LineaPedidoNegocioImpl implements LineaPedidoNegocio {

    private final FabricaDAO fabrica;
    private final LineaPedidoEntidadAssembler ensamblador;

    public LineaPedidoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = LineaPedidoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        validarDatosComunes(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerLineaPedidoDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "línea de pedido");
        validarDatosComunes(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        fabrica.obtenerLineaPedidoDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "línea de pedido");
        fabrica.obtenerLineaPedidoDAO().eliminar(id);
    }

    @Override
    public List<LineaPedidoDominio> consultar(final LineaPedidoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new LineaPedidoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerLineaPedidoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final LineaPedidoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(),
                "pedido asociado a la línea");
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getProducto().getId(),
                "producto asociado a la línea");

        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getCantidad(),
                "cantidad de la línea del pedido");
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getPrecioUnitario(),
                "precio unitario de la línea del pedido");
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getSubtotal(),
                "subtotal de la línea del pedido");
    }

    private void validarCoherenciaSubtotal(final LineaPedidoDominio dominio) {
        final var esperado = dominio.getCantidad() * dominio.getPrecioUnitario();
        final var diferencia = Math.abs(esperado - dominio.getSubtotal());
        if (diferencia > 0.01) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_LINEA_PEDIDO_SUBTOTAL_INCONSISTENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_LINEA_PEDIDO_SUBTOTAL_INCONSISTENTE.getContenido()
                            .formatted(dominio.getSubtotal(), esperado, dominio.getCantidad(),
                                    dominio.getPrecioUnitario()));
        }
    }
}
