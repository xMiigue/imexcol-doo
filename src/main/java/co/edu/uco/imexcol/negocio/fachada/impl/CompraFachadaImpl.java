package co.edu.uco.imexcol.negocio.fachada.impl;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.dto.ResultadoCompraDTO;
import co.edu.uco.imexcol.dto.SolicitudCompraDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ResultadoCompraDTOAssembler;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.SolicitudCompraDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.CompraNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.CompraFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class CompraFachadaImpl implements CompraFachada {

    private final SolicitudCompraDTOAssembler ensambladorSolicitud;
    private final ResultadoCompraDTOAssembler ensambladorResultado;

    public CompraFachadaImpl() {
        super();
        this.ensambladorSolicitud = SolicitudCompraDTOAssembler.obtenerInstancia();
        this.ensambladorResultado = ResultadoCompraDTOAssembler.obtenerInstancia();
    }

    @Override
    public ResultadoCompraDTO realizarCompra(final SolicitudCompraDTO solicitud) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dominio = ensambladorSolicitud.ensamblarDominio(solicitud);
            final var negocio = new CompraNegocioImpl(fabrica);
            final var resultado = negocio.realizarCompra(dominio);
            fabrica.confirmarTransaccion();
            return ensambladorResultado.ensamblarDTO(resultado);
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
