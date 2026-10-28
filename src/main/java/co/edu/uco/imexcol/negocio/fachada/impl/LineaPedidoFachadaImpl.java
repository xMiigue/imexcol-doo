package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.LineaPedidoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.LineaPedidoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.LineaPedidoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.LineaPedidoFachada;

public final class LineaPedidoFachadaImpl implements LineaPedidoFachada {

    private final LineaPedidoDTOAssembler ensamblador;

    public LineaPedidoFachadaImpl() {
        super();
        this.ensamblador = LineaPedidoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final LineaPedidoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new LineaPedidoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final LineaPedidoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new LineaPedidoNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new LineaPedidoNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<LineaPedidoDTO> consultar(final LineaPedidoDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new LineaPedidoNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
