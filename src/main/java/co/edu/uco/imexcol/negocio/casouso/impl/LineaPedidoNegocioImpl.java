package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.LineaPedidoDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.LineaPedidoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.LineaPedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

/**
 * Casos de uso del objeto de dominio LineaPedido.
 *
 * Políticas de negocio aplicadas (referencia documentación de políticas):
 * <ul>
 *   <li>LIN-POL-01: Validación de datos requeridos.</li>
 *   <li>LIN-POL-02: El pedido y producto asociados deben existir.</li>
 *   <li>LIN-POL-03: subtotal = cantidad × precioUnitario.</li>
 * </ul>
 */
public final class LineaPedidoNegocioImpl implements LineaPedidoNegocio {

    private final LineaPedidoDAO dao;
    private final LineaPedidoEntidadAssembler ensamblador;

    public LineaPedidoNegocioImpl(final LineaPedidoDAO dao) {
        super();
        if (UtilObjeto.esNulo(dao)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                    Lugar.NEGOCIO);
        }
        this.dao = dao;
        this.ensamblador = LineaPedidoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        validarDatosComunes(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        // TODO: validar stock disponible del producto contra el DAO de Producto.
        dominioSeguro.setId(UUID.randomUUID());
        dao.acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "línea de pedido");
        validarDatosComunes(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        dao.actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "línea de pedido");
        dao.eliminar(id);
    }

    @Override
    public List<LineaPedidoDominio> consultar(final LineaPedidoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new LineaPedidoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = dao.consultarPorFiltro(entidadFiltro);
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
            throw ImexcolException.crear(
                    "El subtotal de la línea del pedido no coincide con cantidad × precio unitario. Por favor verifique los valores.",
                    "Inconsistencia detectada en LineaPedido: subtotal=%s, esperado=%s (cantidad=%s * precioUnitario=%s)."
                            .formatted(dominio.getSubtotal(), esperado, dominio.getCantidad(), dominio.getPrecioUnitario()),
                    Lugar.NEGOCIO);
        }
    }
}
