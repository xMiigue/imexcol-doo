package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.ResenaDominio;

public interface ResenaNegocio {

    void registrar(ResenaDominio dominio);

    void actualizar(ResenaDominio dominio);

    void eliminar(UUID id);

    List<ResenaDominio> consultar(ResenaDominio filtros);
}
