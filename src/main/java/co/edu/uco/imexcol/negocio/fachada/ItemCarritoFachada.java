package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ItemCarritoDTO;

public interface ItemCarritoFachada {

    void registrar(ItemCarritoDTO dto);

    void actualizar(ItemCarritoDTO dto);

    void eliminar(UUID id);

    List<ItemCarritoDTO> consultar(ItemCarritoDTO filtros);
}
