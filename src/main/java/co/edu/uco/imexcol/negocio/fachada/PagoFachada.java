package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.PagoDTO;

public interface PagoFachada {

    void registrar(PagoDTO dto);

    void actualizar(PagoDTO dto);

    void eliminar(UUID id);

    List<PagoDTO> consultar(PagoDTO filtros);
}
