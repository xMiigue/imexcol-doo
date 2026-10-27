package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.FacturaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.FacturaDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.FacturaNegocio;
import co.edu.uco.imexcol.negocio.casouso.impl.FacturaNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.FacturaFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class FacturaFachadaImpl implements FacturaFachada {

    private final FacturaNegocio negocio;
    private final FacturaDTOAssembler ensamblador;

    public FacturaFachadaImpl() {
        super();
        this.negocio = new FacturaNegocioImpl();
        this.ensamblador = FacturaDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final FacturaDTO dto) {
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new FacturaDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.registrar(dominio);
        } catch (final ImexcolException excepcion) {
            throw excepcion;
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final FacturaDTO dto) {
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new FacturaDTO());
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
    public List<FacturaDTO> consultar(final FacturaDTO filtros) {
        try {
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new FacturaDTO());
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
