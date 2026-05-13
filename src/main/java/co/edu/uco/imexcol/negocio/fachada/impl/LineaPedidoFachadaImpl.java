package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.LineaPedidoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.LineaPedidoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.LineaPedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.impl.LineaPedidoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.LineaPedidoFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class LineaPedidoFachadaImpl implements LineaPedidoFachada {

    private final LineaPedidoNegocio negocio;
    private final LineaPedidoDTOAssembler ensamblador;

    public LineaPedidoFachadaImpl() {
        super();
        // TODO: integrar con DAOFactory — instanciar LineaPedidoNegocioImpl(daoFactory) cuando exista la capa de datos.
        this.negocio = new LineaPedidoNegocioImpl();
        this.ensamblador = LineaPedidoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final LineaPedidoDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new LineaPedidoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.registrar(dominio);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }

    @Override
    public void actualizar(final LineaPedidoDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new LineaPedidoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.actualizar(dominio);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }

    @Override
    public void eliminar(final UUID id) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            negocio.eliminar(id);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }

    @Override
    public List<LineaPedidoDTO> consultar(final LineaPedidoDTO filtros) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new LineaPedidoDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var dominios = negocio.consultar(dominioFiltro);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
            return ensamblador.ensamblarDTO(dominios);
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }

    private static ImexcolException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido(),
                Lugar.FACHADA);
    }
}
