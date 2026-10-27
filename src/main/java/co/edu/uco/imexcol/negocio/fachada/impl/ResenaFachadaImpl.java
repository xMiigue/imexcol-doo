package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ResenaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ResenaDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.ResenaNegocio;
import co.edu.uco.imexcol.negocio.casouso.impl.ResenaNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ResenaFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class ResenaFachadaImpl implements ResenaFachada {

    private final ResenaNegocio negocio;
    private final ResenaDTOAssembler ensamblador;

    public ResenaFachadaImpl() {
        super();
        this.negocio = new ResenaNegocioImpl();
        this.ensamblador = ResenaDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ResenaDTO dto) {
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ResenaDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.registrar(dominio);
        } catch (final ImexcolException excepcion) {
            throw excepcion;
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final ResenaDTO dto) {
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ResenaDTO());
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
    public List<ResenaDTO> consultar(final ResenaDTO filtros) {
        try {
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ResenaDTO());
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
