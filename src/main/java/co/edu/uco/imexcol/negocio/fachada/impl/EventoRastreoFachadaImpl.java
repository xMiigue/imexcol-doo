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
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class EventoRastreoFachadaImpl implements EventoRastreoFachada {

    private final EventoRastreoNegocio negocio;
    private final EventoRastreoDTOAssembler ensamblador;

    public EventoRastreoFachadaImpl() {
        super();
        this.negocio = new EventoRastreoNegocioImpl();
        this.ensamblador = EventoRastreoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EventoRastreoDTO dto) {
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new EventoRastreoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.registrar(dominio);
        } catch (final ImexcolException excepcion) {
            throw excepcion;
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final EventoRastreoDTO dto) {
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new EventoRastreoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.actualizar(dominio);
        } catch (final ImexcolException excepcion) {
            throw excepcion;
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void eliminar(final UUID id) {
        try {
            negocio.eliminar(id);
        } catch (final ImexcolException excepcion) {
            throw excepcion;
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public List<EventoRastreoDTO> consultar(final EventoRastreoDTO filtros) {
        try {
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new EventoRastreoDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var dominios = negocio.consultar(dominioFiltro);
            return ensamblador.ensamblarDTO(dominios);
        } catch (final ImexcolException excepcion) {
            throw excepcion;
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    private static ImexcolFachadaException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolFachadaException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido());
    }
}
