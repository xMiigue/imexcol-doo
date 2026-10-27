package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.dto.ItemCarritoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ItemCarritoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.ItemCarritoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ItemCarritoFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class ItemCarritoFachadaImpl implements ItemCarritoFachada {

    private final ItemCarritoDTOAssembler ensamblador;

    public ItemCarritoFachadaImpl() {
        super();
        this.ensamblador = ItemCarritoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ItemCarritoDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ItemCarritoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new ItemCarritoNegocioImpl(fabrica);
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
    public void actualizar(final ItemCarritoDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ItemCarritoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new ItemCarritoNegocioImpl(fabrica);
            negocio.actualizar(dominio);
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
    public void eliminar(final UUID id) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new ItemCarritoNegocioImpl(fabrica);
            negocio.eliminar(id);
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
    public List<ItemCarritoDTO> consultar(final ItemCarritoDTO filtros) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ItemCarritoDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var negocio = new ItemCarritoNegocioImpl(fabrica);
            final var dominios = negocio.consultar(dominioFiltro);
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
