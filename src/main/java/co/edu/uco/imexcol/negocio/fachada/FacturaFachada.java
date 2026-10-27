package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.FacturaDTO;

public interface FacturaFachada {

    void registrar(FacturaDTO dto);

    FacturaDTO consultarPorPedidoDelCliente(UUID idPedido, UUID idCliente);

    List<FacturaDTO> consultar(FacturaDTO filtros);
}
