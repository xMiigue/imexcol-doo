package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.EventoRastreoDTO;

public interface EventoRastreoFachada {

    void registrar(EventoRastreoDTO dto);

    void actualizar(EventoRastreoDTO dto);

    void eliminar(UUID id);

    List<EventoRastreoDTO> consultar(EventoRastreoDTO filtros);
}
