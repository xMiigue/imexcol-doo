package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.dto.LineaPedidoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.LineaPedidoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.LineaPedidoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.LineaPedidoFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class LineaPedidoFachadaImpl implements LineaPedidoFachada {

    private final LineaPedidoDTOAssembler ensamblador;

    public LineaPedidoFachadaImpl() {
        super();
        this.ensamblador = LineaPedidoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final LineaPedidoDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new LineaPedidoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new LineaPedidoNegocioImpl(fabrica);
            negocio.registrar(dominio);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    @Override
    public void actualizar(final LineaPedidoDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new LineaPedidoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new LineaPedidoNegocioImpl(fabrica);
            negocio.actualizar(dominio);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    @Override
    public void eliminar(final UUID id) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new LineaPedidoNegocioImpl(fabrica);
            negocio.eliminar(id);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    @Override
    public List<LineaPedidoDTO> consultar(final LineaPedidoDTO filtros) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new LineaPedidoDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var negocio = new LineaPedidoNegocioImpl(fabrica);
            final var dominios = negocio.consultar(dominioFiltro);
            final var resultado = ensamblador.ensamblarDTO(dominios);
            fabrica.confirmarTransaccion();
            return resultado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    private static ImexcolFachadaException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolFachadaException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido());
    }
}
