package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.LineaPedidoDTO;

public interface LineaPedidoFachada {

    void registrar(LineaPedidoDTO dto);

    void actualizar(LineaPedidoDTO dto);

    void eliminar(UUID id);

    List<LineaPedidoDTO> consultar(LineaPedidoDTO filtros);
}
