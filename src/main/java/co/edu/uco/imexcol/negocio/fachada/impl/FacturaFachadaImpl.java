package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.FacturaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.FacturaDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.FacturaNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.FacturaFachada;

public final class FacturaFachadaImpl implements FacturaFachada {

    private final FacturaDTOAssembler ensamblador;

    public FacturaFachadaImpl() {
        super();
        this.ensamblador = FacturaDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final FacturaDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new FacturaNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public FacturaDTO consultarPorPedidoDelCliente(final UUID idPedido, final UUID idCliente) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new FacturaNegocioImpl(fabrica).consultarPorPedidoDelCliente(idPedido, idCliente)));
    }

    @Override
    public List<FacturaDTO> consultar(final FacturaDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new FacturaNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
