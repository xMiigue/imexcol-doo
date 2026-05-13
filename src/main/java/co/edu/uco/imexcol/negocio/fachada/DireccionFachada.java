package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.DireccionDTO;

public interface DireccionFachada {

    void registrar(DireccionDTO dto);

    void actualizar(DireccionDTO dto);

    void eliminar(UUID id);

    List<DireccionDTO> consultar(DireccionDTO filtros);
}
