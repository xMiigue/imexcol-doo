package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.datos.dao.fabrica.FabricaEnum;
import co.edu.uco.imexcol.dto.MetodoPagoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.MetodoPagoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.MetodoPagoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.MetodoPagoFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class MetodoPagoFachadaImpl implements MetodoPagoFachada {

    private final MetodoPagoDTOAssembler ensamblador;

    public MetodoPagoFachadaImpl() {
        super();
        this.ensamblador = MetodoPagoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final MetodoPagoDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica(FabricaEnum.SQLSERVER);
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new MetodoPagoNegocioImpl(fabrica.obtenerMetodoPagoDAO());
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
    public void actualizar(final MetodoPagoDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica(FabricaEnum.SQLSERVER);
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new MetodoPagoNegocioImpl(fabrica.obtenerMetodoPagoDAO());
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
        final var fabrica = FabricaDAO.obtenerFabrica(FabricaEnum.SQLSERVER);
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new MetodoPagoNegocioImpl(fabrica.obtenerMetodoPagoDAO());
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
    public List<MetodoPagoDTO> consultar(final MetodoPagoDTO filtros) {
        final var fabrica = FabricaDAO.obtenerFabrica(FabricaEnum.SQLSERVER);
        try {
            fabrica.iniciarTransaccion();
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new MetodoPagoDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var negocio = new MetodoPagoNegocioImpl(fabrica.obtenerMetodoPagoDAO());
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

    private static ImexcolException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido(),
                Lugar.FACHADA);
    }
}
