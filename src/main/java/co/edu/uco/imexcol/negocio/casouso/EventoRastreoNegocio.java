package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.EventoRastreoDominio;

public interface EventoRastreoNegocio {

    void registrar(EventoRastreoDominio dominio);

    List<EventoRastreoDominio> consultarHistorialPorEnvio(UUID idEnvio);

    List<EventoRastreoDominio> consultarHistorialDelCliente(UUID idEnvio, UUID idCliente);
}
