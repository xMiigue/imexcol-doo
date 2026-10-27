package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.dto.EventoRastreoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.EventoRastreoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.EventoRastreoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.EventoRastreoFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class EventoRastreoFachadaImpl implements EventoRastreoFachada {

    private final EventoRastreoDTOAssembler ensamblador;

    public EventoRastreoFachadaImpl() {
        super();
        this.ensamblador = EventoRastreoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EventoRastreoDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new EventoRastreoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new EventoRastreoNegocioImpl(fabrica);
            negocio.registrar(dominio);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    @Override
    public List<EventoRastreoDTO> consultarHistorialPorEnvio(final UUID idEnvio) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new EventoRastreoNegocioImpl(fabrica);
            final var dominios = negocio.consultarHistorialPorEnvio(idEnvio);
            final var resultado = ensamblador.ensamblarDTO(dominios);
            fabrica.confirmarTransaccion();
            return resultado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    @Override
    public List<EventoRastreoDTO> consultarHistorialDelCliente(final UUID idEnvio, final UUID idCliente) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new EventoRastreoNegocioImpl(fabrica);
            final var dominios = negocio.consultarHistorialDelCliente(idEnvio, idCliente);
            final var resultado = ensamblador.ensamblarDTO(dominios);
            fabrica.confirmarTransaccion();
            return resultado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    private static ImexcolFachadaException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolFachadaException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido());
    }
}
