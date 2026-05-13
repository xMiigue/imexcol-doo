package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ResenaDTO;

public interface ResenaFachada {

    void registrar(ResenaDTO dto);

    void actualizar(ResenaDTO dto);

    void eliminar(UUID id);

    List<ResenaDTO> consultar(ResenaDTO filtros);
}
