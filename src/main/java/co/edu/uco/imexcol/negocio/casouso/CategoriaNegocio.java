package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.CategoriaDominio;

public interface CategoriaNegocio {

    void registrar(CategoriaDominio dominio);

    void actualizar(CategoriaDominio dominio);

    void eliminar(UUID id);

    List<CategoriaDominio> consultar(CategoriaDominio filtros);
}
