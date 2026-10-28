package co.edu.uco.imexcol.negocio.fachada.impl;

import co.edu.uco.imexcol.dto.ResultadoCompraDTO;
import co.edu.uco.imexcol.dto.SolicitudCompraDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ResultadoCompraDTOAssembler;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.SolicitudCompraDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.CompraNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.CompraFachada;

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
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensambladorResultado.ensamblarDTO(
                new CompraNegocioImpl(fabrica).realizarCompra(ensambladorSolicitud.ensamblarDominio(solicitud))));
    }
}
