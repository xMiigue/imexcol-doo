package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ProductoDTO;

public interface ProductoFachada {

    void registrar(ProductoDTO dto);

    void actualizar(ProductoDTO dto);

    void eliminar(UUID id);

    List<ProductoDTO> consultar(ProductoDTO filtros);
}
