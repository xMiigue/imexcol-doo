package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.CategoriaDTO;

public interface CategoriaFachada {

    void registrar(CategoriaDTO dto);

    void actualizar(CategoriaDTO dto);

    void eliminar(UUID id);

    List<CategoriaDTO> consultar(CategoriaDTO filtros);
}
