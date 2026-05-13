package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.EventoRastreoDominio;

public interface EventoRastreoNegocio {

    void registrar(EventoRastreoDominio dominio);

    void actualizar(EventoRastreoDominio dominio);

    void eliminar(UUID id);

    List<EventoRastreoDominio> consultar(EventoRastreoDominio filtros);
}
