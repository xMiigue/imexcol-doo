package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.AdministradorDTO;

public interface AdministradorFachada {

    void registrar(AdministradorDTO dto);

    void actualizar(AdministradorDTO dto);

    void eliminar(UUID id);

    List<AdministradorDTO> consultar(AdministradorDTO filtros);
}
