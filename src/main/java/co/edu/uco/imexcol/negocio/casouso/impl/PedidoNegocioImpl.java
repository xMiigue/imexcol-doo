package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.PedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarEstadoPedidoEsValido;
import co.edu.uco.imexcol.negocio.dominio.PedidoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class PedidoNegocioImpl implements PedidoNegocio {

    private static final String CASO_USO = "PedidoNegocio";

    public PedidoNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final PedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PedidoDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerPedidoDAO().crear(...)
        // TODO: validar coherencia 'total == suma(subtotales de LineaPedido)' cuando exista la capa de datos.
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final PedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PedidoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "pedido");
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerPedidoDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "pedido");
        // TODO: integrar con DAO — daoFactory.obtenerPedidoDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<PedidoDominio> consultar(final PedidoDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new PedidoDominio());
        // TODO: integrar con DAO — daoFactory.obtenerPedidoDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final PedidoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado al pedido");
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaPedido(),
                "fecha del pedido");
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getTotal(), "total del pedido");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), "estado del pedido");
        ValidarEstadoPedidoEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
