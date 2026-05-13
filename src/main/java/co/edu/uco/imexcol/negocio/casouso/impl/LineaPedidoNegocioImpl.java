package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.LineaPedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class LineaPedidoNegocioImpl implements LineaPedidoNegocio {

    private static final String CASO_USO = "LineaPedidoNegocio";

    public LineaPedidoNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        validarDatosComunes(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerLineaPedidoDAO().crear(...)
        // TODO: validar stock disponible del producto contra la capa de datos.
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final LineaPedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new LineaPedidoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "línea de pedido");
        validarDatosComunes(dominioSeguro);
        validarCoherenciaSubtotal(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerLineaPedidoDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "línea de pedido");
        // TODO: integrar con DAO — daoFactory.obtenerLineaPedidoDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<LineaPedidoDominio> consultar(final LineaPedidoDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new LineaPedidoDominio());
        // TODO: integrar con DAO — daoFactory.obtenerLineaPedidoDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final LineaPedidoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(),
                "pedido asociado a la línea");
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getProducto().getId(),
                "producto asociado a la línea");

        // La cantidad debe ser al menos 1 (no tiene sentido una línea con 0 unidades).
        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getCantidad(),
                "cantidad de la línea del pedido");
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getPrecioUnitario(),
                "precio unitario de la línea del pedido");
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getSubtotal(),
                "subtotal de la línea del pedido");
    }

    private void validarCoherenciaSubtotal(final LineaPedidoDominio dominio) {
        // Regla de negocio: subtotal == cantidad * precioUnitario (con tolerancia para redondeo).
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

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
