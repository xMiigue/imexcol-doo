package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.FacturaDTO;

public interface FacturaFachada {

    void registrar(FacturaDTO dto);

    void actualizar(FacturaDTO dto);

    void eliminar(UUID id);

    List<FacturaDTO> consultar(FacturaDTO filtros);
}
