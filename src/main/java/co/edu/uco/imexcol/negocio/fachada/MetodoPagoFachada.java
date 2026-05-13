package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.MetodoPagoDTO;

public interface MetodoPagoFachada {

    void registrar(MetodoPagoDTO dto);

    void actualizar(MetodoPagoDTO dto);

    void eliminar(UUID id);

    List<MetodoPagoDTO> consultar(MetodoPagoDTO filtros);
}
