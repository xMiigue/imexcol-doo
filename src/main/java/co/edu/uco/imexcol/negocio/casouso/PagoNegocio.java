package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.PagoDominio;

public interface PagoNegocio {

    void registrar(PagoDominio dominio);

    void actualizar(PagoDominio dominio);

    void eliminar(UUID id);

    List<PagoDominio> consultar(PagoDominio filtros);
}
