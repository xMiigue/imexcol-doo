package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.EventoRastreoDTO;

public interface EventoRastreoFachada {

    void registrar(EventoRastreoDTO dto);

    List<EventoRastreoDTO> consultarHistorialPorEnvio(UUID idEnvio);

    List<EventoRastreoDTO> consultarHistorialDelCliente(UUID idEnvio, UUID idCliente);
}
