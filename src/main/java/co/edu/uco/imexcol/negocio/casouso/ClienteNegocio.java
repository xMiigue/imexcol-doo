package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.ClienteDominio;

public interface ClienteNegocio {

    void registrar(ClienteDominio dominio);

    void actualizar(ClienteDominio dominio);

    void eliminar(UUID id);

    List<ClienteDominio> consultar(ClienteDominio filtros);
}
