package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.datos.dao.fabrica.FabricaEnum;
import co.edu.uco.imexcol.dto.CarritoComprasDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.CarritoComprasDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.CarritoComprasNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.CarritoComprasFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class CarritoComprasFachadaImpl implements CarritoComprasFachada {

    private final CarritoComprasDTOAssembler ensamblador;

    public CarritoComprasFachadaImpl() {
        super();
        this.ensamblador = CarritoComprasDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final CarritoComprasDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica(FabricaEnum.SQLSERVER);
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new CarritoComprasDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new CarritoComprasNegocioImpl(fabrica.obtenerCarritoComprasDAO());
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
    public void actualizar(final CarritoComprasDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica(FabricaEnum.SQLSERVER);
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new CarritoComprasDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new CarritoComprasNegocioImpl(fabrica.obtenerCarritoComprasDAO());
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
            final var negocio = new CarritoComprasNegocioImpl(fabrica.obtenerCarritoComprasDAO());
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
    public List<CarritoComprasDTO> consultar(final CarritoComprasDTO filtros) {
        final var fabrica = FabricaDAO.obtenerFabrica(FabricaEnum.SQLSERVER);
        try {
            fabrica.iniciarTransaccion();
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new CarritoComprasDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var negocio = new CarritoComprasNegocioImpl(fabrica.obtenerCarritoComprasDAO());
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
