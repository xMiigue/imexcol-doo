package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.PedidoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.PedidoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.PedidoFachada;

public final class PedidoFachadaImpl implements PedidoFachada {

    private final PedidoDTOAssembler ensamblador;

    public PedidoFachadaImpl() {
        super();
        this.ensamblador = PedidoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final PedidoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new PedidoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final PedidoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new PedidoNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new PedidoNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<PedidoDTO> consultar(final PedidoDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new PedidoNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
