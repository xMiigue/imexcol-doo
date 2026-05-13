package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;

public interface DireccionNegocio {

    void registrar(DireccionDominio dominio);

    void actualizar(DireccionDominio dominio);

    void eliminar(UUID id);

    List<DireccionDominio> consultar(DireccionDominio filtros);
}
