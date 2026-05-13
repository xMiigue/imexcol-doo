package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.PagoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarEstadoPagoEsValido;
import co.edu.uco.imexcol.negocio.dominio.PagoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class PagoNegocioImpl implements PagoNegocio {

    private static final String CASO_USO = "PagoNegocio";

    public PagoNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final PagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerPagoDAO().crear(...)
        // TODO: validar que el monto del pago no exceda el total del pedido asociado.
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final PagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "pago");
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerPagoDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "pago");
        // TODO: integrar con DAO — daoFactory.obtenerPagoDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<PagoDominio> consultar(final PagoDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new PagoDominio());
        // TODO: integrar con DAO — daoFactory.obtenerPagoDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final PagoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(),
                "pedido asociado al pago");
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getMetodoPago().getId(),
                "método de pago asociado al pago");

        // El monto del pago debe ser estrictamente > 0.
        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getMonto(), "monto del pago");
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaPago(), "fecha del pago");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), "estado del pago");
        ValidarEstadoPagoEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
