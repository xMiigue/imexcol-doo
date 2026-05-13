package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ClienteDTO;

public interface ClienteFachada {

    void registrar(ClienteDTO dto);

    void actualizar(ClienteDTO dto);

    void eliminar(UUID id);

    List<ClienteDTO> consultar(ClienteDTO filtros);
}
