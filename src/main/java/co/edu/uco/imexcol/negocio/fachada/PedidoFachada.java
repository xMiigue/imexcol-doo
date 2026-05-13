package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.PedidoDTO;

public interface PedidoFachada {

    void registrar(PedidoDTO dto);

    void actualizar(PedidoDTO dto);

    void eliminar(UUID id);

    List<PedidoDTO> consultar(PedidoDTO filtros);
}
