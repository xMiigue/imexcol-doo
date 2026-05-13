package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.MetodoPagoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.MetodoPagoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.MetodoPagoNegocio;
import co.edu.uco.imexcol.negocio.casouso.impl.MetodoPagoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.MetodoPagoFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class MetodoPagoFachadaImpl implements MetodoPagoFachada {

    private final MetodoPagoNegocio negocio;
    private final MetodoPagoDTOAssembler ensamblador;

    public MetodoPagoFachadaImpl() {
        super();
        // TODO: integrar con DAOFactory — instanciar MetodoPagoNegocioImpl(daoFactory) cuando exista la capa de datos.
        this.negocio = new MetodoPagoNegocioImpl();
        this.ensamblador = MetodoPagoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final MetodoPagoDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO());
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
    public void actualizar(final MetodoPagoDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO());
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
    public List<MetodoPagoDTO> consultar(final MetodoPagoDTO filtros) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new MetodoPagoDTO());
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
