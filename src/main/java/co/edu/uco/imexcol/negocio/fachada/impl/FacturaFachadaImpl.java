package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.dto.FacturaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.FacturaDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.FacturaNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.FacturaFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class FacturaFachadaImpl implements FacturaFachada {

    private final FacturaDTOAssembler ensamblador;

    public FacturaFachadaImpl() {
        super();
        this.ensamblador = FacturaDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final FacturaDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new FacturaDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new FacturaNegocioImpl(fabrica);
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
    public FacturaDTO consultarPorPedidoDelCliente(final UUID idPedido, final UUID idCliente) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new FacturaNegocioImpl(fabrica);
            final var dominio = negocio.consultarPorPedidoDelCliente(idPedido, idCliente);
            final var resultado = ensamblador.ensamblarDTO(dominio);
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
    public List<FacturaDTO> consultar(final FacturaDTO filtros) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new FacturaDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var negocio = new FacturaNegocioImpl(fabrica);
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
