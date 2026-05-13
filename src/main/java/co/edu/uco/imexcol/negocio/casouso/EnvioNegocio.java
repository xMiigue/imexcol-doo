package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.EnvioDominio;

public interface EnvioNegocio {

    void registrar(EnvioDominio dominio);

    void actualizar(EnvioDominio dominio);

    void eliminar(UUID id);

    List<EnvioDominio> consultar(EnvioDominio filtros);
}
