package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.EventoRastreoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.EventoRastreoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.EventoRastreoNegocio;
import co.edu.uco.imexcol.negocio.casouso.impl.EventoRastreoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.EventoRastreoFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class EventoRastreoFachadaImpl implements EventoRastreoFachada {

    private final EventoRastreoNegocio negocio;
    private final EventoRastreoDTOAssembler ensamblador;

    public EventoRastreoFachadaImpl() {
        super();
        // TODO: integrar con DAOFactory — instanciar EventoRastreoNegocioImpl(daoFactory) cuando exista la capa de datos.
        this.negocio = new EventoRastreoNegocioImpl();
        this.ensamblador = EventoRastreoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EventoRastreoDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new EventoRastreoDTO());
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
    public void actualizar(final EventoRastreoDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new EventoRastreoDTO());
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
    public List<EventoRastreoDTO> consultar(final EventoRastreoDTO filtros) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new EventoRastreoDTO());
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
