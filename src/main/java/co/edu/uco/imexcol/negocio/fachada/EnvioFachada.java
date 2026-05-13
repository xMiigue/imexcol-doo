package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.EnvioDTO;

public interface EnvioFachada {

    void registrar(EnvioDTO dto);

    void actualizar(EnvioDTO dto);

    void eliminar(UUID id);

    List<EnvioDTO> consultar(EnvioDTO filtros);
}
